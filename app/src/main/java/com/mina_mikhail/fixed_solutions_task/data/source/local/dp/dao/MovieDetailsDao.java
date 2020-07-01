package com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import com.mina_mikhail.fixed_solutions_task.data.model.MovieDetails;

@Dao
public interface MovieDetailsDao {

  @Query("SELECT * FROM movies_details where id = :movieID")
  LiveData<MovieDetails> getMovie(int movieID);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertMovie(MovieDetails movie);

  @Query("DELETE FROM movies")
  void clearMovies();
}