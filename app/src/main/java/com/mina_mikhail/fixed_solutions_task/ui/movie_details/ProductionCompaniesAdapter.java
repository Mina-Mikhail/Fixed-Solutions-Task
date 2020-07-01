package com.mina_mikhail.fixed_solutions_task.ui.movie_details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.app.MyApplication;
import com.mina_mikhail.fixed_solutions_task.data.model.ProductionCompany;
import com.mina_mikhail.fixed_solutions_task.databinding.ItemProductionCompanyBinding;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseAdapter;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseViewHolder;
import java.util.List;

public class ProductionCompaniesAdapter
    extends BaseAdapter<ProductionCompany> {

  private List<ProductionCompany> items;

  private int lastPosition = -1;

  ProductionCompaniesAdapter(List<ProductionCompany> items) {
    super(items);
    this.items = items;
  }

  @Override
  public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
    ItemProductionCompanyBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_production_company,
        parent,
        false);
    return new ProductionCompaniesAdapter.ViewHolder(binding);
  }

  @Override
  public void onBindData(RecyclerView.ViewHolder holder, int position) {
    ViewHolder myHolder = (ViewHolder) holder;
    myHolder.onBind(position);

    // add anim to items
    setAnimation(holder.itemView, position);
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

  // To solve the problem of fast scroll
  @Override
  public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
    ViewHolder myHolder = (ViewHolder) holder;
    myHolder.clearAnimation();
    myHolder.unbind();
  }

  class ViewHolder
      extends BaseViewHolder {

    private final ItemProductionCompanyBinding itemBinding;

    ViewHolder(ItemProductionCompanyBinding itemBinding) {
      super(itemBinding.getRoot());
      this.itemBinding = itemBinding;
    }

    @Override
    public void onBind(int position) {
      itemBinding.setItem(items.get(position));

      if (position == items.size() - 1) {
        itemBinding.divider.setVisibility(View.GONE);
      } else {
        itemBinding.divider.setVisibility(View.VISIBLE);
      }
    }

    @Override
    public void unbind() {// Don't forget to unbind
      if (itemBinding != null) {
        itemBinding.unbind();
      }
    }

    @Override
    public void clearAnimation() {
      itemView.clearAnimation();
    }
  }
}