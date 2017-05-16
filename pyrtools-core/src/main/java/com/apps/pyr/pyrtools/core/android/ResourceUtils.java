package com.apps.pyr.pyrtools.core.android;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class ResourceUtils {
  public static final String TAG = ResourceUtils.class.getName();
  private static final int ONEK = 1024;
  private static final String EMPTY = "";
  private static final String EXTRACT_STRING_FROM_RAW_RESOURCE = "extractStringFromRawResource";
  private static final int NO_DATA = -1;
  private static final String UTF_8 = "UTF-8";

  public static String extractStringFromRawResource(Context context, int resourceId) {
    InputStream is;
    try {
      is = context.getResources().openRawResource(resourceId);
    } catch (Resources.NotFoundException nfe) {
      Log.d(TAG, EXTRACT_STRING_FROM_RAW_RESOURCE, nfe);
      return EMPTY;
    }
    Writer rawData = new StringWriter();
    char[] buffer = new char[ONEK];
    try {
      Reader reader = new BufferedReader(new InputStreamReader(is, UTF_8));
      int n;
      while ((n = reader.read(buffer)) != NO_DATA) {
        rawData.write(buffer, 0, n);
      }
    } catch (UnsupportedEncodingException ue) {
      Log.d(TAG, EXTRACT_STRING_FROM_RAW_RESOURCE, ue);
    } catch (IOException ioe) {
      Log.d(TAG, EXTRACT_STRING_FROM_RAW_RESOURCE, ioe);
    } finally {
      try {
        is.close();
      } catch (IOException anotherioe) {
        Log.d(TAG, EXTRACT_STRING_FROM_RAW_RESOURCE, anotherioe);
      }
    }
    return rawData.toString();
  }

  public static int obtainColorFromAttribute(Context context, int attrId) {
    TypedValue typedValue = new TypedValue();
    Resources.Theme theme = context.getTheme();
    theme.resolveAttribute(attrId, typedValue, true);
    int color = typedValue.data;

    return color;
  }
}
