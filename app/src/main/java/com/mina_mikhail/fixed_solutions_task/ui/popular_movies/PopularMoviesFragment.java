package com.mina_mikhail.fixed_solutions_task.ui.popular_movies;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.mina_mikhail.fixed_solutions_task.BR;
import com.mina_mikhail.fixed_solutions_task.R;
import com.mina_mikhail.fixed_solutions_task.data.enums.NetworkState;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;
import com.mina_mikhail.fixed_solutions_task.databinding.FragmentPopularMoviesBinding;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseFragment;
import com.mina_mikhail.fixed_solutions_task.utils.CommonUtils;

public class PopularMoviesFragment
    extends BaseFragment<FragmentPopularMoviesBinding, PopularMoviesViewModel>
    implements PopularMoviesAdapter.MoviesListener {

  private PopularMoviesViewModel mViewModel;

  private PopularMoviesAdapter moviesAdapter;

  @Override
  public int getBindingVariable() {
    return BR.viewModel;
  }

  @Override
  public int getLayoutId() {
    return R.layout.fragment_popular_movies;
  }

  @Override
  public PopularMoviesViewModel getViewModel() {
    return mViewModel;
  }

  @Override
  public boolean hasOptionMenu() {
    return false;
  }

  @Override
  protected void setUpViewModel() {
    mViewModel = new ViewModelProvider(this, factory).get(PopularMoviesViewModel.class);
    getViewDataBinding().setViewModel(getViewModel());
    initBaseObservables();
  }

  @Override
  public void onStart() {
    super.onStart();

    moviesAdapter.registerListener(this);
  }

  @Override
  public void onStop() {
    moviesAdapter.unRegisterListener();

    super.onStop();
  }

  @Override
  protected void setUpViews() {
    initMoviesRecyclerView();

    getData();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    setupToolbar();
  }

  private void setupToolbar() {
    getBaseActivity()
        .setSupportActionBar(getViewDataBinding().includedToolbar.toolbar);
    if (getBaseActivity().getSupportActionBar() != null) {
      getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getBaseActivity().getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
    getViewDataBinding().includedToolbar.toolbar
        .setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_white));
    getViewDataBinding().includedToolbar.toolbar
        .setNavigationOnClickListener(v -> getBaseActivity().onBackPressed());
    getBaseActivity().setTitle("");
    getViewDataBinding().includedToolbar.toolbarTitle
        .setText(getResources().getString(R.string.popular_movies));
  }

  private void initMoviesRecyclerView() {
    CommonUtils.configRecyclerView(getViewDataBinding().includedList.recyclerView, true);
    moviesAdapter = new PopularMoviesAdapter();
    moviesAdapter.setHasStableIds(true);
    getViewDataBinding().includedList.recyclerView.setAdapter(moviesAdapter);
  }

  private void getData() {
    showProgress();
    getViewModel().getPopularMovies();
  }

  @Override
  protected void setUpObservables() {
    getViewModel().getPopularMoviesData().getNetworkState().observe(this, state -> {
      if (state != null) {
        if (state == NetworkState.LOADED_FROM_REMOTE) {
          getViewModel().getPopularMoviesData().getData().observe(this,
              movies -> {
                showData();
                moviesAdapter
                    .submitList(getViewModel().getPopularMoviesData().getData().getValue());
              });
        } else if (state == NetworkState.LOADED_FROM_LOCAL) {
          getViewModel().getPopularMoviesData().getData().observe(this,
              movies -> {
                if (movies != null && !movies.isEmpty()) {
                  showData();
                  moviesAdapter
                      .submitList(getViewModel().getPopularMoviesData().getData().getValue());
                } else {
                  showNoData();
                }
              });
        } else if (state == NetworkState.FAILED) {
          showNoData();
        } else if (state == NetworkState.NO_INTERNET) {
          if (moviesAdapter.getCurrentList() != null && !moviesAdapter.getCurrentList().isEmpty()) {
            onNoInternet();
          } else {
            showNoInternet();
          }
        } else if (state == NetworkState.LOADING) {
          showListProgress();
        }
      }
    });
  }

  @Override
  public void onMovieClicked(Movie movie) {
    PopularMoviesFragmentDirections.ActionNext nextAction =
        PopularMoviesFragmentDirections.actionNext();
    nextAction.setMovieId(movie.getId());
    getNavController().navigate(nextAction);
  }

  private void showData() {
    getViewDataBinding().includedList.recyclerView.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.container.setVisibility(View.GONE);
    getViewDataBinding().includedList.progressBar.setVisibility(View.GONE);
    getViewDataBinding().includedList.listProgressBar.setVisibility(View.GONE);
  }

  private void showListProgress() {
    getViewDataBinding().includedList.recyclerView.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.container.setVisibility(View.GONE);
    getViewDataBinding().includedList.progressBar.setVisibility(View.GONE);
    getViewDataBinding().includedList.listProgressBar.setVisibility(View.VISIBLE);
  }

  private void showNoData() {
    getViewDataBinding().includedList.recyclerView.setVisibility(View.GONE);
    getViewDataBinding().includedList.progressBar.setVisibility(View.GONE);
    getViewDataBinding().includedList.emptyViewContainer.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.internetErrorViewContainer.setVisibility(View.GONE);
    getViewDataBinding().includedList.container.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.listProgressBar.setVisibility(View.GONE);
  }

  private void showProgress() {
    getViewDataBinding().includedList.recyclerView.setVisibility(View.GONE);
    getViewDataBinding().includedList.progressBar.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.emptyViewContainer.setVisibility(View.GONE);
    getViewDataBinding().includedList.internetErrorViewContainer.setVisibility(View.GONE);
    getViewDataBinding().includedList.container.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.listProgressBar.setVisibility(View.GONE);
  }

  private void showNoInternet() {
    getViewDataBinding().includedList.recyclerView.setVisibility(View.GONE);
    getViewDataBinding().includedList.progressBar.setVisibility(View.GONE);
    getViewDataBinding().includedList.emptyViewContainer.setVisibility(View.GONE);
    getViewDataBinding().includedList.internetErrorViewContainer.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.container.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.listProgressBar.setVisibility(View.GONE);
  }
}