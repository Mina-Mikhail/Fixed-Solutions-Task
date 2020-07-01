package com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import org.jetbrains.annotations.NotNull;

public class PopularMoviesDataSourceFactory
    extends DataSource.Factory {

  private MutableLiveData<PopularMoviesRemoteDataSource> itemLiveDataSource;

  public PopularMoviesDataSourceFactory() {
    itemLiveDataSource = new MutableLiveData<>();
  }

  @NotNull
  @Override
  public DataSource create() {
    PopularMoviesRemoteDataSource moviesDataSource = new PopularMoviesRemoteDataSource();
    itemLiveDataSource.postValue(moviesDataSource);
    return moviesDataSource;
  }

  public MutableLiveData<PopularMoviesRemoteDataSource> getItemLiveDataSource() {
    return itemLiveDataSource;
  }
}
