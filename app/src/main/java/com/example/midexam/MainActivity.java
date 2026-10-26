package com.example.midexam;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper db;
    private EditText etName, etId;
    private CheckBox cbEnrolled;
    private ListView lv;
    private List<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        etName = findViewById(R.id.etName);
        etId = findViewById(R.id.etId);
        cbEnrolled = findViewById(R.id.cbEnrolled);
        lv = findViewById(R.id.lvStudents);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnEdit = findViewById(R.id.btnEdit);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnView = findViewById(R.id.btnView);

        btnAdd.setOnClickListener(v -> {
            Student s = readForm();
            if (s == null) return;
            if (db.add(s)) {
                toast("Student added");
                clearForm();
                refresh();
            } else {
                toast("Student ID already exists");
            }
        });

        btnEdit.setOnClickListener(v -> {
            Student s = readForm();
            if (s == null) return;
            if (db.update(s)) {
                toast("Student updated");
                clearForm();
                refresh();
            } else {
                toast("Student ID not found");
            }
        });

        btnDelete.setOnClickListener(v -> {
            String id = etId.getText().toString().trim();
            if (id.isEmpty()) {
                toast("Enter the Student ID to delete");
                return;
            }
            if (db.delete(id)) {
                toast("Student deleted");
                clearForm();
                refresh();
            } else {
                toast("Student ID not found");
            }
        });

        btnView.setOnClickListener(v -> refresh());

        lv.setOnItemClickListener((parent, view, position, rowId) -> {
            Student s = students.get(position);
            etName.setText(s.name);
            etId.setText(s.id);
            cbEnrolled.setChecked(s.enrolled);
        });

        refresh();
    }

    private Student readForm() {
        String name = etName.getText().toString().trim();
        String id = etId.getText().toString().trim();
        if (name.isEmpty() || id.isEmpty()) {
            toast("Name and ID are required");
            return null;
        }
        return new Student(id, name, cbEnrolled.isChecked());
    }

    private void refresh() {
        students = db.getAll();
        lv.setAdapter(new StudentAdapter(this, students));
    }

    private void clearForm() {
        etName.setText("");
        etId.setText("");
        cbEnrolled.setChecked(false);
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}