package com.mina_mikhail.fixed_solutions_task;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.mina_mikhail.fixed_solutions_task.data.enums.NetworkState;
import com.mina_mikhail.fixed_solutions_task.data.model.api.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.repo.MovieDetailsRepository;
import com.mina_mikhail.fixed_solutions_task.di.component.DaggerTestComponent;
import com.mina_mikhail.fixed_solutions_task.di.component.TestComponent;
import com.mina_mikhail.fixed_solutions_task.di.module.TestModule;
import javax.inject.Inject;
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

  private int movieID = 101;
  private String movieName = "Marvel Avengers";

  @Inject
  public MovieDetailsRepository movieDetailsRepository;

  @Before
  public void setUp() {
    TestComponent testComponent = DaggerTestComponent.builder()
        .context(InstrumentationRegistry.getInstrumentation().getTargetContext())
        .testModule(new TestModule())
        .build();

    testComponent.inject(this);
  }

  @Test
  public void insertMovieDetails() {
    MovieDetails movieDetails = new MovieDetails();
    movieDetails.setId(movieID);
    movieDetails.setTitle(movieName);

    movieDetailsRepository.insertMovieDetailsToLocal(movieDetails);

    // Assert
    movieDetailsRepository.getMovieDetails((movieID)).getNetworkState().observeForever(
        new Observer<Integer>() {
          @Override public void onChanged(Integer state) {
            if (state == NetworkState.LOADED_FROM_LOCAL
                || state == NetworkState.LOADED_FROM_REMOTE) {
              MovieDetails localMovieDetails =
                  movieDetailsRepository.getMovieDetailsFromLocal((movieID)).getData();
              assertEquals(localMovieDetails.getId(), movieID);
              assertEquals(localMovieDetails.getTitle(), movieName);

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