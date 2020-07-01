package com.mina_mikhail.fixed_solutions_task.utils;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseActivity;
import java.util.Locale;

public final class CommonUtils {

  public static ProgressDialog showLoadingDialog(BaseActivity activity) {
    if (activity == null || activity.isFinishing()) {
      return null;
    }

    ProgressDialog progressDialog = new ProgressDialog(activity);
    progressDialog.show();
    if (progressDialog.getWindow() != null) {
      progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
    progressDialog.setContentView(R.layout.progress_dialog);

    ProgressBar progressBar = progressDialog.findViewById(R.id.loading);
    Sprite wave = new Wave();
    wave.setColor(activity.getResources().getColor(R.color.colorAccent));
    progressBar.setIndeterminateDrawable(wave);

    progressDialog.setIndeterminate(true);
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    return progressDialog;
  }

  public static void hideLoadingDialog(ProgressDialog mProgressDialog, BaseActivity activity) {
    if (activity != null && !activity.isFinishing() && mProgressDialog != null && mProgressDialog
        .isShowing()) {
      mProgressDialog.dismiss();
    }
  }

  public static void configRecyclerView(RecyclerView recyclerView, boolean isVertical) {
    recyclerView.setHasFixedSize(true);
    recyclerView.setNestedScrollingEnabled(false);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    if (isVertical) {
      recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    } else {
      recyclerView.setLayoutManager(
          new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL,
              false));
    }
  }

  // Converts the number to K, M suffix
  // Ex: 5500 will be displayed as 5.5k
  public static String convertToSuffix(long count) {
    if (count < 1000) return "" + count;
    int exp = (int) (Math.log(count) / Math.log(1000));
    return String.format(Locale.US, "%.1f%c",
        count / Math.pow(1000, exp),
        "kmgtpe".charAt(exp - 1));
  }
}