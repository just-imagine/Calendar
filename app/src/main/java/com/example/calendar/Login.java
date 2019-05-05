package com.example.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendar.Booking;
import com.example.calendar.R;

public class Login extends AppCompatActivity {
    EditText username,password;
    Button Login;
    TextView res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password=(EditText) this.findViewById(R.id.password);
        username=(EditText) this.findViewById(R.id.user);
        Login=(Button)findViewById(R.id.loginbut);
        res=(TextView)findViewById(R.id.textView);


    }
    public void doLogin(View v){
        String userna="Admin";
        String pass="Admin";
        if(username.getText().toString().trim().equals(userna) && password.getText().toString().trim().equals(pass) ){
            Intent Home=new Intent(getApplicationContext(),HomeScreen.class);
            startActivity(Home);
        }
        else if(username.getText().length()==0 && password.getText().length()==0){
            username.setError("Enter username.");
            password.setError("Enter password.");
        }
        else if(username.getText().length()==0 && password.getText().length()>0){
            username.setError("Enter username.");
        }
        else if(username.getText().length()>0 && password.getText().length()==0){
            password.setError("Enter password.");
        }
        else{
            Toast.makeText(getApplicationContext(),"Incorrect Credentials",Toast.LENGTH_SHORT).show();
        }

    }

}