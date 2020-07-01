package com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;
import java.util.List;

@Dao
public interface PopularMoviesDao
    extends BaseDao<Movie> {

  @Query("SELECT * FROM movies")
  LiveData<List<Movie>> getMovies();

  @Override
  @Query("DELETE FROM movies")
  void clearTable();
}