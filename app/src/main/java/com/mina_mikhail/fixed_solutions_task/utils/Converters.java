package com.mina_mikhail.fixed_solutions_task.utils;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Genre;
import com.mina_mikhail.fixed_solutions_task.data.model.api.ProductionCompany;
import com.mina_mikhail.fixed_solutions_task.data.model.api.ProductionCountry;
import com.mina_mikhail.fixed_solutions_task.data.model.api.SpokenLanguage;
import java.lang.reflect.Type;
import java.util.List;

public class Converters {

  @TypeConverter
  public static List<Genre> fromStringToGenreList(String value) {
    Type listType = new TypeToken<List<Genre>>() {
    }.getType();
    return new Gson().fromJson(value, listType);
  }

  @TypeConverter
  public static String fromListToGenreString(List<Genre> list) {
    Gson gson = new Gson();
    return gson.toJson(list);
  }

  @TypeConverter
  public static List<ProductionCompany> fromStringToProductionCompanyString(String value) {
    Type listType = new TypeToken<List<ProductionCompany>>() {
    }.getType();
    return new Gson().fromJson(value, listType);
  }

  @TypeConverter
  public static String fromListToProductionCompanyString(List<ProductionCompany> list) {
    Gson gson = new Gson();
    return gson.toJson(list);
  }

  @TypeConverter
  public static List<ProductionCountry> fromStringToProductionCountryList(String value) {
    Type listType = new TypeToken<List<ProductionCountry>>() {
    }.getType();
    return new Gson().fromJson(value, listType);
  }

  @TypeConverter
  public static String fromListToProductionCountryString(List<ProductionCountry> list) {
    Gson gson = new Gson();
    return gson.toJson(list);
  }

  @TypeConverter
  public static List<SpokenLanguage> fromStringToSpokenLanguageList(String value) {
    Type listType = new TypeToken<List<SpokenLanguage>>() {
    }.getType();
    return new Gson().fromJson(value, listType);
  }

  @TypeConverter
  public static String fromListToSpokenLanguageString(List<SpokenLanguage> list) {
    Gson gson = new Gson();
    return gson.toJson(list);
  }
}
