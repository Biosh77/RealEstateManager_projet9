package com.openclassrooms.realestatemanager.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.MainActivity
import com.openclassrooms.realestatemanager.viewModels.EstateViewModel
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.ui.detail.DetailActivity
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment

class ListFragment : Fragment(), ListAdapter.OnEstateClickListener {

    val estateList = arrayListOf<Estate>()
    lateinit var estateViewModel: EstateViewModel
    lateinit var detailFragment: DetailFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_list, container, false)


        val listRecyclerView = view.findViewById<RecyclerView>(R.id.fragment_list_frame)
        listRecyclerView.adapter = ListAdapter(estateList, Glide.with(this), this)


        configureViewModel()

        return view
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(requireActivity().applicationContext)
        estateViewModel = ViewModelProvider(this, mViewModelFactory).get(EstateViewModel::class.java)
    }

    override fun onEstateClick(estate: Estate?) {

        detailFragment = fragmentManager?.findFragmentById(R.id.detail_fragment_frameLayout) as DetailFragment


        if (detailFragment.isVisible) {
            val intent = Intent(context, MainActivity::class.java).apply { putExtra("Estate", estate) }
            startActivity(intent)
        } else {
            val intent = Intent(context, DetailActivity::class.java).apply { putExtra("Estate", estate) }
            startActivity(intent)
        }
    }


}