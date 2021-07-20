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

public class PictureViewHolder extends RecyclerView.ViewHolder {

    private ImageView imagePhoto;
    private Button imageDelete;
    private TextView imageDescription;



    public PictureViewHolder(@NonNull View itemView) {
        super(itemView);

        imagePhoto = itemView.findViewById(R.id.photo_image);
        imageDelete = itemView.findViewById(R.id.photo_delete);
        imageDescription = itemView.findViewById(R.id.photo_description);

    }

    public void updatePhoto(String picturePath, String pictureDescription, Boolean isEdit){
        RequestManager glide = Glide.with(itemView);
        //PICTURE
        final InputStream imageStream;
        try {
            imageStream = imageDescription.getContext().getContentResolver().openInputStream(Uri.parse(picturePath));
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            glide.load(selectedImage).apply(RequestOptions.centerCropTransform()).into(imagePhoto);
            //imagePhoto.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //DESCRIPTION
        imageDescription.setText(pictureDescription);
        imageDescription.setTextColor(Color.WHITE);
        //DELETE and IMAGE DESC
        if (isEdit){
            imageDelete.setVisibility(View.GONE);
            imageDescription.setEnabled(false);
        }else {
            imageDelete.setVisibility(View.VISIBLE);
            imageDescription.setEnabled(true);
        }

    }


}
