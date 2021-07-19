package com.openclassrooms.realestatemanager.ui.list;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.FullEstate;
import com.openclassrooms.realestatemanager.models.Picture;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class EstateListViewHolder extends RecyclerView.ViewHolder {


    private ImageView itemImage;
    private final TextView itemType;
    private final TextView itemCity;
    private final TextView itemPrice;
    private ImageView itemSold;


    public EstateListViewHolder(@NonNull View itemView) {
        super(itemView);

        itemImage = itemView.findViewById(R.id.item_picture);
        itemType = itemView.findViewById(R.id.item_title);
        itemCity = itemView.findViewById(R.id.item_subtitle);
        itemPrice = itemView.findViewById(R.id.item_price);
        itemSold = itemView.findViewById(R.id.item_sold);

    }


    public void updateWithData(FullEstate fullEstate, RequestManager glide) {


        // For  type
        itemType.setText(fullEstate.estate.getEstateType());
        //For city
        itemCity.setText(fullEstate.estate.getCity());
        //Price
        String price = "$" + NumberFormat.getInstance(Locale.US).format(fullEstate.estate.getPrice());
        itemPrice.setText(price);
        //Sold or not
        if (fullEstate.estate.getSold()) {
            itemSold.setImageResource(R.drawable.sold);
        }

        //Photo
        if (!fullEstate.estate.getEstateID().isEmpty()) {
           glide.load(fullEstate.myListPictures.get(0).getPicturePath()).apply(RequestOptions.centerCropTransform()).into(itemImage);
        } else {
            glide.load(R.drawable.no_photo).apply(RequestOptions.centerCropTransform()).into(itemImage);
        }
    }
}
