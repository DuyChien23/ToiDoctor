package com.toier.toidoctor.Controller;

import com.google.firebase.firestore.DocumentReference;
import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.FirestoreClient;
import com.toier.toidoctor.R;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.toier.toidoctor.controller.patient;
public class patientMedicalRecord {

    private static ArrayList<patient> patients = new ArrayList<patient>();

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
