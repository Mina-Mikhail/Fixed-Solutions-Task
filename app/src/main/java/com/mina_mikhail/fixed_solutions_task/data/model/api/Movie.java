package com.mina_mikhail.fixed_solutions_task.data.model.api;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.mina_mikhail.fixed_solutions_task.utils.Constants;
import com.mina_mikhail.fixed_solutions_task.utils.DateTimeUtils;

@Entity(tableName = "movies")
public class Movie {

  /**
   * vote_count : 1713
   * poster_path : /xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg
   * id : 419704
   * adult : false
   * title : Ad Astra
   * vote_average : 5
   * release_date : 2019-09-17
   */

  @PrimaryKey(autoGenerate = true) // To avoid ordering issue when retrieve movies
  @ColumnInfo(name = "room_id")
  private int local_id;

  @ColumnInfo(name = "movie_id")
  private int id;

  @ColumnInfo(name = "movie_vote_count")
  private int vote_count;

  @ColumnInfo(name = "movie_poster_path")
  private String poster_path;

  @ColumnInfo(name = "movie_original_language")
  private String original_language;

  @ColumnInfo(name = "movie_for_adult")
  private boolean adult;

  @ColumnInfo(name = "movie_title")
  private String title;

  @ColumnInfo(name = "movie_vote_average")
  private float vote_average;

  @ColumnInfo(name = "movie_release_date")
  private String release_date;

  public int getLocal_id() {
    return local_id;
  }

  public void setLocal_id(int local_id) {
    this.local_id = local_id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getVote_count() {
    return vote_count;
  }

  public void setVote_count(int vote_count) {
    this.vote_count = vote_count;
  }

  public String getPoster_path() {
    if (poster_path != null) {
      if (!poster_path.contains(Constants.IMAGES_BASE_URL)) {
        return Constants.IMAGES_BASE_URL + poster_path;
      } else {
        return poster_path;
      }
    } else {
      return "";
    }
  }

  public void setPoster_path(String poster_path) {
    this.poster_path = poster_path;
  }

  public String getOriginal_language() {
    if (original_language != null && !original_language.isEmpty()) {
      return original_language.toUpperCase();
    } else {
      return "";
    }
  }

  public void setOriginal_language(String original_language) {
    this.original_language = original_language;
  }

  public boolean isAdult() {
    return adult;
  }

  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public float getVote_average() {
    return vote_average / 2;
  }

  public void setVote_average(float vote_average) {
    this.vote_average = vote_average;
  }

  public String getRelease_date() {
    return release_date;
  }

  public String getReleaseDateCasted() {
    return DateTimeUtils
        .changeFormat(release_date, Constants.API_DATE_FORMAT, Constants.UI_DATE_FORMAT);
  }

  public void setRelease_date(String release_date) {
    this.release_date = release_date;
  }
}