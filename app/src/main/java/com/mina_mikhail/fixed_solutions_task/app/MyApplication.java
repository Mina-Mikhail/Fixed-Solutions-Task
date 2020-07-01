package com.mina_mikhail.fixed_solutions_task.app;

import android.app.Application;
import android.os.StrictMode;
import com.mina_mikhail.fixed_solutions_task.BuildConfig;
import com.mina_mikhail.fixed_solutions_task.di.AppComponent;
import com.mina_mikhail.fixed_solutions_task.di.DaggerAppComponent;
import com.mina_mikhail.fixed_solutions_task.di.MyApplicationModule;
import io.reactivex.internal.functions.Functions;
import io.reactivex.plugins.RxJavaPlugins;

public class MyApplication
    extends Application {

  private static MyApplication INSTANCE;

  private AppComponent appComponent;

  public static MyApplication getInstance() {
    return INSTANCE;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    INSTANCE = this;

    initDagger();
    setStrictMode();
    initRXJava();
  }

  private void initDagger() {
    appComponent = DaggerAppComponent.builder()
        .myApplicationModule(new MyApplicationModule(this))
        .build();
  }

  private void setStrictMode() {
    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
          .detectDiskWrites()
          .detectNetwork()   // or .detectAll() for all detectable problems
          .penaltyLog()
          .build());
      StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
          .detectLeakedClosableObjects()
          .penaltyLog()
          .build());
    }
  }

  private void initRXJava() {
    RxJavaPlugins.setErrorHandler(Functions.emptyConsumer());
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }
}