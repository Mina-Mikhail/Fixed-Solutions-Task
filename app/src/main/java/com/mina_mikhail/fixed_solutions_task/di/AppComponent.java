package com.mina_mikhail.fixed_solutions_task.di;

import com.mina_mikhail.fixed_solutions_task.ui.movie_details.MovieDetailsViewModel;
import com.mina_mikhail.fixed_solutions_task.ui.popular_movies.PopularMoviesViewModel;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    MyApplicationModule.class, RepositoryModule.class, DataSourceFactoryModule.class
})
public interface AppComponent {

  // Popular Movies
  void inject(PopularMoviesViewModel popularMoviesViewModel);

  // Movie Details
  void inject(MovieDetailsViewModel movieDetailsViewModel);
}