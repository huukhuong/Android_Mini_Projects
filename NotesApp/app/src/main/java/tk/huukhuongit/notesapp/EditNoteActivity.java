package tk.huukhuongit.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

public class EditNoteActivity extends AppCompatActivity {

    private ImageButton btnBack, btnSave;
    private EditText txtHeader, txtContent;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        addControls();
        addEvents();
    }

    private void addControls() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        note = MainActivity.databaseHandler.getNote(id);

        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);

        txtHeader = findViewById(R.id.txtHeaderNote);
        txtContent = findViewById(R.id.txtContentNote);

        txtHeader.setText(note.getTitle());
        txtContent.setText(note.getContent());

        blindSaveButton();
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processBackIntent(v);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processSaveNote(v);
            }
        });

        txtHeader.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showSaveButton();
                } else {
                    blindSaveButton();
                }
            }
        });

        txtContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showSaveButton();
                } else {
                    blindSaveButton();
                }
            }
        });
    }

    private void blindSaveButton() {
        btnSave.setVisibility(View.INVISIBLE);
    }

    private void showSaveButton() {
        btnSave.setVisibility(View.VISIBLE);
    }

    private void processSaveNote(View v) {
        blindSaveButton();
        hideKeyboard(v);

        if (txtContent.getText().toString().length() <= 0) {
            return;
        }

        String header = txtHeader.getText().toString();
        String content = txtContent.getText().toString();
        note.setTitle(header);
        note.setContent(content);

        MainActivity.databaseHandler.updateNote(note);
    }

    private void processBackIntent(View v) {
        processSaveNote(v);
        hideKeyboard(v);
        finish();
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}