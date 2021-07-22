package com.openclassrooms.realestatemanager.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.models.FullEstate
import com.openclassrooms.realestatemanager.models.Picture
import com.openclassrooms.realestatemanager.ui.detail.DetailActivity
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment
import com.openclassrooms.realestatemanager.viewModels.EstateViewModel


class ListFragment : Fragment(), EstateListAdapter.OnEstateClickListener {

    private val estateList = arrayListOf<FullEstate>()
    lateinit var estateViewModel: EstateViewModel
    private var detailFragment: DetailFragment? = null
    private lateinit var adapterEstate: EstateListAdapter
    private val pictureList = arrayListOf<Picture>()




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_list, container, false)

        configureRecyclerView(view)
        configureViewModel()

        return view
    }

    private fun configureRecyclerView(view: View) {
        val listRecyclerView = view.findViewById<RecyclerView>(R.id.fragment_list_frame)
        adapterEstate = EstateListAdapter(estateList, Glide.with(this), pictureList, this)
        listRecyclerView.layoutManager = LinearLayoutManager(activity)
        listRecyclerView.adapter = adapterEstate
    }


    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(requireActivity().applicationContext)
        estateViewModel = ViewModelProvider(this, mViewModelFactory).get(EstateViewModel::class.java)


        estateViewModel.fullEstate.observe(viewLifecycleOwner, Observer { fullEstate ->
            updateEstates(fullEstate);
        })
    }


    override fun onEstateClick(estate: FullEstate) {


        detailFragment = parentFragmentManager.findFragmentById(R.id.detail_fragment_frameLayout) as DetailFragment?

        if (detailFragment?.isVisible == true) {

            val fragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.detail_fragment_frameLayout, detailFragment!!)
            fragmentTransaction.commit()

            detailFragment?.updateUi(estate.estate)
            detailFragment?.updatePictures(estate.myListPictures)


        } else {

            val intent = Intent(activity, DetailActivity::class.java).apply { putExtra("estate", estate.estate.estateID) }
            startActivity(intent)

        }

    }


    // UPDATE UI WITH VIEWMODEL
    private fun updateEstates(fullEstate: List<FullEstate>) {
        adapterEstate.setEstateList(fullEstate)
    }

}