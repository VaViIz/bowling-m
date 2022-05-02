package com.example.bowling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.IOException;
import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity implements SensorEventListener {
    private static final String LOG_TAG = GalleryActivity.class.getName();

    private RecyclerView recyclerView;
    private ArrayList<ImgCard> imgList;
    private GalleryAdapter adapter;

    private int gridNumber = 1;

    private FirebaseFirestore mFirestore;
    private CollectionReference mCards;

    Sensor sensor;
    SensorManager sensorManager;
    boolean isRunning = false;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);




        recyclerView = findViewById(R.id.recyclearView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        imgList = new ArrayList<>();
        
        adapter = new GalleryAdapter(this, imgList);
        
        recyclerView.setAdapter(adapter);

        mFirestore = FirebaseFirestore.getInstance();
        mCards = mFirestore.collection("pictures");

        queryData();


        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


    }

    private void queryData() {
        imgList.clear();

        mCards.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document: queryDocumentSnapshots){
                ImgCard card = document.toObject(ImgCard.class);
                card.setId(document.getId());
                imgList.add(card);
            }

            if(imgList.size() == 0){
                initializeData();
                queryData();
            }
            adapter.notifyDataSetChanged();
        });
    }

    public void deleteImgCard(ImgCard card){
        DocumentReference ref = mCards.document(card._getId());
        ref.delete().addOnSuccessListener(success -> {
            Log.d(LOG_TAG, "sikeres törlés" + card._getId());
        }).addOnFailureListener(failure -> {
            Toast.makeText(this, "Pic " + card._getId() + " cannot be deleted!", Toast.LENGTH_LONG).show();
        });

        queryData();
    }

    private void initializeData() {
        String[] imgInfo = getResources().getStringArray(R.array.img_info);
        TypedArray imgResource = getResources().obtainTypedArray(R.array.img);

        //imgList.clear();

        for(int i=0; i< imgInfo.length; i++){
            mCards.add(new ImgCard(imgInfo[i], imgResource.getResourceId(i, 0)));
        }

        imgResource.recycle();

    }
    public void cancel(View view) {

        Intent intent = new Intent(this, BookingActivity.class);
        startActivity(intent);
        finish();
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

                Intent intent = new Intent(this, BookingActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.gallery:
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
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        if(isRunning){
            isRunning = false;
            mp.stop();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.values[0] > 40 && !isRunning){
            isRunning = true;

            mp = MediaPlayer.create(this, R.raw.music);
            mp.start();
        }
        if(isRunning && !mp.isPlaying()){
            isRunning = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


}
