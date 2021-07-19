package com.openclassrooms.realestatemanager.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.SearchEstates;
import com.openclassrooms.realestatemanager.ui.detail.DetailActivity;
import com.openclassrooms.realestatemanager.viewModels.EstateSearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment implements EstateSearchAdapter.OnSearchEstateClickListener {


    private EstateSearchViewModel estateSearchViewModel;
    private ArrayList<Estate> estateList;
    private EstateSearchAdapter adapter;
    private RecyclerView recyclerView;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_result_search, container, false);



        recyclerView = rootView.findViewById(R.id.search_list);

        configureViewModel();
        configureRecyclerView();

        return rootView;

    }

    private void configureRecyclerView() {
        this.estateList = new ArrayList<>();
        //Create adapter
        this.adapter = new EstateSearchAdapter(this.estateList, Glide.with(this), this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void configureViewModel() {

        estateSearchViewModel = new ViewModelProvider(this, Injection.provideViewModelFactory(getContext())).get(EstateSearchViewModel.class);

        SearchEstates searchEstates = (SearchEstates) getActivity().getIntent().getSerializableExtra("SearchEstate");

        estateSearchViewModel.searchEstate(searchEstates.getEstateType(),
                searchEstates.getCity(),
                searchEstates.getMinRooms(),
                searchEstates.getMaxRooms(),
                searchEstates.getMinSurface(),
                searchEstates.getMaxSurface(),
                searchEstates.getMinPrice(),
                searchEstates.getMaxPrice(),
                searchEstates.getMinDate(),
                searchEstates.getMaxDate(),
                searchEstates.getPhotos(),
                searchEstates.getSchools(),
                searchEstates.getStores(),
                searchEstates.getPark(),
                searchEstates.getRestaurants(),
                searchEstates.getSold()).observe(getViewLifecycleOwner(),this::updateEstatesList);
    }

    private void updateEstatesList(List<Estate> estates) {
        estateList.clear();
        estateList.addAll(estates);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void OnSearchEstateClick(Estate estate) {


        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("estate",estate.getEstateID());
        startActivity(intent);

    }
}
