package com.mina_mikhail.fixed_solutions_task.app;

import android.app.Application;
import android.os.StrictMode;
import androidx.annotation.VisibleForTesting;
import com.mina_mikhail.fixed_solutions_task.BuildConfig;
import com.mina_mikhail.fixed_solutions_task.di.component.AppComponent;
import com.mina_mikhail.fixed_solutions_task.di.component.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import io.reactivex.internal.functions.Functions;
import io.reactivex.plugins.RxJavaPlugins;
import javax.inject.Inject;

public class MyApplication
    extends Application
    implements HasAndroidInjector {

  private static MyApplication INSTANCE;

  @Inject
  DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

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
        .application(this)
        .context(this.getApplicationContext())
        .build();
    appComponent.inject(this);
  }

  @Override
  public AndroidInjector<Object> androidInjector() {
    return dispatchingAndroidInjector;
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

  @VisibleForTesting
  public void setApplicationComponent(AppComponent appComponent) {
    this.appComponent = appComponent;
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }
}