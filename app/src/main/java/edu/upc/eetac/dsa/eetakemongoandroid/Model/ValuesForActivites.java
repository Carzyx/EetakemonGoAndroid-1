package edu.upc.eetac.dsa.eetakemongoandroid.Model;

/**
 * Created by Ignacio on 17/06/2017.
 */

public enum ValuesForActivites {
    SelectEetackemon(1),
    StartCapture(2),
    CaptureOk(3),
    CaptureKO(4),
    StartFight(5),
    FinishFight(6);

    private final int value;

    private ValuesForActivites(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
