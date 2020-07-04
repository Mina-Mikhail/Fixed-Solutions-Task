package com.mina_mikhail.fixed_solutions_task;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.mina_mikhail.fixed_solutions_task.data.model.api.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao.MovieDetailsDao;
import com.mina_mikhail.fixed_solutions_task.di.component.DaggerTestComponent;
import com.mina_mikhail.fixed_solutions_task.di.component.TestComponent;
import com.mina_mikhail.fixed_solutions_task.di.module.TestModule;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MovieDetailsDaoTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  @Inject
  public MovieDetailsDao movieDetailsDao;

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
    int movieID = 101;
    String movieName = "My Favourite Movie";

    MovieDetails movieDetails = new MovieDetails();
    movieDetails.setId(movieID);
    movieDetails.setTitle(movieName);

    movieDetailsDao.insert(movieDetails);

    // Assert
    movieDetailsDao.getMovie(movieID)
        .observeForever(new Observer<MovieDetails>() {
          @Override
          public void onChanged(MovieDetails movieDetails) {
            assertEquals(movieDetails.getId(), movieID);
            assertEquals(movieDetails.getTitle(), movieName);
            movieDetailsDao.getMovie(movieID).removeObserver(this);
          }
        });
  }
}