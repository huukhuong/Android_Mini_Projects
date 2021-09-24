package tk.huukhuongit.notesapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnSearch, btnAdd;
    private EditText txtSearch;

    private RecyclerView rcvNotes;
    private NoteAdapter noteAdapter;
    private ArrayList<Note> noteList, copyList;

    public static DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
        addControls();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();

        noteList = databaseHandler.getAllNotes();
        copyList = new ArrayList<>(noteList);
        noteAdapter = new NoteAdapter(MainActivity.this, noteList);
        rcvNotes.setAdapter(noteAdapter);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);
        }
    }

    private void addControls() {
        databaseHandler = new DatabaseHandler(MainActivity.this);

        txtSearch = findViewById(R.id.txtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        btnAdd = findViewById(R.id.btnAdd);

        rcvNotes = findViewById(R.id.rcvNotes);
        rcvNotes.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        noteList = databaseHandler.getAllNotes();
        if (noteList.size() == 0) {
            databaseHandler.addNote(new Note(0, "Welcome to Notes App", "This app is created by Tran Huu Khuong", "13/08/2021", "13/08/2021"));
            noteList = databaseHandler.getAllNotes();
        }
        copyList = new ArrayList<>(noteList);

        noteAdapter = new NoteAdapter(MainActivity.this, noteList);
        rcvNotes.setAdapter(noteAdapter);

        OverScrollDecoratorHelper.setUpOverScroll(rcvNotes, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
    }

    private void addEvents() {
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                processSearchNotes();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processSearchNotes();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processAddNote();
            }
        });


    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void processSearchNotes() {
        String keyword = txtSearch.getText().toString();

        noteList.clear();

        if (keyword.trim().isEmpty()) {
            noteList.addAll(copyList);
            Log.e("EMPTY", "EMPTY");
        } else {
            keyword = keyword.toLowerCase();
            for (Note note : copyList) {
                if (note.getTitle().toLowerCase().contains(keyword) ||
                        note.getContent().toLowerCase().contains(keyword)) {
                    noteList.add(note);
                }
            }
        }

        noteAdapter.notifyDataSetChanged();
    }

    private void processAddNote() {
        startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
    }

}