package com.openclassrooms.realestatemanager.ui.list;

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

import java.text.NumberFormat;
import java.util.Locale;

public class ListViewHolder extends RecyclerView.ViewHolder {


    private ImageView itemImage;
    private final TextView itemType;
    private final TextView itemCity;
    private final TextView itemPrice;
    private ImageView itemSold;


    public ListViewHolder(@NonNull View itemView) {
        super(itemView);

        itemImage = itemView.findViewById(R.id.item_picture);
        itemType = itemView.findViewById(R.id.item_title);
        itemCity = itemView.findViewById(R.id.item_subtitle);
        itemPrice = itemView.findViewById(R.id.item_price);
        itemSold = itemView.findViewById(R.id.item_sold);

    }


    public void updateWithData(Estate estate, RequestManager glide) {



        // For  type
        itemType.setText(estate.getEstateType());
        //For city
        itemCity.setText(estate.getCity());
        //Price
        String price = "$"+NumberFormat.getInstance(Locale.US).format(estate.getPrice());
        itemPrice.setText(price);
        //Sold or not
        if (estate.getSold()){
            itemSold.setImageResource(R.drawable.sold);
        }
        //Photo
        //if (!estate.xxxx.isEmpty()){
            //glide.load(xxxxx).apply(RequestOptions.centerCropTransform()).into(itemImage);
        //}else {
            glide.load(R.drawable.no_photo).apply(RequestOptions.centerCropTransform()).into(itemImage);
        //}
    }
}
