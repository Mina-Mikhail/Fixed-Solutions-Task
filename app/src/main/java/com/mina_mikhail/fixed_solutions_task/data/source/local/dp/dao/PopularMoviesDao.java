package com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.mina_mikhail.fixed_solutions_task.data.model.Movie;
import java.util.List;

@Dao
public interface PopularMoviesDao {

  @Query("SELECT * FROM movies")
  LiveData<List<Movie>> getMovies();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertMovies(List<Movie> movies);

  @Query("DELETE FROM movies")
  void clearMovies();
}