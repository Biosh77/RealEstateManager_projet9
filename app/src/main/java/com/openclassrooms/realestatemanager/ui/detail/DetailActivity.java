package com.openclassrooms.realestatemanager.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.ui.createOrEditEstate.CreateOrEditActivity;

import static com.openclassrooms.realestatemanager.ui.createOrEditEstate.CreateOrEditActivity.PARAM_EDIT;

public class DetailActivity extends AppCompatActivity {


    private Fragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        configureAndShowDetailFragment();

    }


    private void configureAndShowDetailFragment() {
        //Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_fragment_frameLayout);

        if (detailFragment == null) {
            //Create new main fragment
            detailFragment = new DetailFragment();
            //Add it to FrameLayout container
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_fragment_frameLayout, detailFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    /**
     * For edit button
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
                editIntent.putExtra(PARAM_EDIT,true);
                startActivity(editIntent);
                return true;
            default:
                return
                        super.onOptionsItemSelected(item);
        }
    }


}
