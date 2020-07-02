package com.mina_mikhail.fixed_solutions_task.di;

import androidx.paging.PagedList;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.PopularMoviesDataSourceFactory;
import com.mina_mikhail.fixed_solutions_task.utils.Constants;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class DataSourceFactoryModule {

  // Popular Movies
  @Provides
  static PopularMoviesDataSourceFactory providePopularMoviesDataSourceFactory() {
    return new PopularMoviesDataSourceFactory();
  }

  @Provides
  static PagedList.Config providePagedListConfig() {
    return (new PagedList.Config.Builder())
        .setEnablePlaceholders(false)
        .setPageSize(Constants.PAGE_SIZE)
        .setPrefetchDistance(Constants.PAGE_PRE_FETCH_DISTANCE)
        .build();
  }
}