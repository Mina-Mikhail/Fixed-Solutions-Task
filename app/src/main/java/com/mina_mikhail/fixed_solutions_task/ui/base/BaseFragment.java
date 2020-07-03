package com.mina_mikhail.fixed_solutions_task.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.mina_mikhail.fixed_solutions_task.utils.DisplayLoader;
import com.mina_mikhail.fixed_solutions_task.utils.display_message.DisplayMessage;
import com.mina_mikhail.fixed_solutions_task.utils.display_message.MessageType;

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel>
    extends Fragment {

  private BaseActivity mActivity;
  private View mRootView;
  private T mViewDataBinding;
  private V mViewModel;
  private NavController navController;
  private boolean hasInitializedRootView = false;

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

    if (mRootView == null) {
      mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
      mRootView = mViewDataBinding.getRoot();
      mViewDataBinding.setLifecycleOwner(this);
      setHasOptionsMenu(hasOptionMenu());
    }

    return mRootView;
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
    mViewDataBinding.executePendingBindings();

    if (!hasInitializedRootView) {
      setUpViewModel();
      setUpViews();
      setUpObservables();
      navController = Navigation.findNavController(view);

      hasInitializedRootView = true;
    }
  }

  protected BaseActivity getBaseActivity() {
    return mActivity;
  }

  protected T getViewDataBinding() {
    return mViewDataBinding;
  }

  protected void hideKeyboard() {
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

  protected NavController getNavController() {
    return navController;
  }

  protected void onApiFail() {
    mActivity.onApiFail();
  }

  protected void onNoInternet() {
    mActivity.onNoInternet();
  }

  protected void onError(String message) {
    mActivity.onError(message);
  }

  protected void onError(@StringRes int resId) {
    mActivity.onError(resId);
  }

  protected void showMessage(String message) {
    mActivity.showMessage(message);
  }

  protected void showMessage(@StringRes int resId) {
    mActivity.showMessage(resId);
  }

  protected void showLoading() {
    mActivity.showLoading();
  }

  protected void hideLoading() {
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