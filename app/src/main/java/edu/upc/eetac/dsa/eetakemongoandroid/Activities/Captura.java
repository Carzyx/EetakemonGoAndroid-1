package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.R;

public class Captura extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura);
        Eetakemon eetakemon =(Eetakemon)getIntent().getSerializableExtra("Eetakemon");
        Toast.makeText(this, eetakemon.getName(),Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed(){
        Intent intent=getIntent();
        setResult(RESULT_OK,intent);
        finish();
    }
}
