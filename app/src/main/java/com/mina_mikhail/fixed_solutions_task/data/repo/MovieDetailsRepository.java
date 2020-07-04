package com.mina_mikhail.fixed_solutions_task.data.repo;

import com.mina_mikhail.fixed_solutions_task.data.model.api.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.model.other.RemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.MovieDetailsLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.MovieDetailsRemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkUtils;
import javax.inject.Inject;

public class MovieDetailsRepository {

  private MovieDetailsRemoteDataSource remoteDataSource;
  private MovieDetailsLocalDataSource localDataSource;
  private NetworkUtils networkUtils;

  @Inject
  public MovieDetailsRepository(MovieDetailsRemoteDataSource remoteDataSource
      , MovieDetailsLocalDataSource localDataSource
      , NetworkUtils networkUtils) {
    this.remoteDataSource = remoteDataSource;
    this.localDataSource = localDataSource;
    this.networkUtils = networkUtils;
  }

  public RemoteDataSource<MovieDetails> getMovieDetails(int movieID) {
    if (networkUtils.isNetworkConnected()) {
      return remoteDataSource.getMovieDetails(movieID);
    } else {
      return getMovieDetailsFromLocal(movieID);
    }
  }

  public void insertMovieDetailsToLocal(MovieDetails movieDetails) {
    localDataSource.insertMovie(movieDetails);
  }

  public RemoteDataSource<MovieDetails> getMovieDetailsFromLocal(int movieID) {
    return localDataSource.getMovieDetails(movieID);
  }

  public void destroyInstance(int movieID) {
    localDataSource.unRegisterObservers(movieID);
  }
}