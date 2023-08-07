package com.toier.toidoctor.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.toier.toidoctor.Appointment;
import com.toier.toidoctor.BookingClinicActivity;
import com.toier.toidoctor.Doctor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainHomeController {

    private Context context;

    private ArrayList<Doctor> listDoctor1;

    private Date d;

    private Appointment schedule = new Appointment();
    public MainHomeController(Context context) {
        this.context = context;
        this.listDoctor1 = new ArrayList<>();
    }


    // Hàm để lấy dữ liệu Doctor từ Firestore và thêm vào listDoctor
    public void fetchDoctorsFromFirestore(ListView listView) {
        List<Doctor> top = new ArrayList<Doctor>();
        // Truy cập vào collection "doctors" trong Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference doctorsRef = db.collection("Doctors");

        doctorsRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
                        //listDoctor.clear(); // Xóa danh sách hiện tại để tránh trùng lặp
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            //Log.d("XXX" , "dm e DangLeHai"  );


                            // Lấy dữ liệu từ Firestore và tạo đối tượng Doctor
                            Doctor doctor = new Doctor();
                            doctor.setID(document.get("ID").toString());
                            doctor.setAbout_doctor(document.get("about_doctor").toString());
                            doctor.setName(document.get("name").toString());
                            doctor.setMajor(document.get("major").toString());
                            double rate = document.getDouble("rate");
                            doctor.setRate(rate);
                            long res = document.getLong("review");
                            doctor.setReview((int) res);
                            long res1 = document.getLong("exp");
                            doctor.setExp((int) res1);
                            long res2 = document.getLong("patient");
                            doctor.setPatient((int) res2);


                            // Thêm đối tượng Doctor vào listDoctor

                            top.add(doctor);
                            //listDoctor1.add(doctor);
                            //Log.d("ABC", String.format("Size: %d",listDoctor.size()) );
                        }
                        //Log.d("ABC", String.format("Size: %d",listDoctor1.size()) );
                        Collections.sort(top, new Comparator<Doctor>() {
                            @Override
                            public int compare(Doctor doctor1, Doctor doctor2) {
                                // Sử dụng Double.compare() để so sánh hai giá trị Double (rate)
                                return Double.compare(doctor2.getRate(), doctor1.getRate());
                            }
                        });

                        for (int i = 0 ; i < Math.min(10,top.size()); ++i) {
                            listDoctor1.add(top.get(i));
                        }
                        ListDoctor listDoctorAdapter = new ListDoctor(context, listDoctor1);
                        listView.setAdapter(listDoctorAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Doctor doctor  = (Doctor) listDoctorAdapter.getItem(position);
                                        Intent intent = new Intent(context, BookingClinicActivity.class);
                                intent.putExtra("KEY_VALUE", doctor.getID());
                                context.startActivity(intent);
                            }
                        });
                    } else {
                        // Không có dữ liệu trong collection "doctors"
                    }
                } else {
                    // Xử lý lỗi khi lấy dữ liệu từ Firestore
                }
            }
        });
    }

    public interface OnAppointmentDataListener {
        void onAppointmentDataReceived(Appointment appointment);

        void onAppointmentDataError(String errorMessage);
    }

    //public void getDoctorData(String doctorId, OnDoctorDataListener listener) {
    public void getAppointment(String ID, boolean role, OnAppointmentDataListener listener) {

        String check = "";
        if ( !role ) {
            check = "patient_id";
        }
        else {
            check = "doctor_id";
        }
        Date ans = new Date();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        d = BookingController.convertToTimestamp(31,12,3000,1).toDate();
        //Log.d("CCC", "hoi kho");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Appointments")
                .whereEqualTo(check, ID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                                    if (document.getTimestamp("time") != null) {
                                        Timestamp cur = document.getTimestamp("time");
                                        //Log.d("CCC", String.format("%d",schedule.getTimestamp().compareTo(cur)));
                                        //Log.d("CCC", schedule.getTimestamp().toDate().toString());
                                        if (d.after(cur.toDate()) && now.before(cur.toDate())) {

                                            schedule.setTimestamp(cur);
                                            Log.d("LLL", schedule.getTimestamp().toDate().toString());
                                            schedule.setDoctor_id(document.get("doctor_id").toString());
                                            schedule.setPatient_id(document.get("patient_id").toString());
                                            d = cur.toDate();
                                        }
                                    }
                                }
                                listener.onAppointmentDataReceived(schedule);
                            }
                        }
                    }
                });
    }
}