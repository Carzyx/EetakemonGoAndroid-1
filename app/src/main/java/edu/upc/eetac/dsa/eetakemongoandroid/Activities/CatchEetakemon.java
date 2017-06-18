package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.Random;

import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Atack;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.ValuesForActivites;
import edu.upc.eetac.dsa.eetakemongoandroid.R;

public class CatchEetakemon extends AppCompatActivity {

    private ProgressBar rivalEetakemonHealthProgessBar;
    private ProgressBar myEetakemonHealthProgessBar;
    private Eetakemon rivalEetakemon;
    private Eetakemon myEetakemon;
    private TextView atakText1;
    private TextView atakText2;
    private TextView atakText3;
    private TextView atakText4;
    private TextView myPs;
    private TextView rivalPs;
    private TextView atackText;
    private TextView exitText;
    private int myEetakemonHealth;
    private int rivalEetakemonHealth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);

        prepareAcitivty();
    }

    public void atacak(View view) {
        atakText1.setVisibility(View.VISIBLE);
        atakText2.setVisibility(View.VISIBLE);
        atakText3.setVisibility(View.VISIBLE);
        atakText4.setVisibility(View.VISIBLE);
        atackText.setVisibility(View.INVISIBLE);
        exitText.setVisibility(View.INVISIBLE);
    }

    public void atak1(View view) {
        doAtack(myEetakemon.getAtacks().get(0));
    }

    public void atak2(View view) {
        doAtack(myEetakemon.getAtacks().get(1));
    }

    public void atak3(View view) {
        doAtack(myEetakemon.getAtacks().get(2));
    }

    public void atak4(View view) {
        doAtack(myEetakemon.getAtacks().get(3));
    }

    public void salir(View view) {
        setResult(RESULT_CANCELED, getIntent());
        finish();
    }

    public void onBackPressed() {
        atakText1.setVisibility(View.INVISIBLE);
        atakText2.setVisibility(View.INVISIBLE);
        atakText3.setVisibility(View.INVISIBLE);
        atakText4.setVisibility(View.INVISIBLE);
        atackText.setVisibility(View.VISIBLE);
        exitText.setVisibility(View.VISIBLE);
    }

    private void prepareAcitivty() {
        rivalEetakemon = (Eetakemon) getIntent().getSerializableExtra("Eetakemon");
        myEetakemon = (Eetakemon) getIntent().getSerializableExtra("miEetakemon");
        ImageView suFoto = (ImageView) findViewById(R.id.suFoto);
        ImageView miFoto = (ImageView) findViewById(R.id.miFoto);
        rivalEetakemonHealthProgessBar = (ProgressBar) findViewById(R.id.suProgresVida);
        myEetakemonHealthProgessBar = (ProgressBar) findViewById(R.id.miProgresVida);
        myEetakemonHealth = myEetakemon.getPs();
        rivalEetakemonHealth = rivalEetakemon.getPs();
        rivalEetakemonHealthProgessBar.setProgress(100);
        myEetakemonHealthProgessBar.setProgress(100);
        Picasso.with(this).load(JSONservice.URL + rivalEetakemon.getImage()).resize(120,120).into(suFoto);
        Picasso.with(this).load(JSONservice.URL + myEetakemon.getImage()).resize(120,120).into(miFoto);
        rivalPs = (TextView) findViewById(R.id.suPs);
        rivalPs.setText(String.valueOf(rivalEetakemon.getPs() + "/" + rivalEetakemon.getPs()));
        myPs = (TextView) findViewById(R.id.miPs);
        myPs.setText(String.valueOf(myEetakemon.getPs() + "/" + myEetakemon.getPs()));
        atakText1 = (TextView) findViewById(R.id.atack1);
        atakText1.setText(myEetakemon.getAtacks().get(0).getName());
        atakText2 = (TextView) findViewById(R.id.atack2);
        atakText2.setText(myEetakemon.getAtacks().get(1).getName());
        atakText3 = (TextView) findViewById(R.id.atack3);
        atakText3.setText(myEetakemon.getAtacks().get(2).getName());
        atakText4 = (TextView) findViewById(R.id.atack4);
        atakText4.setText(myEetakemon.getAtacks().get(3).getName());
        atackText = (TextView) findViewById(R.id.Atacar);
        exitText = (TextView) findViewById(R.id.Salir);
        atakText1.setVisibility(View.INVISIBLE);
        atakText2.setVisibility(View.INVISIBLE);
        atakText3.setVisibility(View.INVISIBLE);
        atakText4.setVisibility(View.INVISIBLE);
    }

    private void doAtack(Atack atack) {
        rivalEetakemonHealth = rivalEetakemonHealth - atack.getDamageBase();
        rivalPs.setText(rivalEetakemonHealth + "/" + rivalEetakemon.getPs());
        rivalEetakemonHealthProgessBar.setProgress(rivalEetakemonHealth *100/ rivalEetakemon.getPs());
        if (rivalEetakemonHealth > 0) {
            Random r = new Random();
            recieveAtack(rivalEetakemon.getAtacks().get(r.nextInt(3)));
        }
        status();

    }

    private void recieveAtack(Atack atack) {
        Toast.makeText(CatchEetakemon.this,"El rival ha usado: "+atack.getName(),Toast.LENGTH_LONG).show();
        myEetakemonHealth = myEetakemonHealth - atack.getDamageBase();
        myPs.setText(myEetakemonHealth + "/" + myEetakemon.getPs());
        myEetakemonHealthProgessBar.setProgress(myEetakemonHealth  * 100/ myEetakemon.getPs());
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        status();
    }

    private void status() {

        if (rivalEetakemonHealth <= 0) {
            getIntent().putExtra("Eetakemon", (Serializable) rivalEetakemon);
            Toast.makeText(this, "Lo has capturado", Toast.LENGTH_SHORT).show();
            getIntent().putExtra("Eetakemon",(Serializable)rivalEetakemon);
            setResult(ValuesForActivites.CaptureOk.getValue(), getIntent());
            finish();
        } else if (myEetakemonHealth <= 0) {
            Toast.makeText(this, "Se te ha escapado", Toast.LENGTH_SHORT).show();
            setResult(ValuesForActivites.CaptureKO.getValue(), getIntent());
            finish();
        }
    }
    public void exit(View view){
        setResult(ValuesForActivites.CaptureKO.getValue());
        finish();
    }

}
