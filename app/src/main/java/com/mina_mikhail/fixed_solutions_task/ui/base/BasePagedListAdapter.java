package com.mina_mikhail.fixed_solutions_task.ui.base;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BasePagedListAdapter<T>
    extends PagedListAdapter<T, RecyclerView.ViewHolder> {

  public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);

  public abstract void onBindData(RecyclerView.ViewHolder holder, int position);

  public BasePagedListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
    super(diffCallback);
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return setViewHolder(parent);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    onBindData(holder, position);
  }

  @Override
  public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
  }

  @Override
  public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onDetachedFromRecyclerView(recyclerView);
  }
}