package com.mina_mikhail.fixed_solutions_task.ui.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mina_mikhail.fixed_solutions_task.utils.DisplayLoader;
import com.mina_mikhail.fixed_solutions_task.utils.display_message.DisplayMessage;
import com.mina_mikhail.fixed_solutions_task.utils.display_message.MessageType;

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel>
    extends Fragment {

  private BaseActivity mActivity;
  private View mRootView;
  private T mViewDataBinding;
  private V mViewModel;

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof BaseActivity) {
      this.mActivity = (BaseActivity) context;
    }
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mViewModel = getViewModel();
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
    mRootView = mViewDataBinding.getRoot();
    setHasOptionsMenu(hasOptionMenu());

    // To solve issue when replace fragment, the old fragment views still clickable and take actions but not visible
    mRootView.setOnTouchListener((v, event) -> true);

    return mRootView;
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
    mViewDataBinding.executePendingBindings();

    setUpViewModel();
    setUpViews();
    setUpObservables();
  }

  public BaseActivity getBaseActivity() {
    return mActivity;
  }

  public T getViewDataBinding() {
    return mViewDataBinding;
  }

  public void hideKeyboard() {
    if (mActivity != null) {
      mActivity.hideKeyboard();
    }
  }

  public boolean isNetworkConnected() {
    return mActivity != null && mActivity.isNetworkConnected();
  }

  protected void initBaseObservables() {
    initMessageObservable();

    initLoaderObservable();

    initApiFailObservable();

    initHideKeyboardObservable();
  }

  public void onApiFail() {
    mActivity.onApiFail();
  }

  public void onError(String message) {
    mActivity.onError(message);
  }

  public void onError(@StringRes int resId) {
    mActivity.onError(resId);
  }

  public void showMessage(String message) {
    mActivity.showMessage(message);
  }

  public void showMessage(@StringRes int resId) {
    mActivity.showMessage(resId);
  }

  public void showLoading() {
    mActivity.showLoading();
  }

  public void hideLoading() {
    mActivity.hideLoading();
  }

  protected abstract void setUpViewModel();

  protected abstract void setUpViews();

  protected abstract void setUpObservables();

  /**
   * Override for set binding variable
   *
   * @return variable id
   */
  public abstract int getBindingVariable();

  /**
   * @return layout resource id
   */
  public abstract
  @LayoutRes
  int getLayoutId();

  /**
   * Override for set option menu
   *
   * @return variable id
   */
  public abstract boolean hasOptionMenu();

  /**
   * Override for set view model
   *
   * @return view model instance
   */
  public abstract V getViewModel();

  @Override
  public void onDetach() {
    mActivity = null;
    super.onDetach();
  }

  private void initMessageObservable() {
    getViewModel().getMessageObserver().observe(this, (DisplayMessage.MessageObserver) message -> {
      if (message.getType() == MessageType.INFO_MSG) {
        if (message.getMessageResourceId() == -1) {
          showMessage(message.getMessageText());
        } else {
          showMessage(message.getMessageResourceId());
        }
      } else if (message.getType() == MessageType.ERROR_MSG) {
        if (message.getMessageResourceId() == -1) {
          onError(message.getMessageText());
        } else {
          onError(message.getMessageResourceId());
        }
      }
    });
  }

  private void initLoaderObservable() {
    getViewModel().getLoaderObserver().observe(this, (DisplayLoader.LoaderObserver) showLoader -> {
      if (showLoader) {
        showLoading();
      } else {
        hideLoading();
      }
    });
  }

  private void initApiFailObservable() {
    getViewModel().apiFail().observe(this, aVoid -> onApiFail());
  }

  private void initHideKeyboardObservable() {
    getViewModel().hideKeyboard().observe(this, aVoid -> hideKeyboard());
  }
}