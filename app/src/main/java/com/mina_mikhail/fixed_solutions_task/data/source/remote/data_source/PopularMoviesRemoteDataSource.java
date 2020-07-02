package com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;
import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.data.enums.NetworkState;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.PopularMoviesLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.ApiClient;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.response.PopularMoviesResponse;
import com.mina_mikhail.fixed_solutions_task.utils.Constants;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkStatus;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkUtils;
import com.uber.autodispose.ScopeProvider;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class PopularMoviesRemoteDataSource
    extends PageKeyedDataSource<Long, Movie> {

  private MutableLiveData<NetworkStatus> networkState;
  private CompositeDisposable disposable;

  public PopularMoviesRemoteDataSource() {
    networkState = new MutableLiveData<>();
    disposable = new CompositeDisposable();
  }

  @Override
  public void loadInitial(@NonNull LoadInitialParams<Long> params
      , @NonNull final LoadInitialCallback<Long, Movie> callback) {
    disposable.add(ApiClient.getInstance().getApiService().getMovies(Constants.SORT_BY, 1)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribeWith(new DisposableSingleObserver<PopularMoviesResponse>() {
          @Override
          public void onSuccess(PopularMoviesResponse moviesResponse) {
            if (!disposable.isDisposed()) {
              if (moviesResponse != null
                  && moviesResponse.getResults() != null
                  && !moviesResponse.getResults().isEmpty()) {

                networkState.postValue(new NetworkStatus(NetworkState.LOADED, ""));

                callback.onResult(moviesResponse.getResults(), null, (long) 2);

                saveMoviesToLocal(moviesResponse.getResults(), true);
              } else {
                networkState.postValue(new NetworkStatus(NetworkState.FAILED, ""));
              }

              dispose();
            }
          }

          @Override
          public void onError(Throwable e) {
            if (!disposable.isDisposed()) {
              if (!NetworkUtils.isNetworkConnected(MyApplication.getInstance())) {
                networkState.postValue(new NetworkStatus(NetworkState.NO_INTERNET, ""));
              } else {
                networkState.postValue(new NetworkStatus(NetworkState.FAILED, e.getMessage()));
              }

              dispose();
            }
          }
        }));
  }

  @Override
  public void loadBefore(@NonNull LoadParams<Long> params,
      @NonNull LoadCallback<Long, Movie> callback) {

  }

  @Override
  public void loadAfter(@NonNull final LoadParams<Long> params
      , @NonNull final LoadCallback<Long, Movie> callback) {
    networkState.postValue(new NetworkStatus(NetworkState.LOADING, ""));
    disposable.add(ApiClient.getInstance().getApiService().getMovies(Constants.SORT_BY, params.key)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribeWith(new DisposableSingleObserver<PopularMoviesResponse>() {
          @Override
          public void onSuccess(PopularMoviesResponse moviesResponse) {
            if (!disposable.isDisposed()) {
              if (moviesResponse != null
                  && moviesResponse.getResults() != null
                  && !moviesResponse.getResults().isEmpty()) {

                networkState.postValue(new NetworkStatus(NetworkState.LOADED, ""));

                long nextPage =
                    (params.key == moviesResponse.getTotal_pages()) ? null : params.key + 1;

                callback.onResult(moviesResponse.getResults(), nextPage);

                saveMoviesToLocal(moviesResponse.getResults(), false);
              } else {
                networkState.postValue(new NetworkStatus(NetworkState.FAILED, ""));
              }

              dispose();
            }
          }

          @Override
          public void onError(Throwable e) {
            if (!disposable.isDisposed()) {
              if (!NetworkUtils.isNetworkConnected(MyApplication.getInstance())) {
                networkState.postValue(new NetworkStatus(NetworkState.NO_INTERNET, ""));
              } else {
                networkState.postValue(new NetworkStatus(NetworkState.FAILED, e.getMessage()));
              }

              dispose();
            }
          }
        }));
  }

  private void saveMoviesToLocal(List<Movie> remoteMoviesList, boolean clearOldData) {
    PopularMoviesLocalDataSource localDataSource = new PopularMoviesLocalDataSource();
    if (clearOldData) {
      localDataSource.clearMovies(() -> localDataSource.insertMovies(remoteMoviesList));
    } else {
      localDataSource.insertMovies(remoteMoviesList);
    }
  }

  public MutableLiveData<NetworkStatus> getNetworkState() {
    return networkState;
  }
}