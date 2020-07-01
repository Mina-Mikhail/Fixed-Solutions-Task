package com.mina_mikhail.fixed_solutions_task.ui.popular_movies;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;
import com.mina_mikhail.fixed_solutions_task.databinding.ItemMovieBinding;
import com.mina_mikhail.fixed_solutions_task.ui.base.BasePagedListAdapter;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseViewHolder;

public class PopularMoviesAdapter
    extends BasePagedListAdapter<Movie> {

  private MoviesListener listener;

  private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
    @Override
    public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
      return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
      return oldItem.getId() == newItem.getId();
    }
  };

  PopularMoviesAdapter() {
    super(DIFF_CALLBACK);
  }

  @Override
  public RecyclerView.ViewHolder setViewHolder(ViewGroup parent) {
    ItemMovieBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(parent.getContext()),
        R.layout.item_movie,
        parent,
        false);
    return new ViewHolder(binding);
  }

  @Override
  public void onBindData(RecyclerView.ViewHolder holder, int position) {
    ViewHolder myHolder = (ViewHolder) holder;
    myHolder.onBind(position);
  }

  // To solve the problem of fast scroll
  @Override
  public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
    super.onViewDetachedFromWindow(holder);
    ViewHolder myHolder = (ViewHolder) holder;
    myHolder.clearAnimation();
    myHolder.unbind();
  }

  @Override
  public int getItemCount() {
    return (getCurrentList() == null ? 0 : getCurrentList().size());
  }

  @Override
  public long getItemId(int position) {
    // To solve blinking after notifyDataSetChanged()
    return getItem(position).getId();
  }

  public interface MoviesListener {
    void onMovieClicked(Movie movie);
  }

  void registerListener(MoviesListener listener) {
    this.listener = listener;
  }

  void unRegisterListener() {
    listener = null;
  }

  class ViewHolder
      extends BaseViewHolder {

    private final ItemMovieBinding itemBinding;

    ViewHolder(ItemMovieBinding itemBinding) {
      super(itemBinding.getRoot());
      this.itemBinding = itemBinding;
    }

    @Override
    public void onBind(int position) {
      itemBinding.setItem(getItem(position));
      itemBinding.setListener(listener);

      itemBinding.rateAmount.setRating(getItem(position).getVote_average());
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