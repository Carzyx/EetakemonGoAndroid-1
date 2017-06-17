package edu.upc.eetac.dsa.eetakemongoandroid.Model;

/**
 * Created by Ignacio on 17/06/2017.
 */

public enum ValuesForActivites {
    Capture_OK(1),
    Capture_KO(-1);

    private final int value;

    private ValuesForActivites(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
