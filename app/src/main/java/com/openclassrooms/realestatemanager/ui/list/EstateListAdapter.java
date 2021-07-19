package com.openclassrooms.realestatemanager.ui.list;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.FullEstate;
import com.openclassrooms.realestatemanager.models.Picture;

import java.util.List;

public class EstateListAdapter extends RecyclerView.Adapter<EstateListViewHolder> {


    private List<FullEstate> estateList;
    private RequestManager glide;
    private List<Picture> picturePath;
    OnEstateClickListener listener;
    public static int selected_item = 0;




    interface OnEstateClickListener{
        void onEstateClick(FullEstate fullEstate);
    }




    public EstateListAdapter(List<FullEstate> estateList, RequestManager glide,List<Picture> picturePath , OnEstateClickListener listener) {
        this.estateList = estateList;
        this.glide = glide;
        this.picturePath = picturePath;
        this.listener = listener;
    }



    //créer la vue à partir d'un layout
    @NonNull
    @Override
    public EstateListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_list_item, parent, false);

        return new EstateListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull EstateListViewHolder holder, int position) {
        holder.updateWithData(this.estateList.get(position),glide);

        holder.itemView.setOnClickListener(v ->{
            FullEstate fullEstate = estateList.get(position);
            listener.onEstateClick(fullEstate);

            selected_item = position;
            notifyDataSetChanged();

        });

        if (selected_item == position ) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E5E5E5"));


        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

    }



    @Override
    public int getItemCount() {
        return estateList.size();
    }


    public void setEstateList(List<FullEstate> estateList) {
        this.estateList = estateList;
        notifyDataSetChanged();
    }
}
