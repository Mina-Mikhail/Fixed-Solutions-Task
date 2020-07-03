package com.mina_mikhail.fixed_solutions_task.data.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;
import com.mina_mikhail.fixed_solutions_task.data.model.other.RemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.PopularMoviesLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.PopularMoviesRemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.PopularMoviesRemoteDataSourceFactory;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkStatus;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkUtils;

public class PopularMoviesRepository {

  private PopularMoviesLocalDataSource localDataSource;
  private PopularMoviesRemoteDataSourceFactory moviesDataSourceFactory;
  private PagedList.Config config;

  private final RemoteDataSource<LiveData<PagedList<Movie>>> data;
  private LiveData<NetworkStatus> networkStatusLiveData;
  private Observer<NetworkStatus> networkStatusObserver;

  public PopularMoviesRepository(PopularMoviesLocalDataSource localDataSource
      , PopularMoviesRemoteDataSourceFactory moviesDataSourceFactory
      , PagedList.Config config) {
    this.localDataSource = localDataSource;
    this.moviesDataSourceFactory = moviesDataSourceFactory;
    this.config = config;

    data = new RemoteDataSource<>();

    // To observe network updates from Remote Data Source
    networkStatusLiveData =
        Transformations.switchMap(moviesDataSourceFactory.getItemLiveDataSource(),
            PopularMoviesRemoteDataSource::getNetworkState);
    networkStatusObserver = networkStatus -> {
      if (networkStatus != null) {
        data.setNetworkState(networkStatus.getState());
      }
    };
  }

  public RemoteDataSource<LiveData<PagedList<Movie>>> getPopularMovies() {
    if (NetworkUtils.isNetworkConnected(MyApplication.getInstance())) {
      moviesDataSourceFactory.getItemLiveDataSource();

      data.setIsLoadedFromRemote(
          (new LivePagedListBuilder<Long, Movie>(moviesDataSourceFactory, config)).build());
      networkStatusLiveData.observeForever(networkStatusObserver);

      return data;
    } else {
      data.setIsLoadedFromLocal(
          (new LivePagedListBuilder(localDataSource.getMovies(), config)).build(),
          MyApplication.getInstance().getString(R.string.success_local_load_details));

      return data;
    }
  }

  public void unRegisterObservers() {
    if (networkStatusObserver != null) {
      networkStatusLiveData.removeObserver(networkStatusObserver);
      networkStatusObserver = null;
    }
  }
}