package com.mina_mikhail.fixed_solutions_task;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.mina_mikhail.fixed_solutions_task.data.enums.NetworkState;
import com.mina_mikhail.fixed_solutions_task.data.model.api.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.repo.MovieDetailsRepository;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.data_source.MovieDetailsLocalDataSource;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.data_source.MovieDetailsRemoteDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MovieDetailsRepositoryTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private MovieDetailsRepository movieDetailsRepository;
  private int movieID = 101;
  private String movieName = "Marvel Avengers";

  @Before
  public void initRepository() {
    movieDetailsRepository = new MovieDetailsRepository(new MovieDetailsRemoteDataSource(),
        new MovieDetailsLocalDataSource());
  }

  @Test
  public void insertMovieDetails() {
    MovieDetails movieDetails = new MovieDetails();
    movieDetails.setId(movieID);
    movieDetails.setTitle(movieName);

    movieDetailsRepository.insertMovieDetailsToLocal(movieDetails);

    // Assert
    movieDetailsRepository.getMovieDetailsFromLocal((movieID)).getNetworkState().observeForever(
        new Observer<Integer>() {
          @Override public void onChanged(Integer state) {
            if (state == NetworkState.LOADED_FROM_LOCAL) {
              MovieDetails movieDetails1 =
                  movieDetailsRepository.getMovieDetailsFromLocal((movieID)).getData();
              assertEquals(movieDetails1.getId(), movieID);
              assertEquals(movieDetails1.getTitle(), movieName);

              movieDetailsRepository.getMovieDetailsFromLocal((movieID)).getNetworkState()
                  .removeObserver(this);
            }
          }
        });
  }

  @After
  public void destroyRepository() {
    movieDetailsRepository.destroyInstance(movieID);
  }
}