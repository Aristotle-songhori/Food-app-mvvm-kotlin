package com.aristotele.foodappmvvm.ui.favorite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aristotele.foodappmvvm.R
import com.aristotele.foodappmvvm.databinding.FragmentFoodsFavoriteBinding
import com.aristotele.foodappmvvm.ui.list.FoodsListFragmentDirections
import com.aristotele.foodappmvvm.utils.isVisible
import com.aristotele.foodappmvvm.utils.setupRecyclerView
import com.aristotele.foodappmvvm.viewmodel.FoodsFavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FoodsFavoriteFragment : Fragment() {
    //Binding
    private var _binding: FragmentFoodsFavoriteBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    //Other
    private val viewModel: FoodsFavoriteViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFoodsFavoriteBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding?.apply {
            //Load data
            viewModel.loadFavorites()
            viewModel.favoritesListData.observe(viewLifecycleOwner) {
                if (it.isEmpty) {
                    emptyLay.isVisible(true, favoriteList)
                    statusLay.disImg.setImageResource(R.drawable.box)
                    statusLay.disTxt.text = getString(R.string.emptyList)
                } else {
                    emptyLay.isVisible(false, favoriteList)
                    favoriteAdapter.setData(it.data!!)
                    favoriteList.setupRecyclerView(LinearLayoutManager(requireContext()), favoriteAdapter)

                    favoriteAdapter.setOnItemClickListener { food ->
                        val direction = FoodsListFragmentDirections.actionListToDetail(food.id)
                        findNavController().navigate(direction)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}