package edu.upc.eetac.dsa.eetakemongoandroid.Model;

/**
 * Created by Ignacio on 03/06/2017.
 */

public class Markers {
    private double lat;
    private double lng;
    private Eetakemon eetakemon;

    public void setMarkers(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Eetakemon getEetakemon() {
        return eetakemon;
    }

    public void setEetakemon(Eetakemon eetakemon) {
        this.eetakemon = eetakemon;
    }
}
