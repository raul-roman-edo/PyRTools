package com.apps.pyr.pyrtools.core.db;

public class SearchByRadiusCommand {
  private static final float EARTH_RAD_KM = 6371;
  private static final float TORAD = 3.14159265359f / 180f;
  // Taylor sine (order 2)
  private static final String TAYLOR_SIN =
      "(%s) - (%s) * (%s) * (%s) / 6 + (%s) * (%s) * (%s) * (%s) * (%s) / 120";
  // Taylor cosine (order 2)
  private static final String TAYLOR_COS = "1 - (%s) * (%s) / 2 + (%s) * (%s) * (%s) * (%s) / 24";
  private static final String QUERY_TEMPLATE =
      "SELECT *, Distance FROM (SELECT *, %s AS Distance FROM %s) t WHERE Distance<%s "
          + "ORDER BY Distance;";
  // Haversine formula
  private static final String HAVERSINE_TEMPLATE = "(%s) * (%s) + (%s) * (%s) * (%s) * (%s)";
  private static final String COORDINATE_DIFF_TEMPLATE = "%s * (%s - %s)/2";
  private static final String MULTIPLICATION = "%s * %s";
  private final String tableName;
  private final String latitudeCol;
  private final String longitudeCol;

  public SearchByRadiusCommand(String tableName, String latitudeCol, String longitudeCol) {
    this.tableName = tableName;
    this.latitudeCol = latitudeCol;
    this.longitudeCol = longitudeCol;
  }

  public String buildQuery(float latitude, float longitude, float radiusInKm) {
    return buildHaversineDistanceQuery(latitude, longitude, radiusInKm);
  }

  private String buildHaversineDistanceQuery(float latitude, float longitude, float radiusInKm) {
    float r = (float) Math.pow(Math.sin(radiusInKm / (2 * EARTH_RAD_KM)), 2);

    String dlat = String.format(COORDINATE_DIFF_TEMPLATE, TORAD, latitudeCol, latitude);
    String dlon = String.format(COORDINATE_DIFF_TEMPLATE, TORAD, longitudeCol, longitude);
    float coslat1 = (float) Math.cos(latitude * TORAD);
    String lat2 = String.format(MULTIPLICATION, TORAD, latitudeCol);

    String a = String.format(HAVERSINE_TEMPLATE,
        String.format(TAYLOR_SIN, dlat, dlat, dlat, dlat, dlat, dlat, dlat, dlat, dlat),
        String.format(TAYLOR_SIN, dlat, dlat, dlat, dlat, dlat, dlat, dlat, dlat, dlat), coslat1,
        String.format(TAYLOR_COS, lat2, lat2, lat2, lat2, lat2, lat2),
        String.format(TAYLOR_SIN, dlon, dlon, dlon, dlon, dlon, dlon, dlon, dlon, dlon),
        String.format(TAYLOR_SIN, dlon, dlon, dlon, dlon, dlon, dlon, dlon, dlon, dlon));

    return String.format(QUERY_TEMPLATE, a, tableName, r);
  }
}
