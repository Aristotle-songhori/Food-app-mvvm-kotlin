package com.aristotele.foodappmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aristotele.foodappmvvm.data.database.FoodEntity
import com.aristotele.foodappmvvm.data.repository.FoodsFavoriteRepository
import com.aristotele.foodappmvvm.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoodsFavoriteViewModel @Inject constructor(private val repository: FoodsFavoriteRepository) : ViewModel() {

    val favoritesListData = MutableLiveData<DataStatus<List<FoodEntity>>>()
    fun loadFavorites() = viewModelScope.launch(Dispatchers.IO) {
        repository.foodsList().collect {
            favoritesListData.postValue(DataStatus.success(it, it.isEmpty()))
        }
    }
}