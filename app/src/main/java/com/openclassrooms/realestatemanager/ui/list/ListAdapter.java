package com.openclassrooms.realestatemanager.ui.list;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Estate;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {


    private List<Estate> estateList;
    private RequestManager glide;
    OnEstateClickListener listener;




    interface OnEstateClickListener{
        void onEstateClick(Estate estate);
    }




    public ListAdapter(List<Estate> estateList, RequestManager glide,OnEstateClickListener listener) {
        this.estateList = estateList;
        this.glide = glide;
        this.listener = listener;
    }



    //créer la vue à partir d'un layout
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_list_item, parent, false);

        return new ListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.updateWithData(this.estateList.get(position),glide);
        holder.itemView.setOnClickListener(v ->{
            Estate estate = estateList.get(position);
            listener.onEstateClick(estate);
        });
    }


    @Override
    public int getItemCount() {
        return estateList.size();
    }



}
