package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Random;

import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Atack;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Party;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import edu.upc.eetac.dsa.eetakemongoandroid.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fight extends AppCompatActivity {
    private ProgressBar rivalEetakemonHealthProgessBar;
    private ProgressBar myEetakemonHealthProgessBar;
    private String token;
    private boolean iAmCandidate1;
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
    private Party party;
    private User user;
    private int myEetakemonHealth;
    private int rivalEetakemonHealth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);
        user =(User) getIntent().getSerializableExtra("User");
        token=getIntent().getStringExtra("Token");
        prepareAcitivty();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);
        final Call<Party> getParty = service.resgisterCandidate(user,token);
        getParty.enqueue(new Callback<Party>() {
            @Override
            public void onResponse(Call<Party> call, Response<Party> response) {
                if(response.body()==null){
                    waitVsible();
                    getParty();
                }
                else {
                    waitInvisible();
                    whoIam();
                    prepareAcitivty();
                    if(party.getTurnIndication().get(user.getName())==true)
                        onBackPressed();
                }
            }

            @Override
            public void onFailure(Call<Party> call, Throwable t) {
                Toast.makeText(Fight.this, "No se ha podido acceder al servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void atak1(View view) {
        doAtack(myEetakemon.getEetakemonAtack().get(0));
    }

    public void atak2(View view) {
        doAtack(myEetakemon.getEetakemonAtack().get(1));
    }

    public void atak3(View view) {
        doAtack(myEetakemon.getEetakemonAtack().get(2));
    }

    public void atak4(View view) {
        doAtack(myEetakemon.getEetakemonAtack().get(3));
    }

    private void doAtack(Atack atack) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);
        Call<Party> getParty = service.doAtack(atack,token);
        getParty.enqueue(new Callback<Party>() {
            @Override
            public void onResponse(Call<Party> call, Response<Party> response) {
                whoIam();
                waitVsible();
                Adapt();
            }

            @Override
            public void onFailure(Call<Party> call, Throwable t) {
                Toast.makeText(Fight.this, "No se ha podido acceder al servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void recieveAtack() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);
        Call<Party> getParty = service.reciveAtack(token);
        getParty.enqueue(new Callback<Party>() {
            @Override
            public void onResponse(Call<Party> call, Response<Party> response) {
                if(response.body()==null)
                    recieveAtack();
                whoIam();
                Adapt();
                waitInvisible();
            }

            @Override
            public void onFailure(Call<Party> call, Throwable t) {

            }
        });
    }

    private void prepareAcitivty() {
        ImageView suFoto = (ImageView) findViewById(R.id.suFoto);
        ImageView miFoto = (ImageView) findViewById(R.id.miFoto);
        rivalEetakemonHealthProgessBar = (ProgressBar) findViewById(R.id.suProgresVida);
        myEetakemonHealthProgessBar = (ProgressBar) findViewById(R.id.miProgresVida);
        myEetakemonHealth = myEetakemon.getPs();
        rivalEetakemonHealth = rivalEetakemon.getPs();
        rivalEetakemonHealthProgessBar.setProgress(100);
        myEetakemonHealthProgessBar.setProgress(100);
        Picasso.with(this).load(JSONservice.URL + rivalEetakemon.getImage()).into(suFoto);
        Picasso.with(this).load(JSONservice.URL + myEetakemon.getImage()).into(miFoto);
        rivalPs = (TextView) findViewById(R.id.suPs);
        myPs = (TextView) findViewById(R.id.miPs);
        atakText1 = (TextView) findViewById(R.id.atack1);
        atakText1.setText(myEetakemon.getEetakemonAtack().get(0).getName());
        atakText2 = (TextView) findViewById(R.id.atack2);
        atakText2.setText(myEetakemon.getEetakemonAtack().get(1).getName());
        atakText3 = (TextView) findViewById(R.id.atack3);
        atakText3.setText(myEetakemon.getEetakemonAtack().get(2).getName());
        atakText4 = (TextView) findViewById(R.id.atack4);
        atakText4.setText(myEetakemon.getEetakemonAtack().get(3).getName());
        atackText = (TextView) findViewById(R.id.Atacar);
        exitText = (TextView) findViewById(R.id.Salir);
        atakText1.setVisibility(View.INVISIBLE);
        atakText2.setVisibility(View.INVISIBLE);
        atakText3.setVisibility(View.INVISIBLE);
        atakText4.setVisibility(View.INVISIBLE);
    }
    private void whoIam(){
        if(party.getCandidate1().getName()==user.getName()) {
            myEetakemon=party.getCandidate1().getEetakemons().get(0);
            iAmCandidate1=true;

        }
        else
        {
            myEetakemon=party.getCandidate2().getEetakemons().get(0);
            iAmCandidate1=true;
        }
    }
    private void waitVsible(){
        TextView wait = (TextView) findViewById(R.id.textView11);
        wait.setVisibility(View.VISIBLE);
        ProgressBar waiting=(ProgressBar)findViewById(R.id.progressBar4);
        waiting.setVisibility(View.VISIBLE);
        atakText1.setVisibility(View.INVISIBLE);
        atakText2.setVisibility(View.INVISIBLE);
        atakText3.setVisibility(View.INVISIBLE);
        atakText4.setVisibility(View.INVISIBLE);
        atackText.setVisibility(View.INVISIBLE);
        exitText.setVisibility(View.INVISIBLE);
    }
    private void waitInvisible() {
        TextView wait = (TextView) findViewById(R.id.textView11);
        wait.setVisibility(View.INVISIBLE);
        ProgressBar waiting = (ProgressBar) findViewById(R.id.progressBar4);
        waiting.setVisibility(View.INVISIBLE);
    }
    public void atacak(View view) {
        atakText1.setVisibility(View.VISIBLE);
        atakText2.setVisibility(View.VISIBLE);
        atakText3.setVisibility(View.VISIBLE);
        atakText4.setVisibility(View.VISIBLE);
        atackText.setVisibility(View.INVISIBLE);
        exitText.setVisibility(View.INVISIBLE);
    }
    public void onBackPressed() {
        atakText1.setVisibility(View.INVISIBLE);
        atakText2.setVisibility(View.INVISIBLE);
        atakText3.setVisibility(View.INVISIBLE);
        atakText4.setVisibility(View.INVISIBLE);
        atackText.setVisibility(View.VISIBLE);
        exitText.setVisibility(View.VISIBLE);
    }
    private void Adapt(){
        rivalEetakemonHealthProgessBar.setProgress(rivalEetakemon.getPs()*100/rivalEetakemonHealth);
        myEetakemonHealthProgessBar.setProgress(myEetakemon.getPs()*100/myEetakemonHealth);
        myPs.setText(String.valueOf(myEetakemon.getPs()+ "/" + myEetakemonHealth));
        rivalPs.setText(String.valueOf(rivalEetakemon.getPs()+ "/" +rivalEetakemonHealth));
    }
    private void getParty() {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);
        final Call<Party> getParty = service.getParty(token);
        getParty.enqueue(new Callback<Party>() {
            @Override
            public void onResponse(Call<Party> call, Response<Party> response) {
                if(response.body()==null){
                    waitVsible();
                    getParty();
                }
                else {
                    waitInvisible();
                    whoIam();
                    prepareAcitivty();
                    if(party.getTurnIndication().get(user.getName())==true)
                        onBackPressed();
                }
            }
            @Override
            public void onFailure(Call<Party> call, Throwable t) {
                Toast.makeText(Fight.this, "No se ha podido acceder al servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
