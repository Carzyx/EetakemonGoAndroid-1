package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.upc.eetac.dsa.eetakemongoandroid.R;

public class Fight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelea);
        getIntent().getStringExtra("Kai");
        finish();
    }
}