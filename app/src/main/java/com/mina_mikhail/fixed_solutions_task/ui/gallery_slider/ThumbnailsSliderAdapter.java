package com.mina_mikhail.fixed_solutions_task.ui.gallery_slider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.data.model.other.ThumbnailImages;
import com.mina_mikhail.fixed_solutions_task.utils.image_loader.GlideImageLoader;
import com.mina_mikhail.fixed_solutions_task.utils.image_loader.ImageUtils;
import java.util.List;

public class ThumbnailsSliderAdapter
    extends RecyclerView.Adapter<ThumbnailsSliderAdapter.CustomViewHolder> {

  private List<ThumbnailImages> arrayDetails;

  private ThumbnailsSliderListener listener;

  private int lastPosition = -1;

  private boolean onBind;

  ThumbnailsSliderAdapter(List<ThumbnailImages> arrayDetails) {
    this.arrayDetails = arrayDetails;
  }

  @NonNull @Override
  public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new CustomViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thumbnail, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull final CustomViewHolder holder, int i) {

    onBind = true;
    if (arrayDetails.get(i).isSelected()) {
      holder.flContainer
          .setBackgroundColor(MyApplication.getInstance().getResources().getColor(R.color.white));
      holder.ivOverlay.setVisibility(View.INVISIBLE);
    } else {
      holder.flContainer.setBackgroundColor(
          MyApplication.getInstance().getResources().getColor(R.color.transparent));
      holder.ivOverlay.setVisibility(View.VISIBLE);
    }
    onBind = false;

    holder.flContainer.setOnClickListener(v -> {
      if (!onBind) {
        listener.onThumbnailClicked(holder.getAdapterPosition());
      }
    });

    if (arrayDetails.get(i).getImage() != null && !TextUtils
        .isEmpty(arrayDetails.get(i).getImage())) {
      RequestOptions options = new RequestOptions()
          .centerCrop()
          .placeholder(R.color.backgroundGray)
          .error(R.drawable.bg_no_image)
          .priority(Priority.HIGH);
      new GlideImageLoader(holder.ivImage, holder.progressBar)
          .load(arrayDetails.get(i).getImage(), options, true);
    } else {
      if (holder.progressBar != null) holder.progressBar.setVisibility(View.GONE);
      ImageUtils.clearGlideCache(holder.ivImage);
      holder.ivImage.setImageDrawable(null);
      holder.ivImage.setImageDrawable(
          holder.ivImage.getContext().getResources().getDrawable(R.drawable.bg_no_image));
      holder.ivImage.setVisibility(View.VISIBLE);
    }

    // add anim to items
    setAnimation(holder.itemView, i);
  }

  public void makeItemSelected(int position) {
    for (int i = 0; i < arrayDetails.size(); i++) {
      arrayDetails.get(i).setSelected(false);
    }

    arrayDetails.get(position).setSelected(true);
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    return arrayDetails.size();
  }

  // start animation
  private void setAnimation(View viewToAnimate, int position) {
    // If the bound view wasn't previously displayed on screen, it's animated
    if (position > lastPosition) {
      Animation animation = AnimationUtils.loadAnimation(MyApplication.getInstance(),
          (position > lastPosition) ? R.anim.translate : R.anim.slide_up);
      viewToAnimate.startAnimation(animation);
      lastPosition = position;
    }
  }

  //to solve the problem of fast scroll
  @Override
  public void onViewDetachedFromWindow(@NonNull CustomViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
    holder.clearAnimation();
  }

  public void setThumbnailsSliderListener(ThumbnailsSliderListener listener) {
    this.listener = listener;
  }

  public interface ThumbnailsSliderListener {
    void onThumbnailClicked(int position);
  }

  class CustomViewHolder
      extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_image)
    RoundedImageView ivImage;

    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    @BindView(R.id.iv_overlay)
    View ivOverlay;

    @BindView(R.id.loading)
    ProgressBar progressBar;

    CustomViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    void clearAnimation() {
      itemView.clearAnimation();
    }
  }
}