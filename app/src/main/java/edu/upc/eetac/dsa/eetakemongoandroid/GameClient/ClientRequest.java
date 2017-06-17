package edu.upc.eetac.dsa.eetakemongoandroid.GameClient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Map;

import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.EetakemonType;

/**
 * Created by Miguel Angel on 13/06/2017.
 */
public class ClientRequest implements IClientRequest {

    String username;
    String rival;
    Map<String, Eetakemon> eetakemons;
    boolean isGameRunning = true;
    private Gson jsonSerializer = new Gson();

    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;

    String message;

    public ClientRequest(String username)
    {
        this.username = username;
    }

    public void StartGame() throws IOException, ClassNotFoundException {

        createConnection();
        boolean registrationSucces = sendRequestRegistation(username);

        closeConnection();

        createConnection();

        boolean isRequestSuccesful = sendRequestGame(username, rival);

        if(isRequestSuccesful)
        {
            sendEetakemon();
            reciveEetakemons();


            boolean myTurn = true;
            while(isGameRunning)
            {
                if(myTurn)
                {
                    doTurn();
                }
                if(!myTurn)
                {
                    reciveEetakemons();
                }

                reciveGameStatus();
                myTurn = !myTurn;
            }

            boolean isWinner = reciveGameResult();
        }

        in.close();
        out.close();
        requestSocket.close();
    }

    public void createConnectionRequest() throws IOException, ClassNotFoundException {
        createConnection();
        sendRequestRegistation(username);
    }

    private void createConnection() throws IOException {
        //1. creating a socket to connect to the server
        requestSocket = new Socket("0.0.0.0", 2004);
        System.out.println("Creating connection...");
        System.out.println("Home connected to localhost in port 2004");
        //2. get Input and Output streams
        out = new ObjectOutputStream(requestSocket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(requestSocket.getInputStream());
    }

    private void closeConnection() throws IOException {
        requestSocket.close();
        in.close();
        out.close();
        System.out.println("Connection closed");
    }

    private boolean sendRequestRegistation(String username) throws IOException, ClassNotFoundException {

        System.out.println("Sending request registation...");
        Message message = new Message("RegisterUser", username, rival);
        out.writeObject(jsonSerializer.toJson(message));
        boolean succes = jsonSerializer.fromJson((String) in.readObject(), boolean.class);
        System.out.println("Registation succsefull");
        return succes;
    }

    private boolean sendRequestGame(String username, String rival) throws IOException, ClassNotFoundException {

        System.out.println("Sending request game...");
        Message message = new Message("Invitation", username, rival);
        out.writeObject(jsonSerializer.toJson(message));

        System.out.println("Reciving request confirmation...");
        boolean succes = jsonSerializer.fromJson((String) in.readObject(), boolean.class);

        System.out.println("Request game: " + succes);

        return succes;
    }

    private void sendEetakemon() throws IOException {
        System.out.println("Sending eetakemon...");
        Eetakemon eetakemon = new Eetakemon("pickachu", 5, 20, EetakemonType.NEUTRO, "", "");
        out.writeObject(jsonSerializer.toJson(eetakemon));
        System.out.println("Eetakemon sended");

    }

    private void reciveEetakemons() throws IOException, ClassNotFoundException {

        System.out.println("Reciving eetakemons...");

        Type type = new TypeToken<Map<String, Eetakemon>>(){}.getType();

        eetakemons = jsonSerializer.fromJson((String) in.readObject(), type);

        System.out.println("Eetakemons recived");
    }

    private void doAtack() throws IOException {
        System.out.println("Doing Atack...");
        ActionAtack action = new ActionAtack("placaje", 20);
        out.writeObject(jsonSerializer.toJson(action));
        System.out.println("Atack done");

    }

    private void doTurn() throws IOException, ClassNotFoundException {
        doAtack();
        reciveEetakemons();
    }

    private void reciveGameStatus() throws IOException, ClassNotFoundException {
        isGameRunning = jsonSerializer.fromJson((String) in.readObject(), boolean.class);
        System.out.println("Game is finished? --- " + isGameRunning);
    }

    private boolean reciveGameResult() throws IOException, ClassNotFoundException {
        return jsonSerializer.fromJson((String) in.readObject(), boolean.class);
    }
}
