package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.R;

public class Captura extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);
        Eetakemon rival =(Eetakemon)getIntent().getSerializableExtra("Eetakemon");
        ImageView suFoto=(ImageView)findViewById(R.id.suFoto);
        Picasso.with(this).load(JSONservice.URL+rival.getImage()).into(suFoto);
        ProgressBar suVida=(ProgressBar)findViewById(R.id.suVida);
        suVida.setProgress(100);

        //ImageView miFoto=(ImageView)findViewById(R.id.miFoto);
    }
    public void onBackPressed(){
        Intent intent=getIntent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }
}
