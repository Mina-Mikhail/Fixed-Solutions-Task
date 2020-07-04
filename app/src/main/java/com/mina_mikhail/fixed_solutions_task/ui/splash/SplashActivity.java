package com.mina_mikhail.fixed_solutions_task.ui.splash;

import androidx.lifecycle.ViewModelProvider;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.databinding.ActivitySplashBinding;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseActivity;
import com.mina_mikhail.fixed_solutions_task.ui.main.MainActivity;

public class SplashActivity
    extends BaseActivity<ActivitySplashBinding, SplashViewModel> {

  private SplashViewModel mViewModel;

  @Override
  public int getBindingVariable() {
    return com.mina_mikhail.fixed_solutions_task.BR.viewModel;
  }

  @Override
  public int getLayoutId() {
    return R.layout.activity_splash;
  }

  @Override
  public SplashViewModel getViewModel() {
    return mViewModel;
  }

  @Override
  protected void setUpViewModel() {
    mViewModel = new SplashViewModel();
    mViewModel = new ViewModelProvider(this, factory).get(SplashViewModel.class);
    getViewDataBinding().setViewModel(getViewModel());
    initBaseObservables();
  }

  @Override
  protected void setUpViews() {

  }

  @Override
  protected void setUpObservables() {
    getViewModel().shouldStartApp().observe(this, v -> MainActivity.open(this));
  }
}