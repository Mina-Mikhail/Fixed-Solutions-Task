package com.mina_mikhail.fixed_solutions_task.ui.movie_details;

import androidx.lifecycle.LiveData;
import com.mina_mikhail.fixed_solutions_task.data.model.api.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.model.other.RemoteDataSource;
import com.mina_mikhail.fixed_solutions_task.data.repo.MovieDetailsRepository;
import com.mina_mikhail.fixed_solutions_task.ui.base.BaseViewModel;
import com.mina_mikhail.fixed_solutions_task.utils.SingleLiveEvent;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class MovieDetailsViewModel
    extends BaseViewModel {

  private MovieDetailsRepository repository;

  private RemoteDataSource<MovieDetails> movie;
  private int movieID;

  private SingleLiveEvent<List<String>> onPosterClicked;
  private SingleLiveEvent<List<String>> onBackdropClicked;

  @Inject
  public MovieDetailsViewModel(MovieDetailsRepository repository) {
    this.repository = repository;

    movie = new RemoteDataSource<>();

    onPosterClicked = new SingleLiveEvent<>();
    onBackdropClicked = new SingleLiveEvent<>();
  }

  void getMovieDetails(int movieID) {
    this.movieID = movieID;
    movie = repository.getMovieDetails(movieID);
  }

  RemoteDataSource<MovieDetails> getMovieDetailsData() {
    return movie;
  }

  public void onPosterClicked(String posterPath, String backdropPath) {
    List<String> images = new ArrayList<>();
    images.add(posterPath);
    images.add(backdropPath);
    onPosterClicked.setValue(images);
  }

  public void onBackdropClicked(String posterPath, String backdropPath) {
    List<String> images = new ArrayList<>();
    images.add(posterPath);
    images.add(backdropPath);
    onBackdropClicked.setValue(images);
  }

  LiveData<List<String>> onPosterClicked() {
    return onPosterClicked;
  }

  LiveData<List<String>> onBackdropClicked() {
    return onBackdropClicked;
  }
}