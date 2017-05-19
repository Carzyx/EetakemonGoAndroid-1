package edu.upc.eetac.dsa.eetakemongoandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class Eetakedex extends AppCompatActivity {
List<Eetakemon> eetakemons=new ArrayList<Eetakemon>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eetakedex);
        Eetakemon eetakemon=new Eetakemon("anan",1,10,EetakemonType.AIRE,"dfgh","SDFGH");
        eetakemons.add(eetakemon);

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,eetakemons);
        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}
