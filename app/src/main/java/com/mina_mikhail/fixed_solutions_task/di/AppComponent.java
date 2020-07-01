package com.mina_mikhail.fixed_solutions_task.di;

import com.mina_mikhail.fixed_solutions_task.data.repo.MovieDetailsRepository;
import com.mina_mikhail.fixed_solutions_task.ui.movie_details.MovieDetailsViewModel;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = { MyApplicationModule.class, RepositoryModule.class })
public interface AppComponent {

  MovieDetailsRepository getMovieDetailsRepository();

  void inject(MovieDetailsViewModel movieDetailsViewModel);
}