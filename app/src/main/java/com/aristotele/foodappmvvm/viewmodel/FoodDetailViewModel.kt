package com.aristotele.foodappmvvm.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aristotele.foodappmvvm.data.database.FoodEntity
import com.aristotele.foodappmvvm.data.model.ResponseFoodsList
import com.aristotele.foodappmvvm.data.repository.FoodDetailRepository
import com.aristotele.foodappmvvm.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * اینجا چه خبره ؟ اینجا ویو مدل بخش دیتیل و دیدن جزئیات یک غذا هست
 * خوب به هیلت معرفی میکنیم این ویو مدل رو به عنوان ویو مدل
 *
 */


@HiltViewModel
class FoodDetailViewModel @Inject constructor(private val repository: FoodDetailRepository) : ViewModel() {


    //================================================================================
    /**
     * یه لیست تعریف میکنیم میوتیبل که بشه از تو اکتیوتی کنترلش کنیم
     * در واقع میره تو هاست و یه آیدی میفرسته و مشخصاتش رو میگیره میاره اینجاا
     */
    val foodDetailData = MutableLiveData<MyResponse<ResponseFoodsList>>()
    fun loadFoodDetail(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.foodDetail(id).collect { foodDetailData.postValue(it) }
    }
    //================================================================================


    /**
     * این سه تا فانکشن هم که فانکشن هایی هستن که
     */
    fun saveFood(entity: FoodEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveFood(entity)
    }

    fun deleteFood(entity: FoodEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFood(entity)
    }

    val isFavoriteData = MutableLiveData<Boolean>()
    fun existsFood(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.existsFood(id).collect { isFavoriteData.postValue(it) }
    }
}