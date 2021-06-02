/*package com.openclassrooms.realestatemanager.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.viewModels.EstateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.models.Estate;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {


    private ListAdapter mAdapter;
    private EstateViewModel estateViewModel;
    private List<Estate> estateList;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = rootView.findViewById(R.id.fragment_list_frame);
        estateViewModel = new ViewModelProvider(this, Injection.provideViewModelFactory(getContext())).get(EstateViewModel.class);


        this.configureRecyclerView();



        return rootView;
    }

    private void configureRecyclerView() {



        this.estateList = new ArrayList<>();
        //créer l'adapteur
        this.mAdapter = new ListAdapter(estateList, Glide.with(this));
        //Layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

    }




}

 */

