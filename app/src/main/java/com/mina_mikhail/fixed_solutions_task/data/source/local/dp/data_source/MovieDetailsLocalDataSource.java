package com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source;

import androidx.lifecycle.Observer;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.data.model.api.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.model.other.RemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao.MovieDetailsDao;
import com.mina_mikhail.fixed_solutions_task.utils.ResourceProvider;
import com.uber.autodispose.ScopeProvider;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class MovieDetailsLocalDataSource {

  private MovieDetailsDao movieDetailsDao;
  private ResourceProvider resourceProvider;
  private RemoteDataSource<MovieDetails> data;
  private Observer<MovieDetails> localMovieDetailsObserver;

  @Inject
  public MovieDetailsLocalDataSource(MovieDetailsDao movieDetailsDao
      , ResourceProvider resourceProvider) {
    this.movieDetailsDao = movieDetailsDao;
    this.resourceProvider = resourceProvider;

    data = new RemoteDataSource<>();
  }

  public RemoteDataSource<MovieDetails> getMovieDetails(int movieID) {
    localMovieDetailsObserver = movie -> {
      if (movie != null && movie.getId() != 0) {
        data.setIsLoadedFromLocal(movie,
            resourceProvider.getString(R.string.success_local_load_details));
      } else {
        data.setFailed("");
      }
    };
    movieDetailsDao.getMovie(movieID).observeForever(localMovieDetailsObserver);

    return data;
  }

  public void insertMovie(MovieDetails movieDetails) {
    Completable
        .fromAction(() -> movieDetailsDao.insert(movieDetails))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribe(new CompletableObserver() {
          @Override
          public void onSubscribe(Disposable d) {
            System.out.println("====SUB-INSERT-MOVIE-DETAILS==");
          }

          @Override
          public void onComplete() {
            System.out.println("====COMPLETE-INSERT-MOVIE-DETAILS==");
          }

          @Override
          public void onError(Throwable e) {
            System.out.println("====ERROR-INSERT-MOVIE-DETAILS==>> " + e.getMessage());
          }
        });
  }

  public void clearMovies(ClearLocalDataCallback clearLocalDataCallback) {
    Completable
        .fromAction(movieDetailsDao::clearTable)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribe(new CompletableObserver() {
          @Override
          public void onSubscribe(Disposable d) {
            System.out.println("====SUB-INSERT-MOVIE-DETAILS==");
          }

          @Override
          public void onComplete() {
            System.out.println("====COMPLETE-INSERT-MOVIE-DETAILS==");
            clearLocalDataCallback.onLocalCleared();
          }

          @Override
          public void onError(Throwable e) {
            System.out.println("====ERROR-INSERT-MOVIE-DETAILS==>> " + e.getMessage());
          }
        });
  }

  public void unRegisterObservers(int movieID) {
    if (localMovieDetailsObserver != null) {
      movieDetailsDao.getMovie(movieID).removeObserver(localMovieDetailsObserver);
      localMovieDetailsObserver = null;
    }
  }

  public interface ClearLocalDataCallback {
    void onLocalCleared();
  }
}