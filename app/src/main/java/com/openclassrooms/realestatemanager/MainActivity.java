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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.ui.createOrEditEstate.CreateOrEditActivity;
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment;
import com.openclassrooms.realestatemanager.ui.list.ListFragment;
import com.openclassrooms.realestatemanager.ui.map.MapActivity;
import com.openclassrooms.realestatemanager.ui.search.SearchActivity;

public class MainActivity extends AppCompatActivity {


    private Fragment listFragment;
    private Fragment detailFragment;
    private FloatingActionButton addEstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.addEstate = findViewById(R.id.add_form_button);

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
                startActivity(editIntent);
                return true;
            case R.id.search_btn:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            case R.id.map_btn:
                Intent mapIntent = new Intent(this, MapActivity.class);
                startActivity(mapIntent);
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


    // faire un autre ShowDetailFragment pour la land view ?

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
