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
    User Administrator;
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
        Administrator=new User();
        Administrator.setEmail(username.getText().toString().trim());
        Administrator.setPassword(password.getText().toString().trim());

        if(Administrator.getEmail().equals("Admin") && Administrator.getPassword().equals("Admin")){
            Intent Homescreen=new Intent(getApplicationContext(),HomeScreen.class);
            startActivity(Homescreen);
        }

    }

}