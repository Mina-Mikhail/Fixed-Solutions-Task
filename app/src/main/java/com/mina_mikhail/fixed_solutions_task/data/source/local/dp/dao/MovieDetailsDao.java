package com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.mina_mikhail.fixed_solutions_task.data.model.api.MovieDetails;
import io.reactivex.Single;

@Dao
public interface MovieDetailsDao
    extends BaseDao<MovieDetails> {

  @Query("SELECT * FROM movies_details where movie_id = :movieID")
  Single<MovieDetails> getMovie(int movieID);

  @Override
  @Query("DELETE FROM movies_details")
  void clearTable();
}