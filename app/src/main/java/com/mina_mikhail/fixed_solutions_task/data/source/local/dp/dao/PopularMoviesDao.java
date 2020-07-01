package com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
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