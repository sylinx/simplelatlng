package com.javadocmd.simplelatlng.chinatool;

import com.javadocmd.simplelatlng.LatLng;

/* 
 * author: wangbai@yongche.com
 */
public final class WorldToMars {
    private ChinaOffset converter = new ChinaOffset();
    //
    // Krasovsky 1940
    //
    // a = 6378245.0, 1/f = 298.3
    // b = a * (1 - f)
    // ee = (a^2 - b^2) / a^2;
    final static double a = 6378245.0;
    final static double ee = 0.00669342162296594323;

    public LatLng transcodeFromWgs84toGcj02(LatLng wgPoint) {
        Point point = converter.wg2mars(wgPoint.getLatitude(),
                wgPoint.getLongitude());
        if (point == null) {
            throw new IllegalStateException("Fail to transcode " + wgPoint);
        }
        return new LatLng(point.getY(), point.getX());
    }

    /*
     * The accuracy is about between 0 to 3 meter
     */
    public LatLng transcodeFromGcj02toWgs84(LatLng gcPoint) {
        LatLng tmpPoint = transcodeFromWgs84toGcj02(gcPoint);
        double tmpLat = tmpPoint.getLatitude();
        double tmpLon = tmpPoint.getLongitude();

        double gcLat = gcPoint.getLatitude();
        double gcLon = gcPoint.getLongitude();

        double deltaLat = tmpLat - gcLat;
        double deltaLon = tmpLon - gcLon;

        return new LatLng(gcLat - deltaLat, gcLon - deltaLon);
    }
}
