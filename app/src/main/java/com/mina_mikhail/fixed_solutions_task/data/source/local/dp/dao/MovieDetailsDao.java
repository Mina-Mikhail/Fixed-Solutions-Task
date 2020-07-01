package com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.mina_mikhail.fixed_solutions_task.data.model.MovieDetails;

@Dao
public interface MovieDetailsDao {

  @Query("SELECT * FROM movies_details where movie_id = :movieID")
  LiveData<MovieDetails> getMovie(int movieID);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertMovie(MovieDetails movie);

  @Query("DELETE FROM movies")
  void clearMovies();
}