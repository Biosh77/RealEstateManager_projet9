package com.openclassrooms.realestatemanager.ui.loansimulator

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.openclassrooms.realestatemanager.R
import java.text.DecimalFormat


class LoanSimulatorActivity : AppCompatActivity(), View.OnClickListener {


    private var loanAmount: EditText? = null
    private var contribution: EditText? = null
    private var rate: EditText? = null
    private var years: EditText? = null
    private lateinit var monthlyPayment: TextView
    private lateinit var loanCalculation: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loan_simulation)

        loanAmount = findViewById(R.id.loan_amount)
        contribution = findViewById(R.id.contribution)
        rate = findViewById(R.id.rate)
        years = findViewById(R.id.years)
        monthlyPayment = findViewById(R.id.loan_monthly_payment)

        loanCalculation = findViewById(R.id.loan_calculation)
        loanCalculation.setOnClickListener(this)


        configureToolbar()

    }

    override fun onClick(v: View) {
        if (v == loanCalculation) {

            val mLoanAmount: Int = loanAmount!!.text.toString().toInt()
            val mContribution: Int = contribution!!.text.toString().toInt()
            val mRate: Double = rate!!.text.toString().toDouble()
            val mYears: Int = years!!.text.toString().toInt()

            val decimalFormat = DecimalFormat("#.##")

            val loanRate = (mLoanAmount - mContribution) * ((mRate / 100))

            val mMonthly = ( mLoanAmount - mContribution + loanRate ) / (mYears * 12)

            monthlyPayment.text = decimalFormat.format(mMonthly).toString()
        }

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


