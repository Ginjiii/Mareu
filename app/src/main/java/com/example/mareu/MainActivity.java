package com.example.mareu;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import DI.DI;
import Event.AddMeetingEvent;
import Event.DeleteMeetingEvent;
import Event.DetailMeetingEvent;
import Model.Meeting;
import Service.MeetingApiService;


public class MainActivity extends AppCompatActivity{
    private RecyclerView myRecyclerView;
    private List<Meeting> mMeetingList;
    private MeetingApiService mApiService;

    private DatePickerDialog.OnDateSetListener mOnDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mApiService = DI.getMeetingApiService();
        myRecyclerView = findViewById(R.id.MeetingItemList);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initList();

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_meeting);
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               DialogFragment fragment;
               fragment = AddMeetingFragment.newDialogInstance();
               fragment.show(getSupportFragmentManager(), "Add new meeting");
           }
       });

        Log.d("list size", "list size is" + mMeetingList.size());

    }
    public void initList(){
        mMeetingList = mApiService.getMeetings();
        MeetingListRecyclerViewAdapter adapter = new MeetingListRecyclerViewAdapter((ArrayList<Meeting>) mMeetingList);
        myRecyclerView.setAdapter(adapter);

    }



    @Override
    protected void onStart() {
        super.onStart();
        initList();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }



    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        mApiService.deleteMeeting(event.mMeeting);
        initList();
    }
    @Subscribe
    public void onCreateMeeting(AddMeetingEvent event) {
        mApiService.addMeeting(event.mMeeting);
        initList();
    }
    //@Override
    public void onItemClick(int position) {
        //Context context = getActivity();
     //   Bundle info = new Bundle();
      //  info("Meeting",mMeetingList.get(Meeting));
        Intent intent = new Intent(this, MeetingDetailActivity.class);
       // intent(info);
        startActivity(intent);
    }
    @Subscribe
    public void onDetailMeeting(DetailMeetingEvent event) {
        Intent intent = new Intent(this, MeetingDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        MenuItem searchBar = menu.findItem(R.id.search_by_location);
        SearchView searchView = (SearchView) searchBar.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MeetingListRecyclerViewAdapter adapter = new MeetingListRecyclerViewAdapter((ArrayList<Meeting>) mMeetingList);
                myRecyclerView.setAdapter(adapter);
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        MenuItem dateFilter = menu.findItem(R.id.Date_Filter_Item);
        dateFilter.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH + 1);
                int day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog_MinWidth,
                        mOnDateSetListener, year, month, day);
                dialog.show();

                mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month+1) + "/" + year;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                        String constraint = null;
                        try {
                            Date date1 = sdf.parse(date);
                            constraint = sdf.format(date1);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        MeetingListRecyclerViewAdapter adapter = new MeetingListRecyclerViewAdapter((ArrayList<Meeting>) mMeetingList);
                        myRecyclerView.setAdapter(adapter);
                        adapter.getMyDateFilter().filter(constraint);

                    }
                };

                return false;
            }
        });

        MenuItem sortAlphaItem = menu.findItem(R.id.sortBy_Alpha_item);
        sortAlphaItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                sortAlphabetically();
                return false;
            }
        });

        final MenuItem sortByDatesItem = menu.findItem(R.id.sortBy_Date_item);
        sortByDatesItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                sortByDate();
                return false;
            }
        });


        return true;

    }


    public void sortAlphabetically(){
        Collections.sort(mApiService.getMeetings(), Meeting.byAlpha);
        initList();

    }

    public void sortByDate(){
        Collections.sort(mApiService.getMeetings(), Meeting.byDate);
        initList();

    }


}