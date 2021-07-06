package com.openclassrooms.realestatemanager.injection;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.repositories.PictureDataRepository;
import com.openclassrooms.realestatemanager.viewModels.EstateViewModel;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;
import com.openclassrooms.realestatemanager.viewModels.PictureViewModel;
import com.openclassrooms.realestatemanager.viewModels.SearchViewModel;

import java.util.concurrent.Executor;

public class ViewModelFactory  implements ViewModelProvider.Factory  {

    private final EstateDataRepository estateDataSource;
    private final PictureDataRepository pictureDataSource;
    private final Executor executor;


    public ViewModelFactory(EstateDataRepository estateDataSource,PictureDataRepository pictureDataSource, Executor executor) {
        this.estateDataSource = estateDataSource;
        this.pictureDataSource = pictureDataSource;
        this.executor = executor;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EstateViewModel.class)) {
            return (T) new EstateViewModel(estateDataSource, executor);
        }
        if (modelClass.isAssignableFrom(SearchViewModel.class)){
            return (T) new SearchViewModel(estateDataSource,executor);
        }
        if (modelClass.isAssignableFrom(PictureViewModel.class)){
            return (T) new PictureViewModel(pictureDataSource,executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");

    }

}
