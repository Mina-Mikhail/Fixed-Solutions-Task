package com.mina_mikhail.fixed_solutions_task.ui.popular_movies;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;
import com.mina_mikhail.fixed_solutions_task.data.model.other.RemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.repo.PopularMoviesRepository;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseViewModel;
import javax.inject.Inject;

public class PopularMoviesViewModel
    extends BaseViewModel {

  @Inject
  PopularMoviesRepository moviesRepository;

  private RemoteDataSource<LiveData<PagedList<Movie>>> popularMovies;

  public PopularMoviesViewModel() {
    MyApplication.getInstance().getAppComponent().inject(this);

    popularMovies = new RemoteDataSource<>();
  }

  void getPopularMovies() {
    popularMovies = moviesRepository.getPopularMovies();
  }

  RemoteDataSource<LiveData<PagedList<Movie>>> getPopularMoviesData() {
    return popularMovies;
  }

  @Override
  protected void onCleared() {
    moviesRepository.unRegisterObservers();
    super.onCleared();
  }
}