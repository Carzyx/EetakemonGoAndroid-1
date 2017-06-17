package edu.upc.eetac.dsa.eetakemongoandroid.GameClient;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Map;

import edu.upc.eetac.dsa.eetakemongoandroid.Activities.SelecUser;
import edu.upc.eetac.dsa.eetakemongoandroid.Activities.SelectEetackemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.R;

/**
 * Created by Miguel Angel on 13/06/2017.
 */
public class ClientRequest extends AppCompatActivity implements IClientRequest  {

    String username;
    private boolean isAcceptet;
    Eetakemon eetakemon =new Eetakemon();
    String rival;
    Map<String, Eetakemon> eetakemons;
    boolean isGameRunning = true;
    private Gson jsonSerializer = new Gson();

    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;

    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);
        int result=getIntent().getIntExtra("Value",0);
        message=getIntent().getStringExtra("Message");
        if(result==StateFlowGame.SelectUser.getValue()){
            Intent intent=new Intent(ClientRequest.this, SelecUser.class);
            startActivityForResult(intent,StateFlowGame.SelectUser.getValue());
        }
        else if(result==StateFlowGame.AcceptInvitation.getValue()){
            AlertInvitation(message);
            if(isAcceptet){
            Intent intent=new Intent(ClientRequest.this, SelectEetackemon.class);
            startActivityForResult(intent,StateFlowGame.SelectEetackemon.getValue());}
        }
    }
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(requestCode==StateFlowGame.SelectUser.getValue()){

        }
        else if(requestCode==StateFlowGame.SelectEetackemon.getValue()){
            eetakemon=(Eetakemon)intent.getSerializableExtra("Eetakemon");
        }
        else if(requestCode==StateFlowGame.AcceptInvitation.getValue()){

        }
    }
    public ClientRequest(String username)
    {
        this.username = username;
    }

    public void startGame() throws IOException, ClassNotFoundException {

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
    public void reciveRequestInvitation() throws IOException, ClassNotFoundException {
        System.out.println("Reciving request invitation game...");
        String message = (String) in.readObject();
        System.out.println("Request invitation recived");
        out.writeObject(jsonSerializer.toJson(true));
    }
    public void createConnectionRequest() throws IOException, ClassNotFoundException {
        createConnection();
        sendRequestRegistation(username);
        reciveRequestInvitation();
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
    private void AlertInvitation(String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isAcceptet=true;
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isAcceptet=false;
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}

