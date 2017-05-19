package edu.upc.eetac.dsa.eetakemongoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Eetakedex extends AppCompatActivity {
List<Eetackemon> eetackemons =new ArrayList<Eetackemon>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eetakedex);
        Eetackemon eetackemon =new Eetackemon("anan",1,10,EetakemonType.AIRE,"dfgh","SDFGH");
        eetackemons.add(eetackemon);

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, eetackemons);
        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Eetakedex.this,VerEetakemon.class);
                startActivity(intent);
            }
        });
    }
}
