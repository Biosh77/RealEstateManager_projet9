package com.openclassrooms.realestatemanager.ui.search;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.models.SearchEstates;
import com.openclassrooms.realestatemanager.viewModels.EstateSearchViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {


    AutoCompleteTextView type;
    private TextView city;
    private TextView minRooms;
    private TextView maxRooms;
    private TextView minSurface;
    private TextView maxSurface;
    private TextView minPrice;
    private TextView maxPrice;
    private TextView minDate;
    private TextView maxDate;
    private MaterialCheckBox boxSchools;
    private MaterialCheckBox boxStores;
    private MaterialCheckBox boxRestaurants;
    private MaterialCheckBox boxParks;
    private MaterialCheckBox isSold;
    private SearchEstates searchEstates;
    EstateSearchViewModel estateSearchViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);


        type = findViewById(R.id.search_spinner_Estate);
        city = findViewById(R.id.search_ed_city);
        minRooms = findViewById(R.id.search_et_roomsMin);
        maxRooms = findViewById(R.id.search_et_roomsMax);
        minSurface = findViewById(R.id.search_et_surfaceMini);
        maxSurface = findViewById(R.id.search_et_surfaceMax);
        minPrice = findViewById(R.id.search_et_priceMini);
        maxPrice = findViewById(R.id.search_et_priceMaxi);
        minDate = findViewById(R.id.search_et_minDate);
        maxDate = findViewById(R.id.search_et_maxDate);
        boxSchools = findViewById(R.id.search_box_schools);
        boxStores = findViewById(R.id.search_box_stores);
        boxRestaurants = findViewById(R.id.search_box_restaurants);
        boxParks = findViewById(R.id.search_box_park);
        isSold = findViewById(R.id.available_sold);


        dropDownAdapters();
        configureToolbar();
        configureViewModel();
        setCalendar();
        onTouch();

    }

    private void configureViewModel() {
       estateSearchViewModel = new ViewModelProvider(this, Injection.provideViewModelFactory(getApplicationContext())).get(EstateSearchViewModel.class);
    }


    /**
     * For toolbar
     */
    protected void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

    }

    /**
     * For menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_toolbar_validate_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        if (item.getItemId() == R.id.menu_validate_activity_check) {
           showSearch();
            Intent intent = new Intent(this, SearchResultActivity.class);
            intent.putExtra("SearchEstate",searchEstates);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showSearch() {
        searchEstates = new SearchEstates();

        if (!type.getText().toString().isEmpty()) {
            String estateType = type.getText().toString();
            searchEstates.setEstateType(estateType);
        }

        if (!city.getText().toString().isEmpty()) {
            String estateCity = city.getText().toString();
            searchEstates.setCity(estateCity);
        }

        if (!minRooms.getText().toString().isEmpty()) {
            Integer estateMinRooms = Integer.parseInt(minRooms.getText().toString());
            searchEstates.setMinRooms(estateMinRooms);
        }

        if (!maxRooms.getText().toString().isEmpty()) {
            Integer estateMaxRooms = Integer.parseInt(maxRooms.getText().toString());
            searchEstates.setMaxRooms(estateMaxRooms);
        }

        if (!minSurface.getText().toString().isEmpty()) {
            Integer estateMinSurface = Integer.parseInt(minSurface.getText().toString());
            searchEstates.setMinSurface(estateMinSurface);
        }

        if (!maxSurface.getText().toString().isEmpty()) {
            Integer estateMaxSurface = Integer.parseInt(maxSurface.getText().toString());
            searchEstates.setMaxSurface(estateMaxSurface);
        }

        if (!minPrice.getText().toString().isEmpty()) {
            Double estateMinPrice = Double.parseDouble(minPrice.getText().toString());
            searchEstates.setMinPrice(estateMinPrice);
        }

        if (!maxPrice.getText().toString().isEmpty()) {
            Double estateMaxPrice = Double.parseDouble(maxPrice.getText().toString());
            searchEstates.setMaxPrice(estateMaxPrice);
        }

        if (!minDate.getText().toString().isEmpty()) {
            String estateMinDate = minDate.getText().toString();
            searchEstates.setMinDate(estateMinDate);
        }

        if (!maxDate.getText().toString().isEmpty()) {
            String estateMaxDate =maxDate.getText().toString();
            searchEstates.setMaxDate(estateMaxDate);
        }

        if (boxSchools.isChecked()) {
            boolean estateSchool = boxSchools.isChecked();
            searchEstates.setSchools(estateSchool);
        }

        if (boxParks.isChecked()) {
            boolean estatePark = boxParks.isChecked();
            searchEstates.setPark(estatePark);
        }

        if (boxRestaurants.isChecked()) {
            boolean estateRestaurant= boxRestaurants.isChecked();
            searchEstates.setRestaurants(estateRestaurant);
        }

        if (boxStores.isChecked()) {
            boolean estateStore= boxStores.isChecked();
            searchEstates.setStores(estateStore);
        }

        if (isSold.isChecked()) {
            boolean estateSold = isSold.isChecked();
            searchEstates.setSold(estateSold);
        }
    }




    private ArrayAdapter<String> factoryAdapter(int resId) {
        return new ArrayAdapter<>(this, R.layout.dropdown_item, getResources().getStringArray(resId));
    }

    public void dropDownAdapters() {

        type.setAdapter(factoryAdapter(R.array.estate_type));
    }

    DatePickerDialog mMinDate;
    DatePickerDialog mMaxDate;

    @SuppressLint("ClickableViewAccessibility")
    private void onTouch() {

        minDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mMinDate.show();
                return false;
            }
        });

        maxDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mMaxDate.show();
                return false;
            }
        });
    }


    private void setCalendar() {

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        Calendar newCalendar = Calendar.getInstance();

        mMinDate = new DatePickerDialog(this, (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
            newCalendar.set(year, monthOfYear, dayOfMonth);
            minDate.setText(sdf.format(newCalendar.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        mMaxDate = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            maxDate.setText(sdf.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


}
