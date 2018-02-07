package cfd.ram.attendance;

import android.*;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by mansi on 6/2/18.
 */

public class MarkAttendance extends AppCompatActivity {

    FirebaseAuth mAuth;

    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();

    private static final int REQUEST_LOCATION = 1;
    TextView stud_attendance, text1;
    Button buShow,face_detection;
    Boolean mattend=false;
    int stu_attendance;
    double latti, longi,courseLong,courseLat;
    LocationManager locationManager;
    Bitmap mbitmap;
    String stud_lattitude, studentImage,stud_longitude,courseCode,studentRoll,studentAttendance,course_latti, course_longi,monEnd,monStart,tuStart,tuEnd,wedStart,wedEnd,thursEnd,thursStart,friStart,friEnd,satStart,satEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_mark_attendance);


        buShow = findViewById(R.id.buShow);
        face_detection = findViewById(R.id.buFaceDetect);
        text1 = findViewById(R.id.text);
        stud_attendance = findViewById(R.id.attendance_count);

        Bundle b=getIntent().getExtras();
        courseCode=b.getString("Code");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }

        RetrieveStudentDetails();

        buShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAttendance();
                stud_attendance.setVisibility(View.VISIBLE);
                text1.setVisibility(View.VISIBLE);
            }
        });

        face_detection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GotoDetection();
            }
        });


    }

    public void GotoDetection(){
        Intent intent=new Intent(this,FaceVerificationActivity.class);
        intent.putExtra("Bitmap",mbitmap);
        startActivity(intent);
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MarkAttendance.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MarkAttendance.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MarkAttendance.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                latti = location.getLatitude();
                longi = location.getLongitude();
                stud_lattitude = String.valueOf(latti);
                stud_longitude = String.valueOf(longi);

            } else  if (location1 != null) {
                latti = location1.getLatitude();
                longi = location1.getLongitude();
                stud_lattitude = String.valueOf(latti);
                stud_longitude = String.valueOf(longi);

            } else  if (location2 != null) {
                latti = location2.getLatitude();
                longi = location2.getLongitude();
                stud_lattitude = String.valueOf(latti);
                stud_longitude = String.valueOf(longi);

            }else{

                Toast.makeText(this,"Unable to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void RetrieveStudentDetails(){
        myRef.child("Institute").child("Student").child("Student Info")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                            String email= (String) postSnapShot.child("email").getValue();

                            if (email.equals(mAuth.getCurrentUser().getEmail()) ){
                                studentRoll=(String) postSnapShot.child("roll").getValue();
                            }
                        }
                        RetrieveAttendance();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public void RetrieveAttendance(){
        myRef.child("Institute").child("Student").child("Student Courses").child(studentRoll).child(courseCode)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        studentAttendance=(String) dataSnapshot.child("attendance").getValue();
                        course_latti=(String) dataSnapshot.child("latitude").getValue();
                        course_longi=(String) dataSnapshot.child("longitude").getValue();
                        monStart=(String) dataSnapshot.child("monStart").getValue();
                        monEnd=(String) dataSnapshot.child("monEnd").getValue();
                        tuStart=(String) dataSnapshot.child("tuStart").getValue();
                        tuEnd=(String) dataSnapshot.child("tuEnd").getValue();
                        wedStart=(String) dataSnapshot.child("wedStart").getValue();
                        wedEnd=(String) dataSnapshot.child("wedEnd").getValue();
                        thursStart=(String) dataSnapshot.child("thursStart").getValue();
                        thursEnd=(String) dataSnapshot.child("thursEnd").getValue();
                        friStart=(String) dataSnapshot.child("friStart").getValue();
                        friEnd=(String) dataSnapshot.child("friEnd").getValue();
                        satStart=(String) dataSnapshot.child("satStart").getValue();
                        satEnd=(String) dataSnapshot.child("satEnd").getValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void checkAttendance(){
        courseLong=Double.parseDouble(course_longi);
        courseLat=Double.parseDouble(course_latti);

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        SimpleDateFormat sd = new SimpleDateFormat("mm");
        String presentHour=sdf.format(calendar.getTime());
        String presentMin=sd.format(calendar.getTime());

        stu_attendance=StringToInt(studentAttendance);

        switch (day) {
            case Calendar.MONDAY:
                mattend=true;
                if (CompareTime(monStart,monEnd,presentHour,presentMin)){
                    stu_attendance+=1;
                    myRef.child("Institute").child("Student").child("Student Courses").child(studentRoll).child(courseCode).child("attendance").setValue(IntToString(stu_attendance));
                    mattend=false;
                }
                break;
            case Calendar.TUESDAY:
                mattend=true;
                if (CompareTime(tuStart,tuEnd,presentHour,presentMin)){
                    stu_attendance+=1;
                    myRef.child("Institute").child("Student").child("Student Courses").child(studentRoll).child(courseCode).child("attendance").setValue(IntToString(stu_attendance));
                    mattend=false;
                }
                break;
            case Calendar.WEDNESDAY:
                mattend=true;
                if (CompareTime(wedStart,wedEnd,presentHour,presentMin)){
                    stu_attendance+=1;
                    myRef.child("Institute").child("Student").child("Student Courses").child(studentRoll).child(courseCode).child("attendance").setValue(IntToString(stu_attendance));
                    mattend=false;
                }
                break;
            case Calendar.THURSDAY:
                mattend=true;
                if (CompareTime(thursStart,thursEnd,presentHour,presentMin)){
                    stu_attendance+=1;
                    myRef.child("Institute").child("Student").child("Student Courses").child(studentRoll).child(courseCode).child("attendance").setValue(IntToString(stu_attendance));
                    mattend=false;
                }
                break;
            case Calendar.FRIDAY:
                mattend=true;
                if (CompareTime(friStart,friEnd,presentHour,presentMin)){
                    stu_attendance+=1;
                    myRef.child("Institute").child("Student").child("Student Courses").child(studentRoll).child(courseCode).child("attendance").setValue(IntToString(stu_attendance));
                    mattend=false;
                }
                break;
            case Calendar.SATURDAY:
                mattend=true;
                if (CompareTime(satStart,satEnd,presentHour,presentMin)){
                    stu_attendance+=1;
                    myRef.child("Institute").child("Student").child("Student Courses").child(studentRoll).child(courseCode).child("attendance").setValue(IntToString(stu_attendance));
                    mattend=false;
                }
                break;
        }
        stud_attendance.setText(IntToString(stu_attendance));
    }

    public void show(){
        stud_attendance.setVisibility(View.VISIBLE);
    }

    public Boolean CompareTime(String start,String end,String presentHour,String presentMin){
        String str[]=start.split(":",2);
        String en[]=end.split(":",2);

        if (latti<=StringToDouble(course_latti)+0.5 && longi<=StringToDouble(course_longi)+0.5 && StringToInt(str[0])<=StringToInt(presentHour) && StringToInt(presentHour)<StringToInt(en[0])){
            return true;
        }else {
            return false;
        }
    }

    public String IntToString(Integer a){
        return a.toString();
    }

    public Integer StringToInt(String a){
        return Integer.valueOf(a);
    }

    public Double StringToDouble(String a){
        return Double.valueOf(a);
    }
}


