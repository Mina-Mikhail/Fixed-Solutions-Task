package com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;

@Dao
public interface PopularMoviesDao
    extends BaseDao<Movie> {

  @Query("SELECT * FROM movies")
  DataSource.Factory<Integer, Movie> getMovies();

  @Override
  @Query("DELETE FROM movies")
  void clearTable();
}