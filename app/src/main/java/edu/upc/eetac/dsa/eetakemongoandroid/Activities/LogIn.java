package edu.upc.eetac.dsa.eetakemongoandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.upc.eetac.dsa.eetakemongoandroid.Model.User;
import edu.upc.eetac.dsa.eetakemongoandroid.R;
import retrofit2.Retrofit;

public class LogIn extends AppCompatActivity {
User user;
Retrofit retrofit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        TextView name =(TextView) findViewById(R.id.user);
        TextView pass =(TextView) findViewById(R.id.pasword);
    }
    public void singIn(View view){
        TextView name =(TextView) findViewById(R.id.user);
        TextView pass =(TextView) findViewById(R.id.pasword);
        String string=(name.getText().toString()+","+pass.getText().toString());

        Intent intent=new Intent(LogIn.this,SingIn.class);
        intent.putExtra("Name and pass",string);
        intent.putExtra("Token",string);
        startActivityForResult(intent,100);
    }
    public void onActivityResult(int requestCode,int resultCode,Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(requestCode==RESULT_OK)
            finish();
    }
    public void login(View view){
        Intent intent=new Intent(LogIn.this,Principal.class);
        startActivity(intent);
    }

}
