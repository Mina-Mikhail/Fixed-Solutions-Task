package com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source;

import androidx.lifecycle.Observer;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.data.model.Movie;
import com.mina_mikhail.fixed_solutions_task.data.model.RemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.AppDatabase;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao.PopularMoviesDao;
import com.uber.autodispose.ScopeProvider;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class PopularMoviesLocalDataSource {

  private final PopularMoviesDao moviesDao;
  private RemoteDataSource<List<Movie>> data;
  private Observer<List<Movie>> localMoviesObserver;

  public PopularMoviesLocalDataSource() {
    AppDatabase appDatabase = AppDatabase.getInstance(MyApplication.getInstance());
    moviesDao = appDatabase.getPopularMoviesDao();
    data = new RemoteDataSource<>();
  }

  public RemoteDataSource<List<Movie>> getMovies() {
    localMoviesObserver = movies -> {
      if (movies != null && !movies.isEmpty()) {
        data.setIsLoaded(movies,
            MyApplication.getInstance().getString(R.string.success_local_load));
      } else {
        data.setFailed("");
      }
    };
    moviesDao.getMovies().observeForever(localMoviesObserver);

    return data;
  }

  public void insertMovies(List<Movie> movies) {
    Completable
        .fromAction(() -> moviesDao.insertMovies(movies))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribe(new CompletableObserver() {
          @Override
          public void onSubscribe(Disposable d) {
            System.out.println("==SUB==");
          }

          @Override
          public void onComplete() {
            System.out.println("==COMPLETE==");
          }

          @Override
          public void onError(Throwable e) {
            System.out.println("==ERROR==>> " + e.getMessage());
          }
        });
  }

  public void clearMovies(ClearLocalDataCallback clearLocalDataCallback) {
    Completable
        .fromAction(moviesDao::clearMovies)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribe(new CompletableObserver() {
          @Override
          public void onSubscribe(Disposable d) {
            System.out.println("==SUB==");
          }

          @Override
          public void onComplete() {
            System.out.println("==COMPLETE==");
            clearLocalDataCallback.onLocalCleared();
          }

          @Override
          public void onError(Throwable e) {
            System.out.println("==ERROR==>> " + e.getMessage());
          }
        });
  }

  public void unRegisterObservers() {
    if (localMoviesObserver != null) {
      moviesDao.getMovies().removeObserver(localMoviesObserver);
      localMoviesObserver = null;
    }
  }

  public interface ClearLocalDataCallback {
    void onLocalCleared();
  }
}