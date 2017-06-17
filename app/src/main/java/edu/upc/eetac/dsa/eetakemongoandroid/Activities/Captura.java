package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Atack;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Markers;
import edu.upc.eetac.dsa.eetakemongoandroid.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Captura extends AppCompatActivity {
    private ProgressBar suProgresVida;
    private ProgressBar miProgresVida;
    private Eetakemon suEetakemon;
    private Eetakemon miEetakemon;
    private TextView atakText1;
    private TextView atakText2;
    private TextView atakText3;
    private TextView atakText4;
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
        atakText1 =(TextView)findViewById(R.id.atack1);
        atakText1.setText(miEetakemon.getEetakemonAtack().get(0).getName());
        atakText2 =(TextView)findViewById(R.id.atack2);
        atakText2.setText(miEetakemon.getEetakemonAtack().get(1).getName());
        atakText3 =(TextView)findViewById(R.id.atack3);
        atakText3.setText(miEetakemon.getEetakemonAtack().get(2).getName());
        atakText4 =(TextView)findViewById(R.id.atack4);
        atakText4.setText(miEetakemon.getEetakemonAtack().get(3).getName());
        atacar=(TextView)findViewById(R.id.Atacar);
        salir=(TextView)findViewById(R.id.Salir);
        atakText1.setVisibility(View.INVISIBLE);
        atakText2.setVisibility(View.INVISIBLE);
        atakText3.setVisibility(View.INVISIBLE);
        atakText4.setVisibility(View.INVISIBLE);
    }
    public void atacak(View view){
        atakText1.setVisibility(View.VISIBLE);
        atakText2.setVisibility(View.VISIBLE);
        atakText3.setVisibility(View.VISIBLE);
        atakText4.setVisibility(View.VISIBLE);
        atacar.setVisibility(View.INVISIBLE);
        salir.setVisibility(View.INVISIBLE);
    }
    public void atak1(View view){doAtack(miEetakemon.getEetakemonAtack().get(0));}
    public void atak2(View view){doAtack(miEetakemon.getEetakemonAtack().get(1));}
    public void atak3(View view){doAtack(miEetakemon.getEetakemonAtack().get(2));}
    public void atak4(View view){doAtack(miEetakemon.getEetakemonAtack().get(3));}
    private void doAtack(Atack atack){
        herlive=herlive-atack.getDamageBase();
        suPs.setText(herlive+"/"+suEetakemon.getPs());
        suProgresVida.setProgress(herlive*suEetakemon.getPs()/100);
        if(herlive>0){
        Random r=new Random();
        recieveAtack(suEetakemon.getEetakemonAtack().get(r.nextInt(3)));}
        status();

    }
    private void recieveAtack(Atack atack){
        mylive=mylive-atack.getDamageBase();
        miPs.setText(mylive+"/"+miEetakemon.getPs());
        miProgresVida.setProgress(mylive*miEetakemon.getPs()/100);
        status();
    }
    private void status(){
        if(herlive<=0){
            getIntent().putExtra("Eetakemon",(Serializable)suEetakemon);
            Toast.makeText(this,"Los has capturado",Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK,getIntent());
            finish();
        }
        else if(mylive<=0){
            Toast.makeText(this,"Se te ha escapado",Toast.LENGTH_SHORT).show();
            setResult(RESULT_CANCELED,getIntent());
            finish();
        }
    }

    public void salir(View view){
        setResult(RESULT_CANCELED,getIntent());
        finish();
    }
    public void onBackPressed(){
        atakText1.setVisibility(View.INVISIBLE);
        atakText2.setVisibility(View.INVISIBLE);
        atakText3.setVisibility(View.INVISIBLE);
        atakText4.setVisibility(View.INVISIBLE);
        atacar.setVisibility(View.VISIBLE);
        salir.setVisibility(View.VISIBLE);
    }
}
