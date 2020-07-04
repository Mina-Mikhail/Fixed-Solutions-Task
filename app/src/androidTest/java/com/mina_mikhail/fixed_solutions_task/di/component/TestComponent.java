package com.mina_mikhail.fixed_solutions_task.di.component;

import android.content.Context;
import com.mina_mikhail.fixed_solutions_task.MovieDetailsDaoTest;
import com.mina_mikhail.fixed_solutions_task.MovieDetailsRepositoryTest;
import com.mina_mikhail.fixed_solutions_task.PopularMoviesDaoTest;
import com.mina_mikhail.fixed_solutions_task.di.module.TestModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = { AndroidInjectionModule.class, TestModule.class })
public interface TestComponent {

  void inject(MovieDetailsDaoTest movieDetailsDaoTest);

  void inject(MovieDetailsRepositoryTest movieDetailsRepositoryTest);

  void inject(PopularMoviesDaoTest popularMoviesDaoTest);

  @Component.Builder
  interface Builder {

    @BindsInstance
    Builder testModule(TestModule testModule);

    @BindsInstance
    TestComponent.Builder context(Context context);

    TestComponent build();
  }
}
