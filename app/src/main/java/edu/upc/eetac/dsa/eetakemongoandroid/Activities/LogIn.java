package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import edu.upc.eetac.dsa.eetakemongoandroid.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogIn extends AppCompatActivity {
    String token;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //NAME,SURNAME;USERNAME;PASS;EMAIL
    }

    public void singin(View view) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);
        user = new User();
        EditText name = (EditText) findViewById(R.id.name);
        user.setName(name.getText().toString());
        EditText pasword = (EditText) findViewById(R.id.pasword);
        user.setPassword(pasword.getText().toString());
        EditText username = (EditText) findViewById(R.id.username);
        user.setUsername(username.getText().toString());
        EditText surname = (EditText) findViewById(R.id.surname);
        user.setSurname(surname.getText().toString());
        EditText mail = (EditText) findViewById(R.id.mail);
        user.setEmail(mail.getText().toString());
        user.setImage("profile/evoluciona.png");
        Call<User> login = service.logIn(user);
        login.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200){
                    token=response.headers().get("Authoritzation");
                    user = response.body();
                    Intent intent = new Intent(LogIn.this, Principal.class);
                    intent.putExtra("User", user);
                    intent.putExtra("Token", token);
                    startActivityForResult(intent, 100);}
                else
                    Toast.makeText(LogIn.this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LogIn.this, "No se ha podido acceder al servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }
}
