package com.openclassrooms.realestatemanager.ui.loansimulator

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.openclassrooms.realestatemanager.R

class LoanSimulatorActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_simulation)

        configureToolbar()
    }


    /**
     * For toolbar
     */
    private fun configureToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.title = "Loan Simulator"
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        toolbar.navigationIcon!!.setColorFilter(resources.getColor(R.color.white), PorterDuff.Mode.SRC_ATOP)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return true
        }


}