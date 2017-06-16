package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.R;

public class VerEetakemon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_eetakemon);
        Eetakemon eetakemon =(Eetakemon) getIntent().getSerializableExtra("Eetakemon");
        TextView nombre=(TextView)findViewById(R.id.nombreEetakemon);
        TextView lvl=(TextView)findViewById(R.id.level);
        TextView ps=(TextView)findViewById(R.id.ps);
        TextView tipo=(TextView)findViewById(R.id.tipo);
        TextView description=(TextView)findViewById(R.id.description);
        ImageView imageView=(ImageView)findViewById(R.id.imageView4);
        Picasso.with(VerEetakemon.this).load(JSONservice.URL+ eetakemon.getImage()).into(imageView);
        nombre.setText("Nombre: "+ eetakemon.getName());
        if (eetakemon.getLevel()!=0)
        lvl.setText("Lvl: "+String.valueOf(eetakemon.getLevel()));
        else
            lvl.setVisibility(View.INVISIBLE);
        ps.setText("PS: "+String.valueOf(eetakemon.getPs()));
        tipo.setText("Tipo: "+ eetakemon.getType().toString());
        description.setText(eetakemon.getDescription());
    }

}
