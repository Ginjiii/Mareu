package com.example.mareu;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class DelegateListRecyclerViewAdapter extends RecyclerView.Adapter<DelegateListRecyclerViewAdapter.DelegateListViewHolder>{
    private ArrayList<String> myDelegate;

    public DelegateListRecyclerViewAdapter(ArrayList delegate) {
        myDelegate = delegate;
    }


    @NonNull
    @Override
    public DelegateListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_delegate, parent, false);
        DelegateListViewHolder holder = new DelegateListViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull DelegateListViewHolder holder, int position) {
        String delegate = myDelegate.get(position);
        holder.delegate.setText(delegate);

    }

    @Override
    public int getItemCount() {

        return myDelegate.size();
    }

    public class DelegateListViewHolder extends RecyclerView.ViewHolder{
        TextView delegate;



        public DelegateListViewHolder(@NonNull View itemView) {
            super(itemView);
            delegate = itemView.findViewById(R.id.delegate_txt);




        }
    }
}
