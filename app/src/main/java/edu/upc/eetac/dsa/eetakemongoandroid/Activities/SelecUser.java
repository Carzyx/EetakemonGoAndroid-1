package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;

import edu.upc.eetac.dsa.eetakemongoandroid.Adapter;
import edu.upc.eetac.dsa.eetakemongoandroid.AdapterPeople;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.Eetakemon;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import edu.upc.eetac.dsa.eetakemongoandroid.R;

public class SelecUser extends AppCompatActivity {
User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capturados);
        final List<User> users =(List<User>)getIntent().getSerializableExtra("Users");
        user = (User) getIntent().getSerializableExtra("User");
        AdapterPeople adapter = new AdapterPeople(SelecUser.this, users);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                intent.putExtra("Rival", (Serializable)users.get(position));
                setResult(50, intent);
                finish();
            }
        });
    }
}
