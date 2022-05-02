package com.example.bowling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = BookingActivity.class.getName();

    private FirebaseUser user;


    Spinner spinner;
    DatePicker datePicker;
    //private FirebaseFirestore mFirestore;
    //private CollectionReference mDates;

    private NotificationHandler mNotificationHandler;
    private AlarmManager mAlarmManager;
    Button b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Log.d(LOG_TAG, "Authenticated user!");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }

        spinner = findViewById(R.id.timeSpinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.times, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        datePicker = findViewById(R.id.simpleDatePicker);
        datePicker.setMinDate(System.currentTimeMillis()-1000);
        Calendar twoWeeks = Calendar.getInstance();
        twoWeeks.add(Calendar.DATE, 14);
        datePicker.setMaxDate(twoWeeks.getTimeInMillis());

        //mFirestore = FirebaseFirestore.getInstance();
       //mDates = mFirestore.collection("reservations");

        mNotificationHandler = new NotificationHandler(this);
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        setAlarmManager();

        b = findViewById(R.id.bookingButton);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.booking:
                    return true;
            case R.id.gallery:
                Intent intent = new Intent(this, GalleryActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void booking(View view) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        b.startAnimation(animation);

        String date = datePicker.getYear()+"."+datePicker.getMonth() +"."+ datePicker.getDayOfMonth() + ".";
        String time = spinner.getSelectedItem().toString();

        mNotificationHandler.send("Sikeres foglalás! Időpont: " + date + " " + time +"! Kezdés előtt negyed órával várunk a helyszínen!");
    }

    private void setAlarmManager(){
        long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval, pendingIntent);
    }


}