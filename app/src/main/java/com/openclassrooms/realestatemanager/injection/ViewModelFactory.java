package com.openclassrooms.realestatemanager.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.repositories.FullEstateRepository;
import com.openclassrooms.realestatemanager.repositories.PictureDataRepository;
import com.openclassrooms.realestatemanager.viewModels.EstateViewModel;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.viewModels.EstateSearchViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory  implements ViewModelProvider.Factory  {

    private final EstateDataRepository estateDataSource;
    private final PictureDataRepository pictureDataSource;
    private final FullEstateRepository fullEstateRepository;
    private final Executor executor;


    public ViewModelFactory(EstateDataRepository estateDataSource,PictureDataRepository pictureDataSource, FullEstateRepository fullEstateRepository, Executor executor) {
        this.estateDataSource = estateDataSource;
        this.pictureDataSource = pictureDataSource;
        this.fullEstateRepository = fullEstateRepository;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EstateViewModel.class)) {
            return (T) new EstateViewModel(estateDataSource,pictureDataSource,fullEstateRepository, executor);
        }
        if (modelClass.isAssignableFrom(EstateSearchViewModel.class)){
            return (T) new EstateSearchViewModel(estateDataSource,executor);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");

    }

}
