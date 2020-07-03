package com.mina_mikhail.fixed_solutions_task;

import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.mina_mikhail.fixed_solutions_task.data.model.api.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.AppDatabase;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao.MovieDetailsDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MovieDetailsDaoTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

  private Context instrumentationContext;

  private AppDatabase appDatabase;
  private MovieDetailsDao movieDetailsDao;

  @Before
  public void initDatabase() {
    instrumentationContext = InstrumentationRegistry.getInstrumentation().getContext();

    appDatabase = Room.inMemoryDatabaseBuilder(instrumentationContext, AppDatabase.class).build();
    movieDetailsDao = appDatabase.getMovieDetailsDao();
  }

  @Test
  public void insertMovieDetails() {
    int movieID = 101;
    String movieName = "My Favourite Movie";

    MovieDetails movieDetails = new MovieDetails();
    movieDetails.setId(movieID);
    movieDetails.setTitle(movieName);

    movieDetailsDao.insert(movieDetails);

    LiveData<MovieDetails> retrieveSubjects = movieDetailsDao.getMovie(movieID);
    retrieveSubjects.observeForever(subjectEntities -> {
      assertEquals(subjectEntities.getTitle(), movieName);
    });
  }

  @After
  public void closeDatabase() {
    appDatabase.close();
  }
}