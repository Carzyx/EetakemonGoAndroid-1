package edu.upc.eetac.dsa.eetakemongoandroid.GameClient;

import java.io.IOException;

/**
 * Created by Miguel Angel on 17/06/2017.
 */

public interface IClientRequest {
    void StartGame() throws IOException, ClassNotFoundException;
    void createConnectionRequest() throws IOException, ClassNotFoundException;
}