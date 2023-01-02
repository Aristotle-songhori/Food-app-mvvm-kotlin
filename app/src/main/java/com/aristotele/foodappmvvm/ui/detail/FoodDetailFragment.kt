package com.aristotele.foodappmvvm.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aristotele.foodappmvvm.R
import com.aristotele.foodappmvvm.viewmodel.FoodDetailViewModel

class FoodDetailFragment : Fragment() {

    companion object {
        fun newInstance() = FoodDetailFragment()
    }

    private lateinit var viewModel: FoodDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FoodDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}