package com.example.mareu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import DI.DI;
import Model.Meeting;
import Service.MeetingApiService;

public class MeetingDetailActivity extends AppCompatActivity {
    TextView mTextViewSubject = findViewById(R.id.subject_txt);
    TextView mTextViewDateText = findViewById(R.id.date_txt);
    TextView mTextViewTime = findViewById(R.id.time_txt);
    TextView mTextViewLocation = findViewById(R.id.location_txt);
    TextView mTextViewEmail = findViewById(R.id.emails_recyclerView);


    private MeetingApiService mApiService;
    private Meeting meeting;

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
            if (getIntent().hasExtra("Meeting")){
                Bundle meeting = getIntent().getBundleExtra("Meeting");
            }
            mTextViewSubject.setText(meeting.getSubject());
            mTextViewDateText.setText(meeting.getDate());
            mTextViewTime.setText(meeting.getTime());
            mTextViewLocation.setText(meeting.getLocation());
            mTextViewEmail.setText((CharSequence) meeting.getDelegates());
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