package com.example.weatherapplication.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.R
import com.example.weatherapplication.dB.ConcreteLocalSource
import com.example.weatherapplication.favorite.viewModel.FavoriteViewModel
import com.example.weatherapplication.favorite.viewModel.FavoriteViewModelFactory
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.Location
import com.example.weatherapplication.model.Repository
import com.example.weatherapplication.network.WeatherClient
import com.google.android.material.floatingactionbutton.FloatingActionButton


class  Favorite : Fragment() , Delete  {
  lateinit var fav : FloatingActionButton
    lateinit var favoriteViewModel: FavoriteViewModel
    lateinit var favoriteViewModelFactory: FavoriteViewModelFactory
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FavoriteAdapter
    lateinit var mylayoutManager: LinearLayoutManager
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fav = view.findViewById(R.id.btn_fav)
        fav.setOnClickListener {
            view.findNavController().navigate(R.id.fav_maps)
        }


            recyclerView = view.findViewById(R.id.recycler_fav)
            favoriteViewModelFactory = FavoriteViewModelFactory(Repository.getInstance(WeatherClient.getInstance() , ConcreteLocalSource.getInstance(requireContext())))
            favoriteViewModel = ViewModelProvider(this , favoriteViewModelFactory).get(FavoriteViewModel::class.java)
            favoriteViewModel.finalData.observe(requireActivity()){
                adapter = FavoriteAdapter(it  , this , requireContext())
                mylayoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
                recyclerView.layoutManager = mylayoutManager
                adapter.setList(it)
                adapter.notifyDataSetChanged()
            }

        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Favorite().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun Remove(data: Location) {
        favoriteViewModel.delete(data)
        adapter.notifyDataSetChanged()
    }


}