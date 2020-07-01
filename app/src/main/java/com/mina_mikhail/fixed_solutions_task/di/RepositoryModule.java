package com.mina_mikhail.fixed_solutions_task.di;

import com.mina_mikhail.fixed_solutions_task.data.repo.MovieDetailsRepository;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.MovieDetailsLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.MovieDetailsRemoteDataSource;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModule {

  // Movie Details
  @Provides
  static MovieDetailsRemoteDataSource provideMovieDetailsRemoteDataSource() {
    return new MovieDetailsRemoteDataSource();
  }

  @Provides
  static MovieDetailsLocalDataSource provideMovieDetailsLocalDataSource() {
    return new MovieDetailsLocalDataSource();
  }

  @Provides
  static MovieDetailsRepository provideMovieDetailsRepository(
      MovieDetailsRemoteDataSource movieDetailsRemoteDataSource,
      MovieDetailsLocalDataSource movieDetailsLocalDataSource) {
    return new MovieDetailsRepository(movieDetailsRemoteDataSource, movieDetailsLocalDataSource);
  }
}