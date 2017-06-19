package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Atack;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Party;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.ValuesForActivites;
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
        preActivity();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);

        final Call<Party> resgisterCandidate = service.resgisterCandidate(user,token);
        resgisterCandidate.enqueue(new Callback<Party>() {
            @Override
            public void onResponse(Call<Party> call, Response<Party> response) {
                token=response.headers().get("authoritzation");
                if(response.body()== null){
                    waitVisible();
                    getParty();
                }
                else {
                    party = response.body();
                    waitInvisible();
                    whoIam();
                    prepareAcitivty();
                    adapt();
                    if(party.getTurnIndication().get(user.getUsername()).equals(true))
                        onBackPressed();
                    else
                        recieveAtack();
                }
            }

            @Override
            public void onFailure(Call<Party> call, Throwable t) {
                Toast.makeText(Fight.this, "No se ha podido acceder al servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Button actions
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

    //Actions
    private void doAtack(Atack atack) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);
        party.setAtack(atack.getName());
        final Call<Party> getParty = service.doAtack(party,token);
        getParty.enqueue(new Callback<Party>() {
            @Override
            public void onResponse(Call<Party> call, Response<Party> response) {
                token=response.headers().get("authoritzation");
                whoIam();
                waitVisible();
                adapt();
                getParty();
            }

            @Override
            public void onFailure(Call<Party> call, Throwable t) {
                Toast.makeText(Fight.this, "No se ha podido acceder al servidor", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void recieveAtack() {
        waitVisible();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);
        Call<Party> getParty = service.getParty(user,token);
        getParty.enqueue(new Callback<Party>() {
            @Override
            public void onResponse(Call<Party> call, Response<Party> response) {
                token=response.headers().get("authoritzation");
                if(response.body()==null)
                    recieveAtack();
                else
                {
                    party = response.body();
                    whoIam();
                    adapt();
                    waitInvisible();
                }
            }

            @Override
            public void onFailure(Call<Party> call, Throwable t) {

            }
        });
    }
    private void getParty() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);
        final Call<Party> getParty = service.getParty(user,token);
        getParty.enqueue(new Callback<Party>() {
            @Override
            public void onResponse(Call<Party> call, Response<Party> response) {
                token=response.headers().get("authoritzation");
                if(response.body() == null){
                    waitVisible();
                    getParty();
                }
                else {
                    party = response.body();
                    waitInvisible();
                    whoIam();
                    prepareAcitivty();
                    adapt();
                    if(party.getTurnIndication().get(user.getUsername()).equals(true))
                        onBackPressed();
                    else
                        recieveAtack();
                }
            }
            @Override
            public void onFailure(Call<Party> call, Throwable t) {
                Toast.makeText(Fight.this, "No se ha podido acceder al servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Prepare status
    private void preActivity(){
        rivalEetakemonHealthProgessBar = (ProgressBar) findViewById(R.id.suProgresVida);
        rivalEetakemonHealthProgessBar.setVisibility(View.INVISIBLE);
        myEetakemonHealthProgessBar = (ProgressBar) findViewById(R.id.miProgresVida);
        myEetakemonHealthProgessBar.setVisibility(View.INVISIBLE);
        rivalPs = (TextView) findViewById(R.id.suPs);
        myPs = (TextView) findViewById(R.id.miPs);
        atakText1 = (TextView) findViewById(R.id.atack1);
        atakText2 = (TextView) findViewById(R.id.atack2);
        atakText3 = (TextView) findViewById(R.id.atack3);
        atakText4 = (TextView) findViewById(R.id.atack4);
        atackText = (TextView) findViewById(R.id.Atacar);
        exitText = (TextView) findViewById(R.id.Salir);
    }
    private void prepareAcitivty() {
        ImageView suFoto = (ImageView) findViewById(R.id.suFoto);
        ImageView miFoto = (ImageView) findViewById(R.id.miFoto);
        myEetakemonHealth = myEetakemon.getPs();
        rivalEetakemonHealth = rivalEetakemon.getPs();
        rivalEetakemonHealthProgessBar.setVisibility(View.VISIBLE);
        rivalEetakemonHealthProgessBar.setProgress(100);
        myEetakemonHealthProgessBar.setProgress(100);
        myEetakemonHealthProgessBar.setVisibility(View.VISIBLE);
        Picasso.with(this).load(JSONservice.URL + rivalEetakemon.getImage()).into(suFoto);
        Picasso.with(this).load(JSONservice.URL + myEetakemon.getImage()).into(miFoto);
        atakText1.setText(myEetakemon.getAtacks().get(0).getName());
        atakText2.setText(myEetakemon.getAtacks().get(1).getName());
        atakText3.setText(myEetakemon.getAtacks().get(2).getName());
        atakText4.setText(myEetakemon.getAtacks().get(3).getName());
        atakText1.setVisibility(View.INVISIBLE);
        atakText2.setVisibility(View.INVISIBLE);
        atakText3.setVisibility(View.INVISIBLE);
        atakText4.setVisibility(View.INVISIBLE);
    }
    private void whoIam(){
        if(party.getCandidate1().getUsername().equals(user.getUsername())) {
            myEetakemon=party.getCandidate1().getEetakemons().get(0);
            rivalEetakemon = party.getCandidate2().getEetakemons().get(0);
            iAmCandidate1=true;

        }
        else
        {
            myEetakemon=party.getCandidate2().getEetakemons().get(0);
            rivalEetakemon = party.getCandidate1().getEetakemons().get(0);
            iAmCandidate1=true;
        }
    }
    //Reload and print values
    private void waitVisible(){
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
    public void onBackPressed() {
        atakText1.setVisibility(View.INVISIBLE);
        atakText2.setVisibility(View.INVISIBLE);
        atakText3.setVisibility(View.INVISIBLE);
        atakText4.setVisibility(View.INVISIBLE);
        atackText.setVisibility(View.VISIBLE);
        exitText.setVisibility(View.VISIBLE);
    }
    private void adapt(){
        rivalEetakemonHealthProgessBar.setProgress(rivalEetakemon.getPs()*100/rivalEetakemonHealth);
        myEetakemonHealthProgessBar.setProgress(myEetakemon.getPs()*100/myEetakemonHealth);
        myPs.setText(String.valueOf(myEetakemon.getPs()+ "/" + myEetakemonHealth));
        rivalPs.setText(String.valueOf(rivalEetakemon.getPs()+ "/" +rivalEetakemonHealth));
        if(myEetakemon.getPs()<=0){
            Toast.makeText(Fight.this,"Has perdido",Toast.LENGTH_SHORT).show();
            getIntent().putExtra("Token", token);
            setResult(ValuesForActivites.StartFight.getValue(),getIntent());
            finish();
        }
        else if(rivalEetakemon.getPs()<=0){
            Toast.makeText(Fight.this,"Has ganado",Toast.LENGTH_SHORT).show();
            getIntent().putExtra("Token", token);
            setResult(ValuesForActivites.FinishFight.getValue(),getIntent());
            finish();
        }
    }
    //print action atack
    public void atacak(View view) {
        atakText1.setVisibility(View.VISIBLE);
        atakText2.setVisibility(View.VISIBLE);
        atakText3.setVisibility(View.VISIBLE);
        atakText4.setVisibility(View.VISIBLE);
        atackText.setVisibility(View.INVISIBLE);
        exitText.setVisibility(View.INVISIBLE);
    }




}
