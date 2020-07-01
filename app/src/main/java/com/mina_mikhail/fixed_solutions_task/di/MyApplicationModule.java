package com.mina_mikhail.fixed_solutions_task.di;

import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class MyApplicationModule {

  private final MyApplication myApplication;

  public MyApplicationModule(MyApplication myApplication) {
    this.myApplication = myApplication;
  }

  @Singleton
  @Provides
  MyApplication provideMyApplication() {
    return myApplication;
  }
}