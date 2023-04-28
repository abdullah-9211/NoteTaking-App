package com.example.i200444_assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class EditNote extends AppCompatActivity {
    EditText title, desc;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.topBlue));
        setContentView(R.layout.activity_edit_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = new DBHelper(this);
        Cursor c = db.getSpecificData(getIntent().getStringExtra("title"));
        c.moveToNext();

        title = findViewById(R.id.note_title);
        desc = findViewById(R.id.note_desc);

        title.setText(c.getString(1));
        desc.setText(c.getString(2));

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    getSupportActionBar().setTitle(s);
                } else {
                    getSupportActionBar().setTitle("Edit Note");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.done_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.tick_sign) {
            String titleText = title.getText().toString();
            String descText = desc.getText().toString();
            Date d = new Date();
            String dStr = d.toString();
            boolean res = db.updateNoteData(getIntent().getStringExtra("title") ,titleText, descText, dStr);
            if (res){
                Toast.makeText(this,"Note Updated", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Error while Updating", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(EditNote.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (item.getItemId() == R.id.cancel_note) {
            db.deleteNoteData(getIntent().getStringExtra("id"));
            Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}