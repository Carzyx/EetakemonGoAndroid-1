package edu.upc.eetac.dsa.eetakemongoandroid.GameClient;

/**
 * Created by Ignacio on 17/06/2017.
 */

public enum StateFlowGame {
    SelectUser(1),
    SelectEetackemon(2),
    AcceptInvitation(3);
    private final int value;


    private StateFlowGame(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
