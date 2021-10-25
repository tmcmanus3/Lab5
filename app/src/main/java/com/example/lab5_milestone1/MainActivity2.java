package com.example.lab5_milestone1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.internal.NavigationMenu;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    TextView welcome;
    private NavigationMenu navigationMenu;
    public static String PACKAGE_NAME;
    public static ArrayList<Note> notes = new ArrayList<>();
    static String usernameKey = "username";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        PACKAGE_NAME = getApplicationContext().getPackageName();
        welcome = (TextView) findViewById(R.id.Welcome);
        Intent intent = getIntent();
        String str = intent.getStringExtra("message");
        welcome.setText("Welcome " + str + "!");

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase =  context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper helper = new DBHelper(sqLiteDatabase);

        SharedPreferences sharedPreferences = getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
        String userStr = sharedPreferences.getString(usernameKey, "");
        notes = helper.readNotes(userStr);


        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void logout() {
        Intent intent = new Intent(this, MainActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(MainActivity.usernameKey).apply();
        startActivity(intent);
    }

    public void addNote() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Logout:
                logout();
                return true;
            case R.id.AddNote:
                addNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}