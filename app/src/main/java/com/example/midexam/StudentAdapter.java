package com.example.midexam;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    public StudentAdapter(Context context, List<Student> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_student, parent, false);
        }
        Student s = getItem(position);

        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvId = convertView.findViewById(R.id.tvId);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);

        tvName.setText(s.name);
        tvId.setText("Student ID: " + s.id);

        GradientDrawable bg = new GradientDrawable();
        bg.setCornerRadius(50f);
        if (s.enrolled) {
            tvStatus.setText("Enrolled");
            tvStatus.setTextColor(Color.parseColor("#1B5E20"));
            bg.setColor(Color.parseColor("#C8E6C9"));
        } else {
            tvStatus.setText("Not enrolled");
            tvStatus.setTextColor(Color.parseColor("#B3261E"));
            bg.setColor(Color.parseColor("#F9DEDC"));
        }
        tvStatus.setBackground(bg);

        return convertView;
    }
}