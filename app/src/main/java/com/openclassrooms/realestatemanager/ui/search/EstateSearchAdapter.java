package com.openclassrooms.realestatemanager.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.models.Estate;

import java.util.List;

public class EstateSearchAdapter extends RecyclerView.Adapter<EstateSearchViewHolder> {

    private List<Estate> estates;
    private RequestManager glide;
    EstateSearchAdapter.OnSearchEstateClickListener listener;

    interface OnSearchEstateClickListener{
        void OnSearchEstateClick(Estate estate);
    }

    public EstateSearchAdapter(List<Estate> estates, RequestManager glide, OnSearchEstateClickListener listener) {
        this.estates = estates;
        this.glide = glide;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EstateSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_list_item, parent, false);

        return new EstateSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstateSearchViewHolder holder, int position) {
        holder.updateWithData(estates.get(position),glide);
        holder.itemView.setOnClickListener(v -> {
            Estate estate = estates.get(position);
            listener.OnSearchEstateClick(estate);
        });
    }

    @Override
    public int getItemCount() {
        return estates.size();
    }
}
