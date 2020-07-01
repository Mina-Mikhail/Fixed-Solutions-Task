package com.mina_mikhail.fixed_solutions_task;

import android.content.Context;
import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import com.mina_mikhail.fixed_solutions_task.data.model.Movie;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.AppDatabase;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao.PopularMoviesDao;
import com.mina_mikhail.fixed_solutions_task.utils.LiveDataTestUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoviesDaoTest {

  private Context instrumentationContext;

  private AppDatabase appDatabase;
  private PopularMoviesDao moviesDao;

  @Before
  public void initDatabase() {
    instrumentationContext = InstrumentationRegistry.getInstrumentation().getContext();

    appDatabase = Room.inMemoryDatabaseBuilder(instrumentationContext, AppDatabase.class).build();
    moviesDao = appDatabase.getPopularMoviesDao();
  }

  @Test
  public void movieCanBeRetrievedAfterInsert() {
    // Clear table
    moviesDao.clearTable();

    // Insert Movie
    Movie movie = new Movie();
    movie.setId(70707);

    // Retrieve movies
    List<Movie> movies = new ArrayList<>();
    try {
      movies = LiveDataTestUtil.getValue(moviesDao.getMovies());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // check test result
    assertEquals(1, movies.size());
    assertEquals(70707, movies.get(0).getId());
  }

  @After
  public void closeDatabase() {
    appDatabase.close();
  }
}