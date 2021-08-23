package tk.huukhuongit.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "NoteManager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "notes";

    private static final String ID = "id";
    private static final String HEADER = "header";
    private static final String CONTENT = "content";
    private static final String CREATE_AT = "create_at";
    private static final String UPDATE_AT = "update_at";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_notes_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE_NAME, ID, HEADER, CONTENT, CREATE_AT, UPDATE_AT);
        db.execSQL(create_notes_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_notes_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_notes_table);
        onCreate(db);
    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        ContentValues values = new ContentValues();
        values.put(HEADER, note.getTitle());
        values.put(CONTENT, note.getContent());
        values.put(CREATE_AT, sdf.format(date));
        values.put(UPDATE_AT, sdf.format(date));

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Note getNote(int noteID) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, ID + " = ?", new String[]{String.valueOf(noteID)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note student = new Note(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));

        return student;
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> studentList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Note student = new Note(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4));
            studentList.add(student);
            cursor.moveToNext();
        }
        return studentList;
    }

    public void updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        values.put(HEADER, note.getTitle());
        values.put(CONTENT, note.getContent());
        values.put(UPDATE_AT, sdf.format(date));

        db.update(TABLE_NAME, values, ID + " = ?", new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public void deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = " + noteId, null);
        db.close();
    }

}
