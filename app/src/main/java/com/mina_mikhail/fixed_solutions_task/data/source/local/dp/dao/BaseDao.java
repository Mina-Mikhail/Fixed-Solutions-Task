package com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

public interface BaseDao<T> {

  @Insert
  void insert(List<T> items);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insert(T item);

  @Update
  void update(T item);

  @Delete
  void delete(T item);

  @Query("")
  void clearTable();
}
