package com.aristotele.foodappmvvm.ui.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aristotele.foodappmvvm.R
import com.aristotele.foodappmvvm.viewmodel.FoodsFavoriteViewModel

class FoodsFavoriteFragment : Fragment() {

    companion object {
        fun newInstance() = FoodsFavoriteFragment()
    }

    private lateinit var viewModel: FoodsFavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_foods_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FoodsFavoriteViewModel::class.java)
        // TODO: Use the ViewModel
    }

}