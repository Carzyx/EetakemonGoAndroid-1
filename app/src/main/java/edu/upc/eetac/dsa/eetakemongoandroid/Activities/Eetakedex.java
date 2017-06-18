package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.upc.eetac.dsa.eetakemongoandroid.Adapter;
import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import edu.upc.eetac.dsa.eetakemongoandroid.R;
import edu.upc.eetac.dsa.eetakemongoandroid.View.ViewEetakemon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Eetakedex extends AppCompatActivity {
    User user;
    String token;
    ProgressBar progressBar;
    List<Eetakemon> eetakemons = new ArrayList<Eetakemon>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eetakedex);
        user = (User) getIntent().getSerializableExtra("User");
        token = getIntent().getStringExtra("Token");
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(JSONservice.URL).addConverterFactory(GsonConverterFactory.create()).build();
        JSONservice service = retrofit.create(JSONservice.class);
        Call<List<Eetakemon>> getAllEtakemons = service.getAllEetakemons(token);
        getAllEtakemons.enqueue(new Callback<List<Eetakemon>>() {
            @Override
            public void onResponse(Call<List<Eetakemon>> call, Response<List<Eetakemon>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                token=response.headers().get("authoritzation");
                eetakemons = response.body();
                ListView listView = (ListView) findViewById(R.id.list1);
                Adapter adapter = new Adapter(Eetakedex.this, eetakemons);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Eetakedex.this, ViewEetakemon.class);
                        intent.putExtra("Eetakemon", (Eetakemon) eetakemons.get(position));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Eetakemon>> call, Throwable t) {
                Toast.makeText(Eetakedex.this, "No se pudo conectar", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
