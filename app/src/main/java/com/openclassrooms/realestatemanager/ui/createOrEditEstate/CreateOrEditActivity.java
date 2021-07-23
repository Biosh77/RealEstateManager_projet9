package com.openclassrooms.realestatemanager.ui.createOrEditEstate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.contentProvider.EstateProvider;
import com.openclassrooms.realestatemanager.models.Picture;
import com.openclassrooms.realestatemanager.photo.PictureAdapter;
import com.openclassrooms.realestatemanager.viewModels.EstateViewModel;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.models.Estate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class CreateOrEditActivity extends AppCompatActivity implements View.OnClickListener, PictureAdapter.Listener {


    private boolean isEdit;
    public final static String PARAM_EDIT = "id";

    private EstateViewModel estateViewModel;
    private Estate updateEstate;
    boolean isAllFieldsChecked = false;
    private PictureAdapter pictureAdapter;
    private Picture picture;
    private List<Picture> pictureList = new ArrayList<>();
    private static final int RESULT_LOAD_IMG = 100;
    private static final int RESULT_TAKE_IMG = 200;

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
    private TextInputLayout soldLayout;
    private AppCompatButton addPicture;
    private RecyclerView recyclerViewPhotos;
    private MaterialAlertDialogBuilder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);


        if (getIntent() != null) {
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
        isSold = findViewById(R.id.isSold);
        isSold.setOnClickListener(this);
        entryDate = findViewById(R.id.entryDateEstate);
        soldDate = findViewById(R.id.soldDate);
        soldLayout = findViewById(R.id.layout_soldDate);
        addPicture = findViewById(R.id.photo_btn);
        recyclerViewPhotos = findViewById(R.id.rv_photo);


        //Spinner
        agent = findViewById(R.id.editText_agent);
        type = findViewById(R.id.editText_Estate_type);


        setCalendar();
        configureToolbar();
        dropDownAdapters();
        configureViewModel();
        configureRecyclerView();
        onClickAddPicture();

    }


    private void configureViewModel() {
        estateViewModel = new ViewModelProvider(this, Injection.provideViewModelFactory(this)).get(EstateViewModel.class);

        String estateId = getIntent().getStringExtra("estateEditId");


        estateViewModel.getPictures(estateId).observe(this, new Observer<List<Picture>>() {
            @Override
            public void onChanged(List<Picture> pictures) {
                CreateOrEditActivity.this.updateEditPictures(pictures);

            }
        });

        estateViewModel.getEstate(estateId).observe(this, new Observer<Estate>() {
            @Override
            public void onChanged(Estate estate) {
                CreateOrEditActivity.this.updateEditEstate(estate);

            }
        });

    }

    @Override
    public void onClickDeletePicture(int position) {
        deletePicture(pictureAdapter.getPicture(position));
    }

    private void deletePicture(Picture picture) {
        estateViewModel.deletePicture(picture.getPictureId());
    }

    private void onClickAddPicture() {
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });
    }


    protected void selectPicture() {
        final CharSequence[] options = {"Take Picture", "Choose from Gallery", "Cancel"};

        builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Add pictures");
        builder.setItems(options, (dialog, item) -> {

            if (options[item].equals("Take Picture")) {

                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, RESULT_TAKE_IMG);

            } else if (options[item].equals("Choose from Gallery")) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });

        builder.show();
    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == RESULT_TAKE_IMG && resultCode == RESULT_OK) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            File destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");
            FileOutputStream fo;
            try {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Picture mPicture = new Picture("", getImageUri(this, thumbnail).toString(), "");

            if (isEdit) {
                mPicture.setIdEstate(updateEstate.getEstateID());
                pictureAdapter.addPicture(mPicture);
                //CREATE PICTURE
                estateViewModel.createPicture(mPicture);
            } else {
                pictureAdapter.addPicture(mPicture);
            }

        } else if (reqCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {

            final Uri imageUri = data.getData();
            Picture mPicture = new Picture("", imageUri.toString(), "");
            
            if (isEdit) {
                mPicture.setIdEstate(updateEstate.getEstateID());
                pictureAdapter.addPicture(mPicture);
                //CREATE PICTURE
                estateViewModel.createPicture(mPicture);
            } else {
                pictureAdapter.addPicture(mPicture);
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void configureRecyclerView() {

        pictureAdapter = new PictureAdapter(pictureList, isEdit, this);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPhotos.setLayoutManager(horizontalLayoutManager);
        recyclerViewPhotos.setAdapter(pictureAdapter);

    }


    @Override
    public void onClick(View v) {
        if (v == entryDate) {
            mEntryDate.show();
        } else if (v == soldDate) {
            mSoldDate.show();
        } else if (v == isSold) {
            if (isSold.isChecked()) {
                soldLayout.setVisibility(View.VISIBLE);
                soldDate.setVisibility(View.VISIBLE);
            } else {
                soldLayout.setVisibility(View.GONE);
                soldDate.setVisibility(View.GONE);
            }

        }

    }

    DatePickerDialog mEntryDate;
    DatePickerDialog mSoldDate;

    private void setCalendar() {

        entryDate.setOnClickListener(this);
        soldDate.setOnClickListener(this);


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


    /**
     * For toolbar
     */
    protected void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        if (!isEdit) {
            ab.setTitle("Create Estate");
        } else {
            ab.setTitle("Update Estate");
        }

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
            isAllFieldsChecked = neededFields();
            if (isAllFieldsChecked) {
                saveEstate();//saveEstate
                finish();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Save or Update in dataBase
    private void saveEstate() {

        if (!isEdit) {
            Estate estate = new Estate(
                    UUID.randomUUID().toString(),
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

            estateViewModel.createEstate(estate);

            pictureList = pictureAdapter.getPicturePath();
            ArrayList<String> descriptionList = new ArrayList<>();


            for (int i = 0; i < pictureList.size(); i++) {
                // ID
                pictureList.get(i).setIdEstate(estate.getEstateID());
                //DESCRIPTION PICTURE
                EditText desc = recyclerViewPhotos.getLayoutManager().findViewByPosition(i).findViewById(R.id.photo_description);
                String descriptionImage = desc.getText().toString();
                descriptionList.add(descriptionImage);
                pictureList.get(i).setPictureDescription(descriptionList.get(i));
                //CREATE PICTURE
                estateViewModel.createPicture(pictureList.get(i));

            }


            Toast.makeText(this, getResources().getString(R.string.createEstate), Toast.LENGTH_SHORT).show();
        } else {

            String estateType = type.getText().toString();
            updateEstate.setEstateType(estateType);

            Integer estateSurface = Integer.parseInt(surface.getText().toString());
            updateEstate.setSurface(estateSurface);

            Integer estateRooms = Integer.parseInt(rooms.getText().toString());
            updateEstate.setRooms(estateRooms);

            Integer estateBedrooms = Integer.parseInt(bedrooms.getText().toString());
            updateEstate.setBedrooms(estateBedrooms);

            Integer estateBathrooms = Integer.parseInt(bathrooms.getText().toString());
            updateEstate.setBathrooms(estateBathrooms);

            Double estatePrice = Double.parseDouble(price.getText().toString());
            updateEstate.setPrice(estatePrice);

            String estateDescription = description.getText().toString();
            updateEstate.setDescription(estateDescription);

            String estateAddress = address.getText().toString();
            updateEstate.setAddress(estateAddress);

            Integer estatePostal = Integer.parseInt(postalCode.getText().toString());
            updateEstate.setPostalCode(estatePostal);

            String estateCity = city.getText().toString();
            updateEstate.setCity(estateCity);

            boolean estateSchools = boxSchools.isChecked();
            updateEstate.setSchools(estateSchools);

            boolean estateStores = boxStores.isChecked();
            updateEstate.setStores(estateStores);

            boolean estateParks = boxParks.isChecked();
            updateEstate.setPark(estateParks);

            boolean estateRestaurants = boxRestaurants.isChecked();
            updateEstate.setRestaurants(estateRestaurants);

            boolean estateIsSold = isSold.isChecked();
            updateEstate.setSold(estateIsSold);

            String estateEntryDate = entryDate.getText().toString();
            updateEstate.setEntryDateEstate(estateEntryDate);

            String estateSoldDate = soldDate.getText().toString();
            updateEstate.setSoldDate(estateSoldDate);

            String estateAgent = agent.getText().toString();
            updateEstate.setAgentName(estateAgent);

            estateViewModel.updateEstate(updateEstate);


            // PICTURE DESCRIPTION

            pictureList = pictureAdapter.getPicturePath();

            ArrayList<String> descriptionUpdateList = new ArrayList<>();

            for (int i = 0; i < pictureList.size(); i++) {

                EditText desc = recyclerViewPhotos.getLayoutManager().findViewByPosition(i).findViewById(R.id.photo_description);
                String descriptionImage = desc.getText().toString();
                descriptionUpdateList.add(descriptionImage);
                pictureList.get(i).setPictureDescription(descriptionUpdateList.get(i));

                estateViewModel.updatePicture(pictureList.get(i));
            }

            Toast.makeText(this, getResources().getString(R.string.updateEstate), Toast.LENGTH_SHORT).show();
        }


    }


    private void updateEditEstate(Estate estate) {

        updateEstate = estate;

        if (isEdit) {

            type.setText(estate.getEstateType(), false);
            price.setText(String.valueOf(estate.getPrice()));
            description.setText(estate.getDescription());
            surface.setText(String.valueOf(estate.getSurface()));
            rooms.setText(String.valueOf(estate.getRooms()));
            bathrooms.setText(String.valueOf(estate.getBathrooms()));
            bedrooms.setText(String.valueOf(estate.getBedrooms()));
            address.setText(estate.getAddress());
            postalCode.setText(String.valueOf(estate.getPostalCode()));
            city.setText(estate.getCity());
            boxSchools.setChecked(estate.getSchools());
            boxRestaurants.setChecked(estate.getRestaurants());
            boxParks.setChecked(estate.getPark());
            boxStores.setChecked(estate.getStores());
            isSold.setChecked(estate.getSold());
            entryDate.setText(estate.getEntryDateEstate());
            soldDate.setText(estate.getSoldDate());
            agent.setText(estate.getAgentName(), false);

            if (isSold.isChecked()) {
                soldLayout.setVisibility(View.VISIBLE);
                soldDate.setVisibility(View.VISIBLE);
            }

        }

    }

    private void updateEditPictures(List<Picture> pictures) {

        if (isEdit) {
            pictureAdapter.addPictures(pictures);
        }

    }


    private boolean neededFields() {

        String soldDateInput = soldLayout.getEditText().getText().toString();

        if (soldDateInput.isEmpty() && isSold.isChecked()) {
            soldDate.setError("This field is required");
            return false;
        }


        if (type.length() == 0 && description.length() == 0 && surface.length() == 0 && rooms.length() == 0 && bathrooms.length() == 0 && bedrooms.length() == 0 && address.length() == 0 && postalCode.length() == 0 && city.length() == 0 && price.length() == 0 && agent.length() == 0) {


            type.setError("This field is required");
            description.setError("This field is required");
            surface.setError("This field is required");
            rooms.setError("This field is required");
            bathrooms.setError("This field is required");
            bedrooms.setError("This field is required");
            address.setError("This field is required");
            postalCode.setError("This field is required");
            city.setError("This field is required");
            price.setError("This field is required");
            agent.setError("This field is required");
            return false;
        }

        if (type.length() == 0) {
            type.setError("This field is required");
            return false;
        }

        if (surface.length() == 0) {
            surface.setError("This field is required");
            return false;
        }

        if (description.length() == 0) {
            description.setError("This field is required");
            return false;
        }

        if (rooms.length() == 0) {
            rooms.setError("This field is required");
            return false;
        }

        if (bathrooms.length() == 0) {
            bathrooms.setError("This field is required");
            return false;
        }

        if (bedrooms.length() == 0) {
            bedrooms.setError("This field is required");
            return false;
        }

        if (bedrooms.length() == 0) {
            bedrooms.setError("This field is required");
            return false;
        }

        if (address.length() == 0) {
            address.setError("This field is required");
            return false;
        }

        if (postalCode.length() == 0) {
            postalCode.setError("This field is required");
            return false;
        }


        if (city.length() == 0) {
            city.setError("This field is required");
            return false;
        }


        if (price.length() == 0) {
            price.setError("This field is required");
            return false;
        }

        if (agent.length() == 0) {
            agent.setError("This field is required");
            return false;
        }
        return true;
    }
}