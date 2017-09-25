package com.droidteahouse.contacts.tools;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sgv on 04.11.15.
 */
public class DateFormatter {
  private static String DATE_PATTERN = "dd/MM/yyyy";
  private static String TAG ="DateFormatter";

  public static String convertDateToString(Date date) {
    return new SimpleDateFormat(DATE_PATTERN).format(date);
  }

  public static Date convertStringToDate(String date) {
    try {
      return new SimpleDateFormat(DATE_PATTERN).parse(date);
    } catch (ParseException e) {
      Log.d(TAG, "date parse error" + date);
      return new Date(date);
    }
  }
}
