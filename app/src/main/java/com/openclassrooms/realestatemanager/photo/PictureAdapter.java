package com.openclassrooms.realestatemanager.photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Picture;

import java.util.ArrayList;
import java.util.List;


public class PictureAdapter extends RecyclerView.Adapter<PictureViewHolder> {



    private List<Picture> picturePath = new ArrayList<>();



    public PictureAdapter(List<Picture> picturePath) {
        this.picturePath = picturePath;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_photolist_item, parent, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        holder.updatePhoto(picturePath.get(position).getPicturePath(),picturePath.get(position).getPictureDescription(),false);
    }

    public void addPicture(Picture picture){
       picturePath.add(picture);
       notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return picturePath.size();
    }
}
