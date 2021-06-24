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
import com.openclassrooms.realestatemanager.ui.list.ListAdapter;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private List<Estate> estates;
    private RequestManager glide;
    SearchAdapter.OnSearchEstateClickListener listener;

    interface OnSearchEstateClickListener{
        void OnSearchEstateClick(Estate estate);
    }

    public SearchAdapter(List<Estate> estates,RequestManager glide,OnSearchEstateClickListener listener) {
        this.estates = estates;
        this.glide = glide;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_list_item, parent, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
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
