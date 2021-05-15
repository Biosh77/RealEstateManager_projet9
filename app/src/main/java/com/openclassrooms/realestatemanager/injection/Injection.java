package com.openclassrooms.realestatemanager.injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.EstateDataBase;
import com.openclassrooms.realestatemanager.repositories.EstateDataRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static EstateDataRepository provideEstateDataSource(Context context) {
        EstateDataBase estateDatabase = EstateDataBase.getInstance(context);
        return new EstateDataRepository(estateDatabase.estateDAO());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        EstateDataRepository dataSourceEstate = provideEstateDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceEstate, executor);
    }
}
