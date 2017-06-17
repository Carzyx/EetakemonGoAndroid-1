package edu.upc.eetac.dsa.eetakemongoandroid.GameClient;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Map;

import edu.upc.eetac.dsa.eetakemongoandroid.Activities.SelecUser;
import edu.upc.eetac.dsa.eetakemongoandroid.Activities.SelectEetackemon;
import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.R;

/**
 * Created by Miguel Angel on 13/06/2017.
 */
public class ClientRequest extends AppCompatActivity implements IClientRequest  {

    //Start Layout
    private ProgressBar suProgresVida;
    private ProgressBar miProgresVida;
    private Eetakemon suEetakemon;
    private Eetakemon miEetakemon;
    private TextView atak1;
    private TextView atak2;
    private TextView atak3;
    private TextView atak4;
    private TextView miPs;
    private TextView suPs;
    private TextView atacar;
    private TextView salir;
    private int mylive;
    private int herlive;
    //End Layot
    String username;

    Eetakemon eetakemon =new Eetakemon();
    String rival;
    Map<String, Eetakemon> eetakemons;
    boolean isGameRunning = true;
    private boolean isRequestAccepted = false;
    private boolean isGuestUser = false;
    private boolean isHomeUser = true;

    private Gson jsonSerializer = new Gson();

    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;

    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);

        int result = getIntent().getIntExtra("Value",0);
        message = getIntent().getStringExtra("Message");
        if(result == StateFlowGame.SelectUser.getValue()){
            Intent intent=new Intent(ClientRequest.this, SelecUser.class);
            startActivityForResult(intent,StateFlowGame.SelectUser.getValue());
        }
        else if(result == StateFlowGame.AcceptInvitation.getValue()){
            AlertInvitation(message);
            if(isRequestAccepted) {
                isGuestUser = true;
                isHomeUser = false;
                try {
                    startGame();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(requestCode == StateFlowGame.SelectUser.getValue()){

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

        boolean isRequestSuccesful = false;
        if(isHomeUser)
        {
            createConnection();
            boolean registrationSucces = sendRequestRegistation(username);
            isRequestSuccesful = sendRequestGame(username, rival);
        }

        if(isGuestUser)
        {
            responseInvitation();
        }

        if(isRequestSuccesful || isRequestAccepted)
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

    private void responseInvitation() throws IOException {
        System.out.println("Response request invitation game...");
        out.writeObject(jsonSerializer.toJson(isRequestAccepted));
    }

    public String reciveRequestInvitation() throws IOException, ClassNotFoundException {
        System.out.println("Reciving request invitation game...");
        String message = (String) in.readObject();
        System.out.println("Request invitation recived");
        return message;

    }
    public String createConnectionRequest() throws IOException, ClassNotFoundException {
        createConnection();
        sendRequestRegistation(username);
        return reciveRequestInvitation();
    }

    private void createConnection() throws IOException {
        //1. creating a socket to connect to the server
        requestSocket = new Socket("192.168.1.55", 2004);
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
        eetakemonSelectedByUser();
        out.writeObject(jsonSerializer.toJson(eetakemon));
        System.out.println("Eetakemon sended");
    }

    private void eetakemonSelectedByUser()
    {
        Intent intent=new Intent(ClientRequest.this, SelectEetackemon.class);
        startActivityForResult(intent,StateFlowGame.SelectEetackemon.getValue());
    }

    private void reciveEetakemons() throws IOException, ClassNotFoundException {

        System.out.println("Reciving eetakemons...");

        Type type = new TypeToken<Map<String, Eetakemon>>(){}.getType();

        eetakemons = jsonSerializer.fromJson((String) in.readObject(), type);

        System.out.println("Eetakemons recived");

        updateLayoutByEetakemon();
    }

    private void updateLayoutByEetakemon()
    {
        if(isGuestUser)
        {
            miEetakemon = eetakemons.get("userGuest");
            suEetakemon = eetakemons.get("userHome");
        }
        if(isHomeUser)
        {
            miEetakemon = eetakemons.get("userHome");
            suEetakemon = eetakemons.get("userGuest");
        }

        ImageView suFoto=(ImageView)findViewById(R.id.suFoto);
        ImageView miFoto=(ImageView)findViewById(R.id.miFoto);
        suProgresVida=(ProgressBar)findViewById(R.id.suProgresVida);
        miProgresVida=(ProgressBar)findViewById(R.id.miProgresVida);
        mylive=miEetakemon.getPs();
        herlive=suEetakemon.getPs();
        suProgresVida.setProgress(100);
        miProgresVida.setProgress(100);
        Picasso.with(this).load(JSONservice.URL+suEetakemon.getImage()).into(suFoto);
        Picasso.with(this).load(JSONservice.URL+miEetakemon.getImage()).into(miFoto);
        suPs=(TextView)findViewById(R.id.suPs);
        suPs.setText(String.valueOf(suEetakemon.getPs()+"/"+suEetakemon.getPs()));
        miPs=(TextView)findViewById(R.id.miPs);
        miPs.setText(String.valueOf(miEetakemon.getPs()+"/"+miEetakemon.getPs()));
        atak1=(TextView)findViewById(R.id.atack1);
        atak1.setText(miEetakemon.getEetakemonAtack().get(0).getName());
        atak2=(TextView)findViewById(R.id.atack2);
        atak2.setText(miEetakemon.getEetakemonAtack().get(1).getName());
        atak3=(TextView)findViewById(R.id.atack3);
        atak3.setText(miEetakemon.getEetakemonAtack().get(2).getName());
        atak4=(TextView)findViewById(R.id.atack4);
        atak4.setText(miEetakemon.getEetakemonAtack().get(3).getName());
        atacar=(TextView)findViewById(R.id.Atacar);
        salir=(TextView)findViewById(R.id.Salir);
        atak1.setVisibility(View.INVISIBLE);
        atak2.setVisibility(View.INVISIBLE);
        atak3.setVisibility(View.INVISIBLE);
        atak4.setVisibility(View.INVISIBLE);
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
                        isRequestAccepted=true;
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isRequestAccepted=false;
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}

