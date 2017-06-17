package edu.upc.eetac.dsa.eetakemongoandroid.GameClient;

import java.io.IOException;

/**
 * Created by Miguel Angel on 17/06/2017.
 */

public interface IClientRequest {
    void startGame() throws IOException, ClassNotFoundException;
    String createConnectionRequest() throws IOException, ClassNotFoundException;
}
