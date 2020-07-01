package com.mina_mikhail.fixed_solutions_task.di;

import com.mina_mikhail.fixed_solutions_task.data.repo.MovieDetailsRepository;
import com.mina_mikhail.fixed_solutions_task.data.repo.PopularMoviesRepository;
import com.mina_mikhail.fixed_solutions_task.ui.movie_details.MovieDetailsViewModel;
import com.mina_mikhail.fixed_solutions_task.ui.popular_movies.PopularMoviesViewModel;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { MyApplicationModule.class, RepositoryModule.class })
public interface AppComponent {

  PopularMoviesRepository getMoviesRepository();

  MovieDetailsRepository getMovieDetailsRepository();

  void inject(PopularMoviesViewModel popularMoviesViewModel);

  void inject(MovieDetailsViewModel movieDetailsViewModel);
}