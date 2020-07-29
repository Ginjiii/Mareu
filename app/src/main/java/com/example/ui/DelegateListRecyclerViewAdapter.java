package com.example.ui;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class DelegateListRecyclerViewAdapter extends RecyclerView.Adapter<DelegateListRecyclerViewAdapter.DelegateListViewHolder>{
    private ArrayList<String> myDelegate;

    public DelegateListRecyclerViewAdapter(ArrayList delegates) {
        myDelegate = delegates;
    }

    @Override
    public DelegateListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_meeting, parent, false);
        DelegateListViewHolder holder = new DelegateListViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(DelegateListViewHolder holder, int position) {
        String delegate = myDelegate.get(position);
        holder.delegates.setText(delegate);

    }

    @Override
    public int getItemCount() {

        return myDelegate.size();
    }

    public class DelegateListViewHolder extends RecyclerView.ViewHolder{
        TextView delegates;

        public DelegateListViewHolder(View itemView) {
            super(itemView);
            delegates = itemView.findViewById(R.id.item_list_delegate);
        }
    }
}