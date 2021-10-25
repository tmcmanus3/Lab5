package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static String PACKAGE_NAME;
    static String usernameKey = "username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PACKAGE_NAME = getApplicationContext().getPackageName();

        SharedPreferences sharedPreferences = getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);

        if(!sharedPreferences.getString(usernameKey, "").equals("")){
            String userStr = sharedPreferences.getString(usernameKey, "");
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("message", userStr);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_main);
        }
    }


    public void login(View view) {
        EditText user = (EditText) findViewById(R.id.Username);
        EditText password = (EditText) findViewById(R.id.Password);
        String userStr = user.getText().toString();
        String passStr = password.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(usernameKey, userStr).apply();

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message", userStr);
        startActivity(intent);
    }

}