package com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source;

import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.data.model.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.model.RemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.MovieDetailsLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.ApiClient;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkUtils;
import com.uber.autodispose.ScopeProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class MovieDetailsRemoteDataSource {

  private CompositeDisposable disposable;
  private RemoteDataSource<MovieDetails> data;

  public MovieDetailsRemoteDataSource() {
    data = new RemoteDataSource<>();
    disposable = new CompositeDisposable();
  }

  public RemoteDataSource<MovieDetails> getMovieDetails(int movieID) {
    data.setIsLoading();
    disposable.add(ApiClient.getInstance().getApiService().getMovieDetails(movieID)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribeWith(new DisposableSingleObserver<MovieDetails>() {
          @Override
          public void onSuccess(MovieDetails moviesDetails) {
            if (!disposable.isDisposed()) {

              if (moviesDetails != null
                  && moviesDetails.getId() != 0) {

                saveMovieToLocal(moviesDetails);

                data.setIsLoaded(moviesDetails,
                    MyApplication.getInstance().getString(R.string.success_remote_load_details));
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

  private void saveMovieToLocal(MovieDetails remoteMovieDetails) {
    MovieDetailsLocalDataSource localDataSource = new MovieDetailsLocalDataSource();
    localDataSource.insertMovie(remoteMovieDetails);
  }
}