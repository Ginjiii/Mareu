package com.example.ui;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;

import Model.Meeting;

public class MeetingDetailActivity extends AppCompatActivity {

        ConstraintLayout mAvatarImageView;
        TextView mSubjectTextView;
        TextView mDateTextView;
        TextView mLocationTextView;
        TextView mTimeTextView;
        TextView mDelegatesTextView;
        TextView mAboutTitleTextView;
        TextView mAboutDescriptionTextView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail_meeting);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

            mAvatarImageView = findViewById(R.id.detail_meeting);
            mSubjectTextView = findViewById(R.id.detail_subject);
            mDateTextView = findViewById(R.id.detail_date);
            mLocationTextView = findViewById(R.id.detail_info_location);
            mTimeTextView = findViewById(R.id.detail_info_time);
            mDelegatesTextView = findViewById(R.id.detail_info_delegate);
            mAboutTitleTextView = findViewById(R.id.detail_about_title);
            mAboutDescriptionTextView = findViewById(R.id.detail_about_text);
            // Get all meeting info through Parcelable
            Meeting currentMeeting = Objects.requireNonNull(getIntent().getExtras()).getParcelable("MEETING");
            assert currentMeeting != null;
            this.displayDetails(currentMeeting);
        }


        // Back button click
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == android.R.id.home) {
                finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }


        private void displayDetails(final Meeting meeting) {
            mAvatarImageView.setBackgroundColor(meeting.getAvatarColor());
            mSubjectTextView.setText(meeting.getName());
            mDateTextView.setText(DateFormat.format("dd/MM/yyyy", meeting.getBeginTime()));
            mLocationTextView.setText(getString(R.string.meeting_in_the_x_room, meeting.getLocation().getRoom()));
            mTimeTextView.setText(getString(R.string.meeting_from_x_to_x,
                    DateFormat.format("HH:mm", meeting.getBeginTime()),
                    DateFormat.format("HH:mm", meeting.getEndTime())));
            mDelegatesTextView.setText(MeetingListRecyclerViewAdapter.appendDelegatesInString(meeting.getDelegates()));
            mAboutTitleTextView.setText(R.string.about_the_meeting);
            // Info message displayed if fill by user or not
            if (meeting.getInfo().length() < 2) {
                mAboutDescriptionTextView.setText(R.string.no_more_info);
            } else {
                mAboutDescriptionTextView.setText(meeting.getInfo());
            }
        }
}