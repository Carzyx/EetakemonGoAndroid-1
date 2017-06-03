package edu.upc.eetac.dsa.eetakemongoandroid.Model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Ignacio on 03/06/2017.
 */

public class Markers {
    LatLng latLng;
    Eetakemon eetakemon;
    public void Markers(){}

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Eetakemon getEetakemon() {
        return eetakemon;
    }

    public void setEetakemon(Eetakemon eetakemon) {
        this.eetakemon = eetakemon;
    }
}
