package com.mina_mikhail.fixed_solutions_task;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.AppDatabase;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao.PopularMoviesDao;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class PopularMoviesDaoTest {

  @Rule
  public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

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
  public void insertMovie() {
    int movieID = 707;
    String movieName = "Spider Man";

    Movie movie = new Movie();
    movie.setId(movieID);
    movie.setTitle(movieName);

    moviesDao.insert(movie);

    final LiveData<PagedList<Movie>>
        movies = new LivePagedListBuilder<>(moviesDao.getMovies(), 20).build();

    // Assert
    movies.observeForever(new Observer<PagedList<Movie>>() {
      @Override
      public void onChanged(@Nullable PagedList<Movie> localMovie) {
        assertEquals(localMovie.get(0).getId(), movieID);
        assertEquals(localMovie.get(0).getTitle(), movieName);
        movies.removeObserver(this);
      }
    });
  }

  @Test
  public void clearMoviesTable() {
    List<Movie> moviesList = new ArrayList<>();
    for (int i = 1; i <= 10; i++) {
      Movie movie = new Movie();
      movie.setId(i);

      moviesList.add(movie);
    }

    moviesDao.insert(moviesList);
    moviesDao.clearTable();

    final LiveData<PagedList<Movie>>
        movies = new LivePagedListBuilder<>(moviesDao.getMovies(), 20).build();

    // Assert
    movies.observeForever(new Observer<PagedList<Movie>>() {
      @Override
      public void onChanged(@Nullable PagedList<Movie> localMovies) {
        assertEquals(localMovies.size(), 0);
        movies.removeObserver(this);
      }
    });
  }

  @After
  public void closeDatabase() {
    appDatabase.close();
  }
}