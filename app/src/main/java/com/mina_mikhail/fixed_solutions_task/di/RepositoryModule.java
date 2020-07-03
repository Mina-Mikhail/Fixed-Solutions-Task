package com.mina_mikhail.fixed_solutions_task.di;

import androidx.paging.PagedList;
import com.mina_mikhail.fixed_solutions_task.data.repo.MovieDetailsRepository;
import com.mina_mikhail.fixed_solutions_task.data.repo.PopularMoviesRepository;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.MovieDetailsLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.PopularMoviesLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.MovieDetailsRemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.PopularMoviesRemoteDataSourceFactory;
import com.mina_mikhail.fixed_solutions_task.utils.Constants;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RepositoryModule {

  // Popular Details
  @Provides
  static PopularMoviesLocalDataSource providePopularMoviesLocalDataSource() {
    return new PopularMoviesLocalDataSource();
  }

  @Provides
  static PopularMoviesRemoteDataSourceFactory providePopularMoviesRemoteDataSourceFactory() {
    return new PopularMoviesRemoteDataSourceFactory();
  }

  @Provides
  static PagedList.Config providePagedListConfig() {
    return (new PagedList.Config.Builder())
        .setEnablePlaceholders(false)
        .setPageSize(Constants.PAGE_SIZE)
        .setPrefetchDistance(Constants.PAGE_PRE_FETCH_DISTANCE)
        .build();
  }

  @Provides
  static PopularMoviesRepository providePopularMoviesRepository(
      PopularMoviesLocalDataSource popularMoviesLocalDataSource
      , PopularMoviesRemoteDataSourceFactory moviesRemoteDataSourceFactory
      , PagedList.Config config) {
    return new PopularMoviesRepository(popularMoviesLocalDataSource
        , moviesRemoteDataSourceFactory
        , config);
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