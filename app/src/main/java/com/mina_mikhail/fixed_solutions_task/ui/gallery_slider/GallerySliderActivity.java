package com.mina_mikhail.fixed_solutions_task.ui.gallery_slider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProviders;
import com.mina_mikhail.fixed_solutions_task.BR;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.data.model.other.ThumbnailImages;
import com.mina_mikhail.fixed_solutions_task.databinding.ActivityGallerySliderBinding;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseActivity;
import com.mina_mikhail.fixed_solutions_task.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import org.parceler.Parcels;

public class GallerySliderActivity
    extends BaseActivity<ActivityGallerySliderBinding, GallerySliderViewModel> {

  public static void open(Context context, List<String> imagesList, int clickedPosition) {
    Bundle bundle = new Bundle();
    bundle.putParcelable(Constants.KEY_IMAGES_LIST, Parcels.wrap(imagesList));
    Intent intent = new Intent(context, GallerySliderActivity.class);
    intent.putExtra(Constants.KEY_IMAGES_LIST, bundle);
    intent.putExtra(Constants.KEY_CLICKED_POSITION, clickedPosition);
    context.startActivity(intent);
  }

  private GallerySliderViewModel mViewModel;

  private List<String> sliderImages = new ArrayList<>();
  private List<ThumbnailImages> thumbnailImages = new ArrayList<>();
  private int clickedPosition;

  @Override
  public int getBindingVariable() {
    return BR.viewModel;
  }

  @Override
  public int getLayoutId() {
    return R.layout.activity_gallery_slider;
  }

  @Override
  public GallerySliderViewModel getViewModel() {
    return mViewModel;
  }

  @Override
  protected void setUpViewModel() {
    mViewModel = new GallerySliderViewModel();
    mViewModel = ViewModelProviders.of(this).get(GallerySliderViewModel.class);
    getViewDataBinding().setViewModel(getViewModel());
    initBaseObservables();
  }

  @Override
  protected void setUpViews() {
    setupToolbar();

    getIntentData();

    initRestaurantGallery();
  }

  private void setupToolbar() {
    setSupportActionBar(getViewDataBinding().includedToolbar.toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    getViewDataBinding().includedToolbar.toolbar
        .setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_white));
    getViewDataBinding().includedToolbar.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    setTitle("");
  }

  private void getIntentData() {
    Bundle bundle = this.getIntent().getBundleExtra(Constants.KEY_IMAGES_LIST);
    if (bundle != null) {
      sliderImages = Parcels.unwrap(bundle.getParcelable(Constants.KEY_IMAGES_LIST));
    }

    clickedPosition = getIntent().getIntExtra(Constants.KEY_CLICKED_POSITION, 0);
  }

  private void initRestaurantGallery() {
    thumbnailImages.add(new ThumbnailImages(sliderImages.get(0), true));
    if (sliderImages.size() > 1) {
      for (int i = 1; i < sliderImages.size(); i++) {
        thumbnailImages.add(new ThumbnailImages(sliderImages.get(i), false));
      }
    }

    if (sliderImages.size() == 0) {
      hideSlider();
    } else {
      showSlider();

      new GallerySliderHelper.Builder()
          .setImagesSlider(getViewDataBinding().spSlider)
          .setSliderImages(sliderImages)
          .setThumbnailsRecyclerView(getViewDataBinding().rvThumbnails)
          .setThumbnailImages(thumbnailImages)
          .setClickedPosition(clickedPosition)
          .create();
    }
  }

  private void showSlider() {
    getViewDataBinding().llSlider.setVisibility(View.VISIBLE);
    getViewDataBinding().tvNoImages.setVisibility(View.VISIBLE);
  }

  private void hideSlider() {
    getViewDataBinding().llSlider.setVisibility(View.GONE);
    getViewDataBinding().tvNoImages.setVisibility(View.GONE);
  }

  @Override
  protected void setUpObservables() {

  }
}