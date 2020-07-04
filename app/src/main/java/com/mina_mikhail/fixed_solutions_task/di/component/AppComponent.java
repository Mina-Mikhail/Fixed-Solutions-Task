package com.mina_mikhail.fixed_solutions_task.di.component;

import android.content.Context;
import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.di.module.AppModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = { AndroidInjectionModule.class, AppModule.class })
public interface AppComponent {

  void inject(MyApplication appClass);

  @Component.Builder
  interface Builder {

    @BindsInstance
    AppComponent.Builder application(MyApplication application);

    @BindsInstance
    AppComponent.Builder context(Context context);

    AppComponent build();
  }
}
