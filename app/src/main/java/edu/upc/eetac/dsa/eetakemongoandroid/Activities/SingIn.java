package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import edu.upc.eetac.dsa.eetakemongoandroid.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SingIn extends AppCompatActivity {
    private User user;
    private String token;
    private ProgressBar progressBar;
    private  Retrofit retrofit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new User();
        setContentView(R.layout.activity_sing_in);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.INVISIBLE);

    }

    public void logIn(View view) {
        Intent intent = new Intent(SingIn.this, LogIn.class);
        startActivityForResult(intent, 100);
    }

    public void singIn(View view) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);
        TextView name = (TextView) findViewById(R.id.user);
        TextView pass = (TextView) findViewById(R.id.pasword);
        user.setUsername(name.getText().toString());
        user.setPassword(pass.getText().toString());
        Call<User> singIn = service.SingIn(user);
        progressBar.setVisibility(View.VISIBLE);
        singIn.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code()==200) {
                    user = response.body();
                    token=response.headers().get("authoritzation");
                    Intent intent = new Intent(SingIn.this, Principal.class);
                    intent.putExtra("User", user);
                    intent.putExtra("Token", token);
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivityForResult(intent, 100);
                } else
                    Toast.makeText(SingIn.this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SingIn.this, "No se ha podido acceder al servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == RESULT_OK)
            finish();
    }

}
