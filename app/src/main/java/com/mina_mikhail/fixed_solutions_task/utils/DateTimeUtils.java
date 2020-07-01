/*
 * *
 *  * Created by MIna Mikhail on 3/18/19 12:27 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 3/18/19 12:11 AM
 *
 */

package com.mina_mikhail.fixed_solutions_task.utils;

import com.mina_mikhail.fixed_solutions_task.data.enums.Languages;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateTimeUtils {

  public static String changeFormat(String input, String oldFormat, String newFormat) {
    if (input != null) {
      SimpleDateFormat formatView = new SimpleDateFormat(oldFormat, getLocale());
      SimpleDateFormat newFormatView = new SimpleDateFormat(newFormat, getLocale());
      Date dateObj = null;
      try {
        dateObj = formatView.parse(input);
      } catch (ParseException e) {
        e.printStackTrace();
      }

      return newFormatView.format(dateObj);
    } else {
      return "";
    }
  }

  private static Locale getLocale() {
    if (String.valueOf(Locale.getDefault().getLanguage()).equals(Languages.ENGLISH)) {
      return Locale.ENGLISH;
    } else if (String.valueOf(Locale.getDefault().getLanguage()).equals(Languages.ARABIC)) {
      return new Locale(Languages.ARABIC);
    } else if (String.valueOf(Locale.getDefault().getLanguage()).equals(Languages.FRENCH)) {
      return Locale.FRENCH;
    } else {
      return Locale.ENGLISH;
    }
  }
}
