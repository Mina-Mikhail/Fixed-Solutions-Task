package com.mina_mikhail.fixed_solutions_task.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.utils.image_loader.GlideImageLoader;

public class BindingUtils {

  @BindingAdapter(value = { "load_image", "progress_view" }, requireAll = false)
  public static void loadImage(ImageView imageView, String imageURL,
      ProgressBar progressBar) {
    if (imageURL != null && !TextUtils.isEmpty(imageURL)) {
      RequestOptions options = new RequestOptions()
          .centerCrop()
          .placeholder(R.color.backgroundGray)
          .error(R.drawable.bg_no_image)
          .priority(Priority.HIGH);
      new GlideImageLoader(imageView, progressBar).load(imageURL, options, true);
    } else {
      if (progressBar != null) progressBar.setVisibility(View.GONE);
      imageView.setImageDrawable(null);
      imageView.setImageDrawable(
          imageView.getContext().getResources().getDrawable(R.drawable.bg_no_image));
      imageView.setVisibility(View.VISIBLE);
    }
  }
}