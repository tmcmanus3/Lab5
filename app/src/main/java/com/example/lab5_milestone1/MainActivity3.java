package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {
    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText editText = (EditText) findViewById(R.id.notesContent);
        Intent intent = getIntent();
        int id = intent.getIntExtra("noteid",0);
        if (id != 0){
            noteid = id;
        }
        if(noteid != -1) {
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }


    }

    public void saveMethod(View view) {
        EditText editText = (EditText) findViewById(R.id.notesContent);
        String content = editText.toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase =  context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper helper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity2.PACKAGE_NAME, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(MainActivity2.usernameKey, "");

        String title;
        DateFormat dateformat = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");
        String date = dateformat.format(new Date());

        if(noteid == -1) {
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            helper.saveNotes(username, title, content, date);
        } else {
            title = "NOTE_" + (noteid + 1);
            helper.updateNote(title, date, content, username);
        }

        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("message", username);
        startActivity(intent);

    }


}