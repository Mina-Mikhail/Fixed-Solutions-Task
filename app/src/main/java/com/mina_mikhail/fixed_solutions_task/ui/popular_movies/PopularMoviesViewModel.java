package com.mina_mikhail.fixed_solutions_task.ui.popular_movies;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.AppDatabase;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.PopularMoviesDataSourceFactory;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseViewModel;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkUtils;

public class PopularMoviesViewModel
    extends BaseViewModel {

  private LiveData<PagedList<Movie>> moviePagedList;

  public PopularMoviesViewModel() {

  }

  void getMovies() {
    if (NetworkUtils.isNetworkConnected(MyApplication.getInstance())) {
      PopularMoviesDataSourceFactory moviesDataSourceFactory = new PopularMoviesDataSourceFactory();
      moviesDataSourceFactory.getItemLiveDataSource();

      PagedList.Config pageConfig = (new PagedList.Config.Builder())
          .setEnablePlaceholders(false)
          .setPageSize(20)
          .setPrefetchDistance(4)
          .build();

      moviePagedList =
          (new LivePagedListBuilder<Long, Movie>(moviesDataSourceFactory, pageConfig)).build();
    } else {
      // TODO: Get data from room
    }
  }

  LiveData<PagedList<Movie>> moviePagedList() {
    return moviePagedList;
  }

  @Override
  protected void onCleared() {
    AppDatabase.destroyInstance();
    super.onCleared();
  }
}