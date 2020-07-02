package com.mina_mikhail.fixed_solutions_task.ui.popular_movies;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
  private NavController navController;

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
    mViewModel = new ViewModelProvider(this).get(PopularMoviesViewModel.class);
    getViewDataBinding().setViewModel(getViewModel());
    initBaseObservables();
  }

  @Override
  public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    navController = Navigation.findNavController(view);
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
    getViewDataBinding().includedList.reloadBtn.setOnClickListener(v -> getData());
  }

  private void getData() {
    showProgress();
    getViewModel().getMovies();
  }

  @Override
  protected void setUpObservables() {
    getViewModel().remoteMoviePagedList().observe(this, movies -> moviesAdapter.submitList(movies));

    getViewModel().localMoviePagedList().observe(this, movies -> {
      if (movies != null && !movies.isEmpty()) {
        moviesAdapter.submitList(movies);
        showData();
      } else {
        showNoData();
      }
    });

    getViewModel().networkStatusLiveData().observe(this, networkStatus -> {
      if (networkStatus != null) {
        if (networkStatus.getState() == NetworkState.LOADING) {
          showListProgress();
        } else if (networkStatus.getState() == NetworkState.LOADED) {
          showData();
        } else if (networkStatus.getState() == NetworkState.FAILED) {
          showNoData();
        } else if (networkStatus.getState() == NetworkState.NO_INTERNET) {
          if (moviesAdapter.getCurrentList() != null && !moviesAdapter.getCurrentList().isEmpty()) {
            onNoInternet();
          } else {
            showNoInternet();
          }
        }
      }
    });
  }

  @Override
  public void onMovieClicked(Movie movie) {
    PopularMoviesFragmentDirections.ActionNext nextAction =
        PopularMoviesFragmentDirections.actionNext();
    nextAction.setMovieId(movie.getId());
    navController.navigate(nextAction);
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
    getViewDataBinding().includedList.reloadBtn.setVisibility(View.GONE);
    getViewDataBinding().includedList.container.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.listProgressBar.setVisibility(View.GONE);
  }

  private void showProgress() {
    getViewDataBinding().includedList.recyclerView.setVisibility(View.GONE);
    getViewDataBinding().includedList.progressBar.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.emptyViewContainer.setVisibility(View.GONE);
    getViewDataBinding().includedList.internetErrorViewContainer.setVisibility(View.GONE);
    getViewDataBinding().includedList.reloadBtn.setVisibility(View.GONE);
    getViewDataBinding().includedList.container.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.listProgressBar.setVisibility(View.GONE);
  }

  private void showNoInternet() {
    getViewDataBinding().includedList.recyclerView.setVisibility(View.GONE);
    getViewDataBinding().includedList.progressBar.setVisibility(View.GONE);
    getViewDataBinding().includedList.emptyViewContainer.setVisibility(View.GONE);
    getViewDataBinding().includedList.internetErrorViewContainer.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.reloadBtn.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.container.setVisibility(View.VISIBLE);
    getViewDataBinding().includedList.listProgressBar.setVisibility(View.GONE);
  }
}