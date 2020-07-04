package com.mina_mikhail.fixed_solutions_task.ui.popular_movies;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;
import com.mina_mikhail.fixed_solutions_task.data.model.other.RemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.repo.PopularMoviesRepository;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseViewModel;
import javax.inject.Inject;

public class PopularMoviesViewModel
    extends BaseViewModel {

  private PopularMoviesRepository moviesRepository;
  private RemoteDataSource<LiveData<PagedList<Movie>>> popularMovies;

  @Inject
  public PopularMoviesViewModel(PopularMoviesRepository moviesRepository) {
    this.moviesRepository = moviesRepository;
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