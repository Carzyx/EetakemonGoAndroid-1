package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Atack;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.R;

public class Captura extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);
        suEetakemon=(Eetakemon) getIntent().getSerializableExtra("Eetakemon");
        miEetakemon=(Eetakemon) getIntent().getSerializableExtra("miEetakemon");
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
    public void atacak(View view){
        atak1.setVisibility(View.VISIBLE);
        atak2.setVisibility(View.VISIBLE);
        atak3.setVisibility(View.VISIBLE);
        atak4.setVisibility(View.VISIBLE);
        atacar.setVisibility(View.INVISIBLE);
        salir.setVisibility(View.INVISIBLE);
    }
    public void atak1(View view){}
    public void atak2(View view){}
    public void atak3(View view){}
    public void atak4(View view){}
    private int atacar(Atack atack){
        miPs.setText(mylive+"/"+miEetakemon.getPs());
        miProgresVida.setProgress(mylive*miEetakemon.getPs()/100);
        return 0;
    }
    public void salir(View view){
        finish();
    }
    public void onBackPressed(){
        atak1.setVisibility(View.INVISIBLE);
        atak2.setVisibility(View.INVISIBLE);
        atak3.setVisibility(View.INVISIBLE);
        atak4.setVisibility(View.INVISIBLE);
        atacar.setVisibility(View.VISIBLE);
        salir.setVisibility(View.VISIBLE);
    }
}
