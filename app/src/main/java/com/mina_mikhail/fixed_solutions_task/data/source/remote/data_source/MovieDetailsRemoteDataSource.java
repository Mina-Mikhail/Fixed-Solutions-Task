package com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source;

import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.data.model.api.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.model.other.RemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.MovieDetailsLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.MoviesService;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkUtils;
import com.mina_mikhail.fixed_solutions_task.utils.ResourceProvider;
import com.uber.autodispose.ScopeProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class MovieDetailsRemoteDataSource {

  private CompositeDisposable disposable;
  private RemoteDataSource<MovieDetails> data;

  private MoviesService moviesService;
  private MovieDetailsLocalDataSource localDataSource;
  private ResourceProvider resourceProvider;
  private NetworkUtils networkUtils;

  @Inject
  public MovieDetailsRemoteDataSource(MoviesService moviesService
      , MovieDetailsLocalDataSource localDataSource
      , ResourceProvider resourceProvider
      , NetworkUtils networkUtils) {
    this.moviesService = moviesService;
    this.localDataSource = localDataSource;
    this.resourceProvider = resourceProvider;
    this.networkUtils = networkUtils;

    data = new RemoteDataSource<>();
    disposable = new CompositeDisposable();
  }

  public RemoteDataSource<MovieDetails> getMovieDetails(int movieID) {
    data.setIsLoading();
    disposable.add(moviesService.getMovieDetails(movieID)
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

                data.setIsLoadedFromRemote(moviesDetails,
                    resourceProvider.getString(R.string.success_remote_load_details));
              }

              dispose();
            }
          }

          @Override
          public void onError(Throwable e) {
            if (!disposable.isDisposed()) {
              if (!networkUtils.isNetworkConnected()) {
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
    localDataSource.insertMovie(remoteMovieDetails);
  }
}