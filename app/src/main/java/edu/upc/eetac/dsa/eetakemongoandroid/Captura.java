package edu.upc.eetac.dsa.eetakemongoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetackemon;

public class Captura extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);
        Eetackemon eetackemon=(Eetackemon)getIntent().getSerializableExtra("Eetakemon");
        Toast.makeText(this,eetackemon.getName(),Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed(){
        Intent intent=getIntent();
        setResult(RESULT_OK,intent);
        finish();
    }
}
