package com.openclassrooms.realestatemanager.ui.createOrEditEstate;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.openclassrooms.realestatemanager.viewModels.EstateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.models.Estate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateOrEditActivity extends AppCompatActivity {



    private boolean isEdit;
    public final static String PARAM_EDIT = "id";

    private EstateViewModel estateViewModel;

    AutoCompleteTextView agent;
    AutoCompleteTextView type;
    private TextView description;
    private TextView surface;
    private TextView rooms;
    private TextView bathrooms;
    private TextView bedrooms;
    private TextView address;
    private TextView postalCode;
    private TextView city;
    private TextView price;
    private MaterialCheckBox boxSchools;
    private MaterialCheckBox boxStores;
    private MaterialCheckBox boxRestaurants;
    private MaterialCheckBox boxParks;
    private MaterialCheckBox isSold;
    private TextView entryDate;
    private TextView soldDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        if (getIntent() != null){
            isEdit = getIntent().getExtras().getBoolean(PARAM_EDIT);
        }

        description = findViewById(R.id.editText_description);
        surface = findViewById(R.id.editText_surface);
        rooms = findViewById(R.id.editText_rooms);
        bathrooms = findViewById(R.id.editText_bathrooms);
        bedrooms = findViewById(R.id.editText_bedrooms);
        address = findViewById(R.id.editText_address);
        postalCode = findViewById(R.id.editText_postalCode);
        city = findViewById(R.id.editText_city);
        price = findViewById(R.id.editText_price);
        boxSchools = findViewById(R.id.box_schools);
        boxStores = findViewById(R.id.search_box_stores);
        boxRestaurants = findViewById(R.id.search_box_restaurants);
        boxParks = findViewById(R.id.search_box_park);
        isSold = findViewById(R.id.yes);
        entryDate = findViewById(R.id.entryDateEstate);
        soldDate = findViewById(R.id.soldDate);

        //Spinner
        agent = findViewById(R.id.editText_agent);
        type = findViewById(R.id.editText_Estate_type);


        onTouch();
        setCalendar();

        configureToolbar();
        dropDownAdapters();
        configureViewModel();

    }


    @SuppressLint("ClickableViewAccessibility")
    private void onTouch() {

        entryDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEntryDate.show();
                return false;
            }
        });

        soldDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mSoldDate.show();
                return false;
            }
        });
    }

    DatePickerDialog mEntryDate;
    DatePickerDialog mSoldDate;

    private void setCalendar() {

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        Calendar newCalendar = Calendar.getInstance();

        mEntryDate = new DatePickerDialog(this, (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
            newCalendar.set(year, monthOfYear, dayOfMonth);
            entryDate.setText(sdf.format(newCalendar.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        mSoldDate = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
            soldDate.setText(sdf.format(newDate.getTime()));
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


//Spinners

    private ArrayAdapter<String> factoryAdapter(int resId) {
        return new ArrayAdapter<>(this, R.layout.dropdown_item, getResources().getStringArray(resId));
    }

    public void dropDownAdapters() {

        agent.setAdapter(factoryAdapter(R.array.agent_name));
        type.setAdapter(factoryAdapter(R.array.estate_type));
    }


    private void configureViewModel() {
        estateViewModel = new ViewModelProvider(this, Injection.provideViewModelFactory(this)).get(EstateViewModel.class);
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
            saveEstate();//saveEstate
            neededFields();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Save or Update in dataBase
    private void saveEstate() {


        Estate estate = new Estate(
                type.getText().toString(),
                Integer.parseInt(surface.getText().toString()),
                Integer.parseInt(rooms.getText().toString()),
                Integer.parseInt(bedrooms.getText().toString()),
                Integer.parseInt(bathrooms.getText().toString()),
                Double.parseDouble(price.getText().toString()),
                description.getText().toString(),
                address.getText().toString(),
                Integer.parseInt(postalCode.getText().toString()),
                city.getText().toString(),
                boxSchools.isChecked(),
                boxStores.isChecked(),
                boxParks.isChecked(),
                boxRestaurants.isChecked(),
                isSold.isChecked(),
                entryDate.getText().toString(),
                soldDate.getText().toString(),
                agent.getText().toString());

        if (!isEdit) {
            estateViewModel.createEstate(estate);
            Toast.makeText(this, getResources().getString(R.string.createEstate), Toast.LENGTH_SHORT).show();
        } else {
            estateViewModel.updateEstate(estate);
            Toast.makeText(this, getResources().getString(R.string.updateEstate), Toast.LENGTH_SHORT).show();
        }


    }

    //avec boolean isEdit ?
    private void updateEditEstate() {
        //if (isEdit)

    }


    private boolean neededFields() {

        if (description.toString().trim().isEmpty()
                && surface.toString().trim().isEmpty()
                && rooms.toString().trim().isEmpty()
                && bathrooms.toString().trim().isEmpty()
                && bedrooms.toString().trim().isEmpty()
                && address.toString().trim().isEmpty()
                && postalCode.toString().trim().isEmpty()
                && city.toString().trim().isEmpty()
                && price.toString().trim().isEmpty()
                && agent.toString().trim().isEmpty()) {

            description.setError("Required");
            surface.setError("Required");
            rooms.setError("Required");
            bathrooms.setError("Required");
            bedrooms.setError("Required");
            address.setError("Required");
            postalCode.setError("Required");
            city.setError("Required");
            price.setError("Required");
            agent.setError("Required");


            return false;
        }
        return true;
    }


    private TextWatcher estatesWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


}
