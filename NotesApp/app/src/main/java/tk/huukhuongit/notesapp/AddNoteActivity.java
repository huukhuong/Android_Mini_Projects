package tk.huukhuongit.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddNoteActivity extends AppCompatActivity {

    private ImageButton btnBack, btnSave;
    private EditText txtHeader, txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        addControls();
        addEvents();
    }

    private void addControls() {
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);

        txtHeader = findViewById(R.id.txtHeaderNote);
        txtContent = findViewById(R.id.txtContentNote);

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
        Note note = new Note(0, header, content, "", "");
        MainActivity.databaseHandler.addNote(note);

        finish();
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