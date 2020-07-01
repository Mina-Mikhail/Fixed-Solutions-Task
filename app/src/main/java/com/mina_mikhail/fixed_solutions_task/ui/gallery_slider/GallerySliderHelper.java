package com.mina_mikhail.fixed_solutions_task.ui.gallery_slider;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import com.mina_mikhail.fixed_solutions_task.data.model.other.ThumbnailImages;
import com.mina_mikhail.fixed_solutions_task.ui.gallery_slider.widgets.SliderPager;
import com.mina_mikhail.fixed_solutions_task.utils.CommonUtils;
import com.mina_mikhail.fixed_solutions_task.utils.ZoomOutTransformation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mina Mikhail on 1/09/2018.
 */

public class GallerySliderHelper {

  private int lastPosition;
  private int clickedPosition;

  // Slider Data
  private List<String> images;
  private SliderPager imagesSlider;

  // Thumbnails Data
  private List<ThumbnailImages> thumbnailImages;
  private RecyclerView rvThumbnails;
  private ThumbnailsSliderAdapter thumbnailsSliderAdapter;

  private GallerySliderHelper(final Builder builder) {
    images = builder.images;
    thumbnailImages = builder.thumbnailImages;
    imagesSlider = builder.imagesSlider;
    rvThumbnails = builder.rvThumbnails;
    clickedPosition = builder.clickedPosition;

    showSlider();
  }

  private void showSlider() {
    configImagesSlider();

    configThumbnailsRecyclerView();

    imagesSlider.setCurrentItem(clickedPosition, true);
    rvThumbnails.scrollToPosition(clickedPosition);
  }

  private void configImagesSlider() {
    GalleryAdapter imagesSliderAdapter = new GalleryAdapter(images, imagesSlider);
    imagesSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      public void onPageScrollStateChanged(int state) {
      }

      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      public void onPageSelected(int position) {
        thumbnailsSliderAdapter.makeItemSelected(position);

        if (position > lastPosition) {
          rvThumbnails.scrollToPosition(position + 1);
        } else {
          if (position == 0) {
            rvThumbnails.scrollToPosition(position);
          } else {
            rvThumbnails.scrollToPosition(position - 1);
          }
        }
        lastPosition = position;
      }
    });

    // Add animation to images slider
    imagesSlider.setPageTransformer(true, new ZoomOutTransformation());
    imagesSlider.setAdapter(imagesSliderAdapter);
  }

  private void configThumbnailsRecyclerView() {
    CommonUtils.configRecyclerView(rvThumbnails, false);
    thumbnailsSliderAdapter = new ThumbnailsSliderAdapter(thumbnailImages);
    rvThumbnails.setAdapter(thumbnailsSliderAdapter);

    ThumbnailsSliderAdapter.ThumbnailsSliderListener listener =
        position -> imagesSlider.setCurrentItem(position, true);
    thumbnailsSliderAdapter.setThumbnailsSliderListener(listener);
  }

  public static class Builder {
    private int clickedPosition;

    // Slider Data
    private SliderPager imagesSlider;
    private List<String> images = new ArrayList<>();

    // Thumbnails Data
    private RecyclerView rvThumbnails;
    private List<ThumbnailImages> thumbnailImages = new ArrayList<>();

    public Builder() {

    }

    public Builder setImagesSlider(SliderPager imagesSlider) {
      this.imagesSlider = imagesSlider;
      return this;
    }

    public Builder setSliderImages(List<String> images) {
      this.images = images;
      return this;
    }

    public Builder setClickedPosition(int clickedPosition) {
      this.clickedPosition = clickedPosition;
      return this;
    }

    public Builder setThumbnailsRecyclerView(RecyclerView rvThumbnails) {
      this.rvThumbnails = rvThumbnails;
      return this;
    }

    public Builder setThumbnailImages(
        List<ThumbnailImages> thumbnailImages) {
      this.thumbnailImages = thumbnailImages;
      return this;
    }

    public GallerySliderHelper create() {
      return new GallerySliderHelper(this);
    }
  }
}