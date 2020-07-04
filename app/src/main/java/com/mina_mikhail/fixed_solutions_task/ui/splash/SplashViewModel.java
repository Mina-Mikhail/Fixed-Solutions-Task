package com.mina_mikhail.fixed_solutions_task.ui.splash;

import androidx.lifecycle.LiveData;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseViewModel;
import com.mina_mikhail.fixed_solutions_task.utils.SingleLiveEvent;
import javax.inject.Inject;

public class SplashViewModel
    extends BaseViewModel {

  private SingleLiveEvent<Void> startApp;

  @Inject
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