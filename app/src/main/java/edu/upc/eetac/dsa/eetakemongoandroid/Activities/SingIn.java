package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import edu.upc.eetac.dsa.eetakemongoandroid.R;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingIn extends AppCompatActivity {
User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);
        //NAME,SURNAME;USERNAME;PASS;EMAIL
    }
    public void singin(View view){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);
        user=new User();
        EditText name=(EditText)findViewById(R.id.name);
        user.setName(name.getText().toString());
        EditText pasword=(EditText)findViewById(R.id.pasword);
        user.setPassword(pasword.getText().toString());
        EditText username=(EditText)findViewById(R.id.username);
        user.setUsername(username.getText().toString());
        Call<User> singIn=service.singIn(user);
        Intent intent=new Intent(SingIn.this,Principal.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
