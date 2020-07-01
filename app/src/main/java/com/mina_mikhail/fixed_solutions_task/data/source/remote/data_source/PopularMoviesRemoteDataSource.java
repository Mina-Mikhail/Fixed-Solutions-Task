package com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source;

import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.data.model.Movie;
import com.mina_mikhail.fixed_solutions_task.data.model.RemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.PopularMoviesLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.ApiClient;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.response.PopularMoviesResponse;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkUtils;
import com.uber.autodispose.ScopeProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class PopularMoviesRemoteDataSource {

  private CompositeDisposable disposable;
  private RemoteDataSource<List<Movie>> data;

  public PopularMoviesRemoteDataSource() {
    data = new RemoteDataSource<>();
    disposable = new CompositeDisposable();
  }

  public RemoteDataSource<List<Movie>> getMovies(String sortBy, int pageNumber) {
    data.setIsLoading();
    disposable.add(ApiClient.getInstance().getApiService().getMovies(sortBy, pageNumber)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribeWith(new DisposableSingleObserver<PopularMoviesResponse>() {
          @Override
          public void onSuccess(PopularMoviesResponse moviesResponse) {
            if (!disposable.isDisposed()) {
              if (moviesResponse != null
                  && moviesResponse.getResults() != null) {
                saveMoviesToLocal(moviesResponse.getResults());
                data.setIsLoaded(moviesResponse.getResults(),
                    MyApplication.getInstance().getString(R.string.success_remote_load));
              }

              dispose();
            }
          }

          @Override
          public void onError(Throwable e) {
            if (!disposable.isDisposed()) {
              if (!NetworkUtils.isNetworkConnected(MyApplication.getInstance())) {
                data.setNoInternet();
              } else {
                data.setFailed(e.getMessage());
              }

              dispose();
            }
          }
        }));

    return data;
  }

  private void saveMoviesToLocal(List<Movie> remoteMoviesList) {
    PopularMoviesLocalDataSource localDataSource = new PopularMoviesLocalDataSource();
    localDataSource.clearMovies(() -> localDataSource.insertMovies(remoteMoviesList));
  }
}