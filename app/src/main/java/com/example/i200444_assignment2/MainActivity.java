package com.example.i200444_assignment2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> title;
    DBHelper db;
    MyAdapter myAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.topBlue));
        setContentView(R.layout.activity_main);
        db = new DBHelper(this);
        title = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fillNotes();
        myAdapter = new MyAdapter(this, title);

        recyclerView.setAdapter(myAdapter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button addNote = findViewById(R.id.addnote);
        addNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddNote.class);
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search_btn);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void filterList(String newText) {
        ArrayList<String> filteredList = new ArrayList<>();
        for (String s : title) {
            if (s.toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(s);
            }
        }
        myAdapter.adapFilterList(filteredList);
    }

    public void fillNotes(){
        Cursor cursor = db.getData();
        if(cursor.getCount() == 0){

            return;
        }else{
            while(cursor.moveToNext()){
                title.add(cursor.getString(1));
            }
        }
    }

}