package com.aristotele.foodappmvvm.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aristotele.foodappmvvm.data.model.ResponseCategoriesList
import com.aristotele.foodappmvvm.data.model.ResponseFoodsList
import com.aristotele.foodappmvvm.data.repository.FoodsListRepository
import com.aristotele.foodappmvvm.utils.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * FoodsList  و اما ویو مدل صفحه فرگمنت اصلی به اسم
 * اولا که این رو دادیم به هیلت دقتتتتتتتتتتتتتتتتتتت با نوتیشن ویووووووووووو مدللللللل
 * @HiltViewModel
 *  و داخل سازنده کلاس هم یه اینجکت داریم که ریپزیتوری هست
 *  داخل دیپازیتوری یک سری فانکشن زیر رو داریم که این 5 فانکشن عبارتند از
 *  suspend fun randomFood(): Flow<Response<ResponseFoodsList>>
 *  suspend fun categoriesList(): Flow<MyResponse<ResponseCategoriesList>>
 *  suspend fun foodsList(letter: String): Flow<MyResponse<ResponseFoodsList>>
 *  suspend fun foodsBySearch(letter: String): Flow<MyResponse<ResponseFoodsList>>
 *  suspend fun foodsByCategory(letter: String): Flow<MyResponse<ResponseFoodsList>>
 *
 *      باید بدونیم اگر قراره اینجا از یک ریپزیتوری سرور یا دیتابیس استفاده بشه باید داخل سازنده تعریف بشه که اینجا هم تعریف شده
 *
 */
@HiltViewModel
class FoodsListViewModel @Inject constructor(private val repository: FoodsListRepository) : ViewModel() {




    // این چی ؟
    //هروقت این ویو مدل اینشیالایز بشه این بخش اجرا میشه
    init {
        //سریععععععععععع زود تند برو این 2 تا کار رو انجام بده
        //اول یه فود رندم لود کن که یک MutableLiveData هست و تو خود فرگمنت داره نگاه میشه
        // یه لیست برمیگردونه از  ResponseFoodsList.Meal که یه مدل هست و اطلاعات یه غذا رو برمیگردونه
        // بقیه توضیحات رو روی خود فانکشن بخونید
        loadFoodRandom()



        loadCategoriesList()
    }











    //region گرفتن یک غذای رندم از سرور
    /**
     * خوب این جا یک پارامتر MutableLiveData دارریم  که یه لیستی میگیره و میفرسته در فرگمن چون ویو میشه
     * امل چطوری پُر میشه ؟ با randomFoodData.postValue
     * اما دقت کنید از کجا پُر میشه ؟ از رپوزیتوری که میره از سرور میگیره
     * و چون اونجا saspend استفاده کردیم
     * اینجا باید از کviewModelScope.launch(Dispatchers.IO) استفاده کنیم
     *
     * پس میره سریع به repository و فانکشن randomFood() رو صدا میکنه
     * و حتما با collect باید صدا بشه چون یه مقدار flow هست  جوابش
     *
     * بعد میاد چک میکنه اگر جواب داشت میاد با postValue مقدار داخل randomFoodData رو عوض میکنه و چون لایو دیتا هست اونطرف سریع عوض میشه
     *
     */
    val randomFoodData = MutableLiveData<List<ResponseFoodsList.Meal>>()
    private fun loadFoodRandom() = viewModelScope.launch(Dispatchers.IO) {
        repository.randomFood().collect {
            randomFoodData.postValue(it.body()?.meals!!)
        }
    }

    //endregion




    ////region ساخت لیست حروف داخل اسپینر فیلتر براساس حروف
    /**
     * خوب اینجا میایم ساده یه لیست میسازیم میدیم به filtersListData چون لایو دیتا هست اونطرف  آبزرو میشه
     * و میاد سریع تغییر میده و میدتش به اسپینر
     */
    val filtersListData = MutableLiveData<MutableList<Char>>()
    fun loadFilterList() = viewModelScope.launch(Dispatchers.IO) {
        val letters = listOf('A'..'Z').flatten().toMutableList()
        filtersListData.postValue(letters)
    }

    //endregion


    //region گرفتن لیست کتگوری ها از سرور و دادن به categoriesListData
    /**
     * اینجا امل چه میشه ؟ مثل گزینه گرفتن یه غذای رندم
     * این میاد لیست غذاهارو میگیره کتگوری مختلف
     *
     */

    val categoriesListData = MutableLiveData<MyResponse<ResponseCategoriesList>>()
    private fun loadCategoriesList() = viewModelScope.launch(Dispatchers.IO) {
        repository.categoriesList().collect { categoriesListData.postValue(it) }
    }
    //endregion




    // یه لیست میوتیبل که لیستی از غذاها هست و هر سه تابع پایینی اون رو تغییر میدن
    // این 3 جا استفاده میشه هم در لیست / هم در سرچ / هم در کتگوری
    val foodsListData = MutableLiveData<MyResponse<ResponseFoodsList>>()


    //گرفتن
    fun loadFoodsList(letter: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.foodsList(letter).collect { foodsListData.postValue(it) }
    }

    fun loadFoodBySearch(letter: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.foodsBySearch(letter).collect { foodsListData.postValue(it) }
    }

    /**
     * این میاد اسم یه کتگوری رو میگیره و میفرسته برای سرچ
     * که وقتی روی لیست کتگوری ها کلیک میکنیم تو خود فرگمنت به اینجا میایم
     */
    fun loadFoodByCategory(letter: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.foodsByCategory(letter).collect { foodsListData.postValue(it) }
    }
}