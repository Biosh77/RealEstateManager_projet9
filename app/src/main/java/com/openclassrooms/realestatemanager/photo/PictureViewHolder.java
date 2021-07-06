package com.openclassrooms.realestatemanager.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class PictureViewHolder extends RecyclerView.ViewHolder {

    private ImageView imagePhoto;
    private Button imageDelete;
    private EditText imageDescription;



    public PictureViewHolder(@NonNull View itemView) {
        super(itemView);

        imagePhoto = itemView.findViewById(R.id.photo_image);
        imageDelete = itemView.findViewById(R.id.photo_delete);
        imageDescription = itemView.findViewById(R.id.photo_description);

    }

    public void updatePhoto(String picturePath, String pictureDescription, Boolean isEdit){

        //PICTURE
        final InputStream imageStream;
        try {
            imageStream = imageDescription.getContext().getContentResolver().openInputStream(Uri.parse(picturePath));
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imagePhoto.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //DESCRIPTION
        imageDescription.setText(pictureDescription);
        //DELETE
        if (isEdit){
            imageDelete.setVisibility(View.VISIBLE);
        }else {
            imageDelete.setVisibility(View.GONE);
        }

    }


}
