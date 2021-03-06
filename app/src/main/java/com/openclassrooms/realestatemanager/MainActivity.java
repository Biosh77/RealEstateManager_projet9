package com.openclassrooms.realestatemanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.ui.createOrEditEstate.CreateOrEditActivity;
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment;
import com.openclassrooms.realestatemanager.ui.list.ListFragment;
import com.openclassrooms.realestatemanager.ui.loansimulator.LoanSimulatorActivity;
import com.openclassrooms.realestatemanager.ui.map.MapsActivity;
import com.openclassrooms.realestatemanager.ui.search.SearchActivity;
import com.openclassrooms.realestatemanager.viewModels.EstateViewModel;

import java.util.List;

import static com.openclassrooms.realestatemanager.ui.createOrEditEstate.CreateOrEditActivity.PARAM_EDIT;

public class MainActivity extends AppCompatActivity {


    private Fragment listFragment;
    private Fragment detailFragment;
    private FloatingActionButton addEstate;
    private EstateViewModel estateViewModel;
    private Estate estate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.addEstate = findViewById(R.id.add_form_button);

        configureViewModel();
        configureAndShowListFragment();
        configureAndShowDetailFragment();
        configureToolbar();
        onClickAddEstate();
    }

    /**
     * For toolbar
     */
    protected void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
    }


    private void configureViewModel() {
        estateViewModel = new ViewModelProvider(this, Injection.provideViewModelFactory(this)).get(EstateViewModel.class);
        estateViewModel.getEstates().observe(this, estates -> {
            for (int i = 0; i < estates.size(); i++) {
                   estate = estates.get(i);
                }
        });
    }


    /**
     * For click on button on toolbar
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        //Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.edit_btn:
                Intent editIntent = new Intent(this, CreateOrEditActivity.class);
                editIntent.putExtra(PARAM_EDIT, true);
                editIntent.putExtra("estateEditId", estate.getEstateID());
                startActivity(editIntent);
                return true;
            case R.id.search_btn:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            case R.id.map_btn:
                Intent mapIntent = new Intent(this, MapsActivity.class);
                startActivity(mapIntent);
                return true;
            case R.id.loan_btn:
                Intent loanIntent = new Intent(this, LoanSimulatorActivity.class);
                startActivity(loanIntent);
                return true;
            default:
                return
                        super.onOptionsItemSelected(item);
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
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }


    public void onClickAddEstate() {
        addEstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent formIntent = new Intent(getApplicationContext(), CreateOrEditActivity.class);
                formIntent.putExtra(PARAM_EDIT, false);
                startActivity(formIntent);
            }
        });
    }


    private void configureAndShowListFragment() {

        listFragment = getSupportFragmentManager().findFragmentById(R.id.list_fragment_frameLayout);

        if (listFragment == null) {
            listFragment = new ListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.list_fragment_frameLayout, listFragment).commit();
        }

    }



    private void configureAndShowDetailFragment() {
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment_frameLayout);
        if (detailFragment == null && findViewById(R.id.detail_fragment_frameLayout) != null) {
            detailFragment = new DetailFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_fragment_frameLayout, detailFragment)
                    .commit();
        }
    }


}
