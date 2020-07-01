package com.mina_mikhail.fixed_solutions_task.data.repo;

import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.data.model.Movie;
import com.mina_mikhail.fixed_solutions_task.data.model.RemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.PopularMoviesLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.PopularMoviesRemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkUtils;
import java.util.List;

public class PopularMoviesRepository {

  private PopularMoviesRemoteDataSource remoteDataSource;
  private PopularMoviesLocalDataSource localDataSource;

  public PopularMoviesRepository(PopularMoviesRemoteDataSource remoteDataSource,
      PopularMoviesLocalDataSource localDataSource) {
    this.remoteDataSource = remoteDataSource;
    this.localDataSource = localDataSource;
  }

  public RemoteDataSource<List<Movie>> getMovies(String sortBy) {
    if (NetworkUtils.isNetworkConnected(MyApplication.getInstance())) {
      return remoteDataSource.getMovies(sortBy);
    } else {
      return localDataSource.getMovies();
    }
  }

  public void destroyInstance() {
    localDataSource.unRegisterObservers();
  }
}