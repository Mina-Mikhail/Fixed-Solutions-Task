package com.mina_mikhail.fixed_solutions_task.data.model.api;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.mina_mikhail.fixed_solutions_task.utils.CommonUtils;
import com.mina_mikhail.fixed_solutions_task.utils.Constants;
import com.mina_mikhail.fixed_solutions_task.utils.Converters;
import com.mina_mikhail.fixed_solutions_task.utils.DateTimeUtils;
import java.util.List;

@Entity(tableName = "movies_details")
public class MovieDetails {

  /**
   * adult : false
   * backdrop_path : /eFw5YSorHidsajLTayo1noueIxI.jpg
   * budget : 150000000
   * genres : [{"id":28,"name":"Action"},{"id":53,"name":"Thriller"},{"id":35,"name":"Comedy"}]
   * id : 509967
   * original_language : en
   * overview : After faking his death, a tech billionaire recruits a team of international operatives for a bold and bloody mission to take down a brutal dictator.
   * poster_path : /lnWkyG3LLgbbrIEeyl5mK5VRFe4.jpg
   * production_companies : [{"id":82819,"logo_path":"/5Z8WWr0Lf1tInVWwJsxPP0uMz9a.png","name":"Skydance Media","origin_country":"US"},{"id":6734,"logo_path":null,"name":"Bay Films","origin_country":""}]
   * production_countries : [{"iso_3166_1":"US","name":"United States of America"}]
   * release_date : 2019-12-13
   * title : 6 Underground
   * vote_average : 6.2
   * vote_count : 1124
   */

  @PrimaryKey
  @ColumnInfo(name = "movie_id")
  private int id;

  @ColumnInfo(name = "movie_for_adult")
  private boolean adult;

  @ColumnInfo(name = "movie_backdrop_path")
  private String backdrop_path;

  @ColumnInfo(name = "movie_status")
  private String status;

  @ColumnInfo(name = "movie_budget")
  private double budget;

  @ColumnInfo(name = "movie_original_language")
  private String original_language;

  @ColumnInfo(name = "movie_overview")
  private String overview;

  @ColumnInfo(name = "movie_poster_path")
  private String poster_path;

  @ColumnInfo(name = "movie_release_date")
  private String release_date;

  @ColumnInfo(name = "movie_title")
  private String title;

  @ColumnInfo(name = "movie_vote_average")
  private float vote_average;

  @ColumnInfo(name = "movie_vote_count")
  private int vote_count;

  @TypeConverters(Converters.class)
  @ColumnInfo(name = "movie_genres")
  private List<Genre> genres;

  @TypeConverters(Converters.class)
  @ColumnInfo(name = "movie_production_companies")
  private List<ProductionCompany> production_companies;

  @TypeConverters(Converters.class)
  @ColumnInfo(name = "movie_production_countries")
  private List<ProductionCountry> production_countries;

  @TypeConverters(Converters.class)
  @ColumnInfo(name = "movie_spoken_languages")
  private List<SpokenLanguage> spoken_languages;

  public boolean isAdult() {
    return adult;
  }

  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  public String getBackdrop_path() {
    if (backdrop_path != null) {
      if (!backdrop_path.contains(Constants.IMAGES_BASE_URL)) {
        return Constants.IMAGES_BASE_URL + backdrop_path;
      } else {
        return backdrop_path;
      }
    } else {
      return "";
    }
  }

  public void setBackdrop_path(String backdrop_path) {
    this.backdrop_path = backdrop_path;
  }

  public double getBudget() {
    return budget;
  }

  public String getBudgetFormatted() {
    if (budget != 0) {
      return CommonUtils.convertToSuffix((long) budget);
    } else {
      return "";
    }
  }

  public void setBudget(double budget) {
    this.budget = budget;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
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

  public String getRelease_date() {
    return release_date;
  }

  public String getReleaseDateCasted() {
    return DateTimeUtils
        .changeFormat(release_date, DateTimeUtils.API_DATE_FORMAT, DateTimeUtils.UI_DATE_FORMAT);
  }

  public void setRelease_date(String release_date) {
    this.release_date = release_date;
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

  public int getVote_count() {
    return vote_count;
  }

  public void setVote_count(int vote_count) {
    this.vote_count = vote_count;
  }

  public List<Genre> getGenres() {
    return genres;
  }

  public String getGenresInfo() {
    if (genres != null && genres.size() > 0) {
      StringBuilder items = new StringBuilder();
      for (Genre item : genres) {
        if (!items.toString().isEmpty()) items.append("/ ");
        items.append(item.getName());
      }
      return items.toString();
    } else {
      return "";
    }
  }

  public void setGenres(List<Genre> genres) {
    this.genres = genres;
  }

  public List<ProductionCompany> getProduction_companies() {
    return production_companies;
  }

  public void setProduction_companies(
      List<ProductionCompany> production_companies) {
    this.production_companies = production_companies;
  }

  public List<ProductionCountry> getProduction_countries() {
    return production_countries;
  }

  public String getProductionCountriesStr() {
    if (genres != null && genres.size() > 0) {
      StringBuilder items = new StringBuilder();
      for (ProductionCountry item : production_countries) {
        if (!items.toString().isEmpty()) items.append(", ");
        items.append(item.getName());
      }
      return items.toString();
    } else {
      return "";
    }
  }

  public void setProduction_countries(
      List<ProductionCountry> production_countries) {
    this.production_countries = production_countries;
  }

  public List<SpokenLanguage> getSpoken_languages() {
    return spoken_languages;
  }

  public String getSpokenLanguagesStr() {
    if (genres != null && genres.size() > 0) {
      StringBuilder items = new StringBuilder();
      for (SpokenLanguage item : spoken_languages) {
        if (!items.toString().isEmpty()) items.append(", ");
        items.append(item.getName());
      }
      return items.toString();
    } else {
      return "";
    }
  }

  public void setSpoken_languages(
      List<SpokenLanguage> spoken_languages) {
    this.spoken_languages = spoken_languages;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
