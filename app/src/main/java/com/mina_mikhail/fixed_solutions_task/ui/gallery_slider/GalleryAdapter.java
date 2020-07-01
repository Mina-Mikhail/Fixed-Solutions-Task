package com.mina_mikhail.fixed_solutions_task.ui.gallery_slider;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoView;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.ui.gallery_slider.widgets.SliderPager;
import com.mina_mikhail.fixed_solutions_task.utils.image_loader.GlideImageLoader;
import com.mina_mikhail.fixed_solutions_task.utils.image_loader.ImageUtils;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mina-mikhail on 29/08/18.
 */

class GalleryAdapter
    extends PagerAdapter {

  private List<String> imageList;
  private LayoutInflater inflater;
  private SliderPager viewPager;
  private PhotoView imageView;
  private ProgressBar progressBar;

  private static int currentPage = 0;

  GalleryAdapter(List<String> list, SliderPager viewPager) {
    this.imageList = list;
    this.viewPager = viewPager;

    inflater = LayoutInflater.from(viewPager.getContext());
  }

  @Override
  public int getCount() {
    return imageList.size();
  }

  @NonNull
  @Override
  public Object instantiateItem(@NonNull ViewGroup container, int position) {
    ViewGroup itemView =
        (ViewGroup) inflater.inflate(R.layout.item_slider, container, false);

    setUpViews(itemView, position);

    container.addView(itemView);

    return itemView;
  }

  private void setUpViews(ViewGroup itemView, int position) {
    imageView = itemView.findViewById(R.id.photoView);
    progressBar = itemView.findViewById(R.id.loading);

    setImages(position);
  }

  private void setImages(int position) {
    if (imageList.get(position) != null && !TextUtils.isEmpty(imageList.get(position))) {
      RequestOptions options = new RequestOptions()
          .placeholder(R.color.backgroundGray)
          .error(R.drawable.bg_no_image)
          .priority(Priority.HIGH);
      new GlideImageLoader(imageView, progressBar).load(imageList.get(position), options, true);
    } else {
      if (progressBar != null) progressBar.setVisibility(View.GONE);
      ImageUtils.clearGlideCache(imageView);
      imageView.setImageDrawable(null);
      imageView.setImageDrawable(
          imageView.getContext().getResources().getDrawable(R.drawable.bg_no_image));
      imageView.setVisibility(View.VISIBLE);
    }
  }

  void setAutoSlideImages() {
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        viewPager.post(() -> {
          viewPager.setCurrentItem(currentPage % imageList.size());
          currentPage++;
        });
      }
    }, 1000, 2000);
  }

  @Override
  public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    container.removeView((View) object);
  }

  @Override
  public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
    return view == object;
  }
}