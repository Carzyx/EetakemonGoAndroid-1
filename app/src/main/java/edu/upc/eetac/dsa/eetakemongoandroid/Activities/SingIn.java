package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import edu.upc.eetac.dsa.eetakemongoandroid.R;

public class SingIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        //NAME,SURNAME;USERNAME;PASS;EMAIL
    }
    public void singin(View view){
        Intent intent=new Intent(SingIn.this,Principal.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}