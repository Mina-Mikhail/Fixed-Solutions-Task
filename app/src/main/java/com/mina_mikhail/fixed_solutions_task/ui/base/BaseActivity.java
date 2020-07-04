package com.mina_mikhail.fixed_solutions_task.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import com.irozon.sneaker.Sneaker;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.utils.CommonUtils;
import com.mina_mikhail.fixed_solutions_task.utils.DisplayLoader;
import com.mina_mikhail.fixed_solutions_task.utils.KeyboardUtils;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkUtils;
import com.mina_mikhail.fixed_solutions_task.utils.display_message.DisplayMessage;
import com.mina_mikhail.fixed_solutions_task.utils.display_message.MessageType;
import dagger.android.AndroidInjection;
import javax.inject.Inject;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
    extends AppCompatActivity {

  @Inject
  public ViewModelProvider.Factory factory;

  @Inject
  public NetworkUtils networkUtils;

  private ProgressDialog mProgressDialog;
  private T mViewDataBinding;
  private V mViewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    injectDagger();
    performDataBinding();
    setUpViewModel();
    setUpViews();
    setUpObservables();
  }

  public T getViewDataBinding() {
    return mViewDataBinding;
  }

  public void hideKeyboard() {
    KeyboardUtils.hideSoftInput(this);
  }

  public boolean isNetworkConnected() {
    return networkUtils.isNetworkConnected();
  }

  private void injectDagger() {
    AndroidInjection.inject(this);
  }

  private void performDataBinding() {
    if (getLayoutId() != -1) {
      mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
      this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
      mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
      mViewDataBinding.executePendingBindings();
      mViewDataBinding.setLifecycleOwner(this);
    }
  }

  public void onApiFail() {
    if (isNetworkConnected()) {
      showMessage(getResources().getString(R.string.try_again));
    } else {
      onNoInternet();
    }
  }

  public void onNoInternet() {
    Sneaker.with(this)
        .setMessage(getApplicationContext().getResources().getString(R.string.no_internet))
        .sneakError();
  }

  private void showSnackBar(String message) {
    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View toastLayout = inflater.inflate(R.layout.toast, null);

    TextView text = toastLayout.findViewById(R.id.text);
    text.setText(message);

    Toast toast = new Toast(getApplicationContext());
    toast.setDuration(Toast.LENGTH_LONG);
    toast.setView(toastLayout);
    toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, toast.getXOffset(), 0);
    toast.show();

    toastLayout.findViewById(R.id.hide).setOnClickListener(v -> toast.cancel());
  }

  public void onError(String message) {
    if (message != null && !message.isEmpty()) {
      showSnackBar(message);
    } else {
      showSnackBar(getString(R.string.some_error));
    }
  }

  public void onError(@StringRes int resId) {
    onError(getString(resId));
  }

  public void showMessage(String message) {
    if (message != null && !message.isEmpty()) {
      Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    } else {
      Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_LONG).show();
    }
  }

  public void showMessage(@StringRes int resId) {
    showMessage(getString(resId));
  }

  public void showLoading() {
    hideLoading();
    mProgressDialog = CommonUtils.showLoadingDialog(this);
  }

  public void hideLoading() {
    CommonUtils.hideLoadingDialog(mProgressDialog, this);
  }

  public void initBaseObservables() {
    initMessageObservable();

    initLoaderObservable();

    initApiFailObservable();

    initHideKeyboardObservable();
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
   * Override for set view model
   *
   * @return view model instance
   */
  public abstract V getViewModel();

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