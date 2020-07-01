package com.mina_mikhail.fixed_solutions_task.data.repo;

import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.data.model.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.model.RemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.MovieDetailsLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.MovieDetailsRemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkUtils;

public class MovieDetailsRepository {

  private MovieDetailsRemoteDataSource remoteDataSource;
  private MovieDetailsLocalDataSource localDataSource;

  public MovieDetailsRepository(MovieDetailsRemoteDataSource remoteDataSource,
      MovieDetailsLocalDataSource localDataSource) {
    this.remoteDataSource = remoteDataSource;
    this.localDataSource = localDataSource;
  }

  public RemoteDataSource<MovieDetails> getMovieDetails(int movieID) {
    if (NetworkUtils.isNetworkConnected(MyApplication.getInstance())) {
      System.out.println("call------>> " + this);
      return remoteDataSource.getMovieDetails(movieID);
    } else {
      return localDataSource.getMovieDetails(movieID);
    }
  }

  public void destroyInstance(int movieID) {
    localDataSource.unRegisterObservers(movieID);
  }
}