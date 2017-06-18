package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.upc.eetac.dsa.eetakemongoandroid.Adapter;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import edu.upc.eetac.dsa.eetakemongoandroid.R;
import edu.upc.eetac.dsa.eetakemongoandroid.View.ViewEetakemon;

public class EetakemonsCatched extends AppCompatActivity {
    private User user;
    private String token;
    private List<Eetakemon> eetakemons = new ArrayList<Eetakemon>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_capturados);
        user = (User) getIntent().getSerializableExtra("User");
        token = getIntent().getStringExtra("Token");

        Adapter adapter = new Adapter(EetakemonsCatched.this, user.getEetakemons());

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EetakemonsCatched.this, ViewEetakemon.class);
                intent.putExtra("Eetakemon", (Eetakemon) user.getEetakemons().get(position));
                startActivity(intent);
            }
        });
    }
}
