package com.mina_mikhail.fixed_solutions_task.ui.splash;

import android.arch.lifecycle.LiveData;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseViewModel;
import com.mina_mikhail.fixed_solutions_task.utils.SingleLiveEvent;

public class SplashViewModel
    extends BaseViewModel {

  private SingleLiveEvent<Void> startApp;

  public SplashViewModel() {
    startApp = new SingleLiveEvent<>();
  }

  public void onStartClicked() {
    startApp.call();
  }

  LiveData<Void> shouldStartApp() {
    return startApp;
  }
}