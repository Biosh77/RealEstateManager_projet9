package com.openclassrooms.realestatemanager.photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Picture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PictureAdapter extends RecyclerView.Adapter<PictureViewHolder> {


    //CALLBACK
    public interface Listener{
        void onClickDeletePicture(int position);
    }

    private final Listener callback;
    private List<Picture> picturePath;
    private boolean isEdit;



    public PictureAdapter(List<Picture> picturePath, Boolean isEdit,Listener callback) {
        this.picturePath = picturePath;
        this.isEdit = isEdit;
        this.callback = callback;
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
        holder.updatePhoto(picturePath.get(position).getPicturePath(),picturePath.get(position).getPictureDescription(),isEdit,callback);


    }


    public List<Picture> getPicturePath() {
        return picturePath;
    }


    public Picture getPicture(int position){
        return picturePath.get(position);
    }


    //Add picture in picturePath
    public void addPicture(Picture picture){
       picturePath.add(picture);
       notifyDataSetChanged();
    }

    public void addPictures(List<Picture> pictures){
        picturePath.clear();
        picturePath.addAll(pictures);
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return picturePath.size();
    }
}
