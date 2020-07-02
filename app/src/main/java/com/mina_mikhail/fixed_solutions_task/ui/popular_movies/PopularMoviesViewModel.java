package com.mina_mikhail.fixed_solutions_task.ui.popular_movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.PopularMoviesLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.PopularMoviesDataSourceFactory;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.PopularMoviesRemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseViewModel;
import com.mina_mikhail.fixed_solutions_task.utils.Constants;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkStatus;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkUtils;
import com.mina_mikhail.fixed_solutions_task.utils.SingleLiveEvent;
import javax.inject.Inject;

public class PopularMoviesViewModel
    extends BaseViewModel {

  @Inject
  PopularMoviesDataSourceFactory moviesDataSourceFactory;

  @Inject
  PagedList.Config pageConfig;

  private LiveData<PagedList<Movie>> remoteMoviePagedList;
  private LiveData<PagedList<Movie>> localMoviePagedList;
  private LiveData<NetworkStatus> networkStatusLiveData;

  public PopularMoviesViewModel() {
    MyApplication.getInstance().getAppComponent().inject(this);

    remoteMoviePagedList = new SingleLiveEvent<>();
    localMoviePagedList = new SingleLiveEvent<>();

    networkStatusLiveData =
        Transformations.switchMap(moviesDataSourceFactory.getItemLiveDataSource(),
            PopularMoviesRemoteDataSource::getNetworkState);
  }

  void getMovies() {
    if (NetworkUtils.isNetworkConnected(MyApplication.getInstance())) {
      moviesDataSourceFactory.getItemLiveDataSource();

      remoteMoviePagedList =
          (new LivePagedListBuilder<Long, Movie>(moviesDataSourceFactory, pageConfig)).build();
    } else {
      PopularMoviesLocalDataSource localDataSource = new PopularMoviesLocalDataSource();
      localMoviePagedList =
          new LivePagedListBuilder<>(localDataSource.getMovies(), Constants.PAGE_SIZE).build();
    }
  }

  LiveData<PagedList<Movie>> remoteMoviePagedList() {
    return remoteMoviePagedList;
  }

  LiveData<PagedList<Movie>> localMoviePagedList() {
    return localMoviePagedList;
  }

  LiveData<NetworkStatus> networkStatusLiveData() {
    return networkStatusLiveData;
  }
}