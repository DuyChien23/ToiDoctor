package com.toier.toidoctor.controllers;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class PatientMedicalRecord {
    private static ArrayList<Patient> patients = new ArrayList<Patient>();

    public static Button createButton(Context context, Button btnTest, String id, String time) {
        int whiteColor = 0xFFFFFFFF;
        btnTest.setBackgroundColor(whiteColor); // Set the background color to green
        btnTest.setText(time);
        btnTest.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        int heightInDp = 74;
        float scale = context.getResources().getDisplayMetrics().density;
        int heightInPixels = (int) (heightInDp * scale + 0.5f);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // Set the desired width (you can use WRAP_CONTENT, MATCH_PARENT, or specific pixel values)
                heightInPixels  // Set the desired height (you can use WRAP_CONTENT, MATCH_PARENT, or specific pixel values)
        );
        int marginInDp = 5;
        int marginInPixels = (int) (marginInDp * scale + 0.5f);
        params.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels);
        params.weight = 1.0f;
        btnTest.setLayoutParams(params);
        String tagString = id;
        btnTest.setTag(tagString);
        return btnTest;
    }
}