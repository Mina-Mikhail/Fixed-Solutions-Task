package com.mina_mikhail.fixed_solutions_task.di.module;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.mina_mikhail.fixed_solutions_task.ui.main.MainViewModel;
import com.mina_mikhail.fixed_solutions_task.ui.movie_details.MovieDetailsViewModel;
import com.mina_mikhail.fixed_solutions_task.ui.popular_movies.PopularMoviesViewModel;
import com.mina_mikhail.fixed_solutions_task.ui.splash.SplashViewModel;
import com.mina_mikhail.fixed_solutions_task.utils.viewModelFactory.ViewModelProviderFactory;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

  @Binds
  @IntoMap
  @ViewModelKey(SplashViewModel.class)
  abstract ViewModel movieSplashViewModel(SplashViewModel vm);

  @Binds
  @IntoMap
  @ViewModelKey(MainViewModel.class)
  abstract ViewModel movieMainViewModel(MainViewModel vm);

  @Binds
  @IntoMap
  @ViewModelKey(PopularMoviesViewModel.class)
  abstract ViewModel popularMoviesViewModel(PopularMoviesViewModel vm);

  @Binds
  @IntoMap
  @ViewModelKey(MovieDetailsViewModel.class)
  abstract ViewModel movieDetailsViewModel(MovieDetailsViewModel vm);

  @Binds
  abstract ViewModelProvider.Factory bindsViewModelFactory(
      ViewModelProviderFactory viewModelProviderFactory);
}