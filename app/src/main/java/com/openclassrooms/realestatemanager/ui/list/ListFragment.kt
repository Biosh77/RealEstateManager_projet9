package com.openclassrooms.realestatemanager.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.models.Estate
import com.openclassrooms.realestatemanager.ui.detail.DetailActivity
import com.openclassrooms.realestatemanager.ui.detail.DetailFragment
import com.openclassrooms.realestatemanager.viewModels.EstateViewModel


class ListFragment : Fragment(), ListAdapter.OnEstateClickListener {

    private val estateList = arrayListOf<Estate>()
    lateinit var estateViewModel: EstateViewModel
    private var detailFragment: DetailFragment? = null
    private lateinit var adapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_list, container, false)



        val listRecyclerView = view.findViewById<RecyclerView>(R.id.fragment_list_frame)
        adapter = ListAdapter(estateList, Glide.with(this), this)
        listRecyclerView.layoutManager = LinearLayoutManager(activity)
        listRecyclerView.adapter = adapter


        configureViewModel()


        return view
    }


    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(requireActivity().applicationContext)
        estateViewModel = ViewModelProvider(this, mViewModelFactory).get(EstateViewModel::class.java)
        estateViewModel.estates.observe(viewLifecycleOwner, { updateEstates(it) })
    }





    override fun onEstateClick(estate: Estate) {


        detailFragment = parentFragmentManager.findFragmentById(R.id.detail_fragment_frameLayout) as DetailFragment?

        if (detailFragment?.isVisible == true) {

            val fragmentTransaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.detail_fragment_frameLayout, detailFragment!!)
            fragmentTransaction.commit()

            detailFragment?.updateUi(estate)



        } else {

            val intent = Intent(activity, DetailActivity::class.java).apply { putExtra("estate", estate.estateID) }
            startActivity(intent)

       }



    }

    // UPDATE UI W VIEWMODEL
    private fun updateEstates(estates: MutableList<Estate>) {
        estateList.clear()
        estateList.addAll(estates)
        adapter.notifyDataSetChanged()

    }

}