package com.example.mareu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import DI.DI;
import Event.DeleteMeetingEvent;
import Model.Meeting;
import Service.MeetingApiService;

public class MeetingListRecyclerViewAdapter extends RecyclerView.Adapter<MeetingListRecyclerViewAdapter.MeetingListViewHolder> implements Filterable {

    private ArrayList<Meeting> mMeetingList, myFullList;
    private MeetingApiService mApiService;



    public MeetingListRecyclerViewAdapter (ArrayList<Meeting> items) {
        mMeetingList = items;
    }

    @NonNull
    @Override
    public MeetingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_meeting, parent, false);
        MeetingListViewHolder holder = new MeetingListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingListViewHolder holder, int position) {
        final Meeting meeting = mMeetingList.get(position);
        holder.subject.setText(meeting.getSubject());
        holder.Date.setText(meeting.getDate());
        holder.Time.setText(meeting.getTime());
        holder.Location.setText(meeting.getLocation());


        String firstLetter = String.valueOf(meeting.getSubject().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(meeting);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

        holder.picture.setImageDrawable(drawable);


        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));




            }
        });
        DelegateListRecyclerViewAdapter participantsAdapter = new DelegateListRecyclerViewAdapter(meeting.getDelegates());
        holder.Delegates.setAdapter(participantsAdapter);
        holder.Delegates.addItemDecoration(new DividerItemDecoration(holder.Delegates.getContext(), DividerItemDecoration.HORIZONTAL));
    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }


    public class MeetingListViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView subject;
        TextView Location;
        TextView Date;
        ImageButton Delete;
        RecyclerView Delegates;
        TextView Time;



        public MeetingListViewHolder(@NonNull View itemView) {
            super(itemView);
            picture= itemView.findViewById(R.id.ItemImageView);
            subject= itemView.findViewById(R.id.subject_txt);
            Location= itemView.findViewById(R.id.location_txt);
            Date= itemView.findViewById(R.id.date_txt);
            Delete= itemView.findViewById(R.id.delete_button);
            Delegates= itemView.findViewById(R.id.emails_recyclerView);
            Time = itemView.findViewById(R.id.time_txt);



        }
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    public Filter getMyDateFilter(){
        return myDateFilter;
    }



    private Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            mApiService = DI.getNewInstanceApiService();
            myFullList = (ArrayList<Meeting>) mApiService.getMeetings();
            ArrayList<Meeting> filtredList = new ArrayList<>();

            if (constraint ==  null && constraint.length()== 0) {
                filtredList.addAll(myFullList);
            }else {


                constraint = constraint.toString().toLowerCase();


                for (Meeting meeting : myFullList) {
                    if (meeting.getLocation().toString().toLowerCase().contains(constraint)) {
                        filtredList.add(meeting);

                    }

                }

            }
            results.values = filtredList;
            return results;
        }




        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mMeetingList.clear();
            mMeetingList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };


    private Filter myDateFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            mApiService = DI.getNewInstanceApiService();
            myFullList = (ArrayList<Meeting>) mApiService.getMeetings();
            ArrayList<Meeting> filtredList = new ArrayList<>();

            if (constraint ==  null && constraint.length()== 0) {
                filtredList.addAll(myFullList);
            }else {


                constraint = constraint.toString().toLowerCase();


                for (Meeting meeting : myFullList) {
                    if (meeting.getDate().toString().toLowerCase().contains(constraint)) {
                        filtredList.add(meeting);

                    }

                }

            }
            results.values = filtredList;
            return results;
        }




        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mMeetingList.clear();
            mMeetingList.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

}