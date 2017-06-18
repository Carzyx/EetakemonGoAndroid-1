package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;

import edu.upc.eetac.dsa.eetakemongoandroid.Adapter;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.ValuesForActivites;
import edu.upc.eetac.dsa.eetakemongoandroid.R;

/**
 * Created by Ignacio on 16/06/2017.
 */

public class SelectEetackemon extends AppCompatActivity {
    private User user;
    private String token;
    private Eetakemon eetakemon = new Eetakemon();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturados);
        user = (User) getIntent().getSerializableExtra("User");
        eetakemon = (Eetakemon) getIntent().getSerializableExtra("Eetakemon");
        Adapter adapter = new Adapter(SelectEetackemon.this, user.getEetakemons());
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                intent.putExtra("Eetakemon", (Serializable) eetakemon);
                intent.putExtra("miEetakemon", (Serializable) user.getEetakemons().get(position));
                setResult(ValuesForActivites.SelectEetackemon.getValue(), intent);
                finish();
            }
        });
    }
}
