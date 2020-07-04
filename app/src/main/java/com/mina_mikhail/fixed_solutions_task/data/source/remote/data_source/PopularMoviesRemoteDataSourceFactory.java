package com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import javax.inject.Inject;
import org.jetbrains.annotations.NotNull;

public class PopularMoviesRemoteDataSourceFactory
    extends DataSource.Factory {

  private PopularMoviesRemoteDataSource moviesDataSource;
  private MutableLiveData<PopularMoviesRemoteDataSource> itemLiveDataSource;

  @Inject
  public PopularMoviesRemoteDataSourceFactory(PopularMoviesRemoteDataSource moviesDataSource) {
    this.moviesDataSource = moviesDataSource;
    itemLiveDataSource = new MutableLiveData<>();
  }

  @NotNull
  @Override
  public DataSource create() {
    itemLiveDataSource.postValue(moviesDataSource);
    return moviesDataSource;
  }

  public void setSortType(String sortType) {
    moviesDataSource.setSortType(sortType);
  }

  public MutableLiveData<PopularMoviesRemoteDataSource> getItemLiveDataSource() {
    return itemLiveDataSource;
  }
}
