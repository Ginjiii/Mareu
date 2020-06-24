package com.example.mareu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import DI.DI;
import Model.Meeting;
import Service.MeetingApiService;

public class MeetingDetailActivity extends AppCompatActivity {


    private MeetingApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_meeting);
        mApiService = DI.getMeetingApiService();

        displayDetail();
        actionOnButtonback();

    }

    /**
     * get extra and show detail of meeting
     */
    private void displayDetail() {
        if (getIntent().hasExtra("meeting")) {
            Bundle meeting = getIntent().getExtras();
        }
    }

    /**
     * back to previously page
     */
    private void actionOnButtonback() {

        ImageButton mButtonBack = findViewById(R.id.back_button);
        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeetingDetailActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}