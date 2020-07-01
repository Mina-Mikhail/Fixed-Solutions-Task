package com.mina_mikhail.fixed_solutions_task.di;

import com.mina_mikhail.fixed_solutions_task.data.repo.MovieDetailsRepository;
import com.mina_mikhail.fixed_solutions_task.data.repo.PopularMoviesRepository;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.MovieDetailsLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.PopularMoviesLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.MovieDetailsRemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.PopularMoviesRemoteDataSource;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModule {

  // Movies
  @Provides
  static PopularMoviesRemoteDataSource provideMoviesRemoteDataSource() {
    return new PopularMoviesRemoteDataSource();
  }

  @Provides
  static PopularMoviesLocalDataSource provideMoviesLocalDataSource() {
    return new PopularMoviesLocalDataSource();
  }

  @Provides
  static PopularMoviesRepository provideMoviesRepository(
      PopularMoviesRemoteDataSource moviesRemoteDataSource,
      PopularMoviesLocalDataSource moviesLocalDataSource) {
    return new PopularMoviesRepository(moviesRemoteDataSource, moviesLocalDataSource);
  }

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