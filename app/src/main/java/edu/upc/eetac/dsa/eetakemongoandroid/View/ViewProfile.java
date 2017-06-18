package edu.upc.eetac.dsa.eetakemongoandroid.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.upc.eetac.dsa.eetakemongoandroid.JSONservice;
import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import edu.upc.eetac.dsa.eetakemongoandroid.R;

public class ViewProfile extends AppCompatActivity {
User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        user = (User) getIntent().getSerializableExtra("User");
        ImageView imageView = (ImageView) findViewById(R.id.imageView5);
        Picasso.with(ViewProfile.this).load(JSONservice.URL + user.getImage()).into(imageView);
        TextView username=(TextView)findViewById(R.id.textView);
        TextView name=(TextView)findViewById(R.id.textView2);
        TextView surname=(TextView)findViewById(R.id.textView3);
        TextView email =(TextView)findViewById(R.id.textView4);
        TextView capturados =(TextView)findViewById(R.id.textView5);

        username.setText(user.getUsername());
        name.setText("Name: "+user.getName());
        surname.setText("Surname: "+user.getSurname());
        email.setText("Email:"+user.getEmail());
        capturados.setText("Numero de capturas:"+String.valueOf(user.getEetakemons().size()));

    }
}
