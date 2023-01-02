package com.aristotele.foodappmvvm.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aristotele.foodappmvvm.R
import com.aristotele.foodappmvvm.viewmodel.FoodsListViewModel

class FoodsListFragment : Fragment() {

    companion object {
        fun newInstance() = FoodsListFragment()
    }

    private lateinit var viewModel: FoodsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_foods_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FoodsListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}