package com.example.midexam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "students.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE students (" +
                "student_id TEXT PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "enrolled INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS students");
        onCreate(db);
    }

    public boolean exists(String id) {
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT 1 FROM students WHERE student_id = ?", new String[]{id});
        boolean found = c.moveToFirst();
        c.close();
        return found;
    }

    public boolean add(Student s) {
        if (exists(s.id)) return false;
        ContentValues cv = new ContentValues();
        cv.put("student_id", s.id);
        cv.put("name", s.name);
        cv.put("enrolled", s.enrolled ? 1 : 0);
        return getWritableDatabase().insert("students", null, cv) != -1;
    }

    public boolean update(Student s) {
        ContentValues cv = new ContentValues();
        cv.put("name", s.name);
        cv.put("enrolled", s.enrolled ? 1 : 0);
        return getWritableDatabase().update(
                "students", cv, "student_id = ?", new String[]{s.id}) > 0;
    }

    public boolean delete(String id) {
        return getWritableDatabase().delete(
                "students", "student_id = ?", new String[]{id}) > 0;
    }

    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT student_id, name, enrolled FROM students ORDER BY name", null);
        while (c.moveToNext()) {
            list.add(new Student(c.getString(0), c.getString(1), c.getInt(2) == 1));
        }
        c.close();
        return list;
    }
}