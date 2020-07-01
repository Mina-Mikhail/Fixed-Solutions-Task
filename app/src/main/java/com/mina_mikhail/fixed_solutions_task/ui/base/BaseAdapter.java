package com.mina_mikhail.fixed_solutions_task.ui.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import java.util.List;

public abstract class BaseAdapter<T>
    extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<T> items;

  public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent);

  public abstract void onBindData(RecyclerView.ViewHolder holder, int position);

  public BaseAdapter(List<T> items) {
    this.items = items;
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
  public int getItemCount() {
    return items.size();
  }

  @Override
  public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
  }

  public void addItems(List<T> items) {
    this.items = items;
    this.notifyDataSetChanged();
  }

  public void replaceItems(List<T> items) {
    this.items.clear();
    this.items.addAll(items);
    this.notifyDataSetChanged();
  }

  public T getItem(int position) {
    return items.get(position);
  }

  @Override
  public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onDetachedFromRecyclerView(recyclerView);
  }
}