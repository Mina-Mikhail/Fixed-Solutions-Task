package com.mina_mikhail.fixed_solutions_task.di.module;

import com.mina_mikhail.fixed_solutions_task.ui.movie_details.MovieDetailsFragment;
import com.mina_mikhail.fixed_solutions_task.ui.popular_movies.PopularMoviesFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilderModule {

  @ContributesAndroidInjector
  abstract PopularMoviesFragment bindPopularMoviesFragment();

  @ContributesAndroidInjector
  abstract MovieDetailsFragment bindMovieDetailsFragment();
}