package com.openclassrooms.realestatemanager.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class PictureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView imagePhoto;
    private Button imageDelete;
    private TextView imageDescription;
    private WeakReference<PictureAdapter.Listener> callbackWeakRef;


    public PictureViewHolder(@NonNull View itemView) {
        super(itemView);

        imagePhoto = itemView.findViewById(R.id.photo_image);
        imageDelete = itemView.findViewById(R.id.photo_delete);
        imageDescription = itemView.findViewById(R.id.photo_description);

    }

    public void updatePhoto(String picturePath, String pictureDescription, Boolean isEdit, PictureAdapter.Listener callback){
        RequestManager glide = Glide.with(itemView);
        this.callbackWeakRef = new WeakReference<PictureAdapter.Listener>(callback);
        this.imageDelete.setOnClickListener(this);

        //PICTURE
        final InputStream imageStream;
        try {
            imageStream = imageDescription.getContext().getContentResolver().openInputStream(Uri.parse(picturePath));
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            glide.load(selectedImage).apply(RequestOptions.centerCropTransform()).into(imagePhoto);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //DESCRIPTION
        imageDescription.setText(pictureDescription);
        imageDescription.setTextColor(Color.WHITE);
        //DELETE and IMAGE DESC
        if (isEdit){
            imageDelete.setVisibility(View.VISIBLE);
            imageDescription.setEnabled(true);
        }else {
            imageDelete.setVisibility(View.GONE);
            imageDescription.setEnabled(true);
        }

    }


    @Override
    public void onClick(View v) {
        PictureAdapter.Listener callback = callbackWeakRef.get();
        if (callback !=null) callback.onClickDeletePicture(getAdapterPosition());
    }
}
