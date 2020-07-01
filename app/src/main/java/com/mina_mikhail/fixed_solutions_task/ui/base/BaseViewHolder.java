package com.mina_mikhail.fixed_solutions_task.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder
    extends RecyclerView.ViewHolder {

  public BaseViewHolder(View itemView) {
    super(itemView);
  }

  public abstract void onBind(int position);

  public abstract void unbind();

  public abstract void clearAnimation();
}
