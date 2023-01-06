package com.aristotele.foodappmvvm.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aristotele.foodappmvvm.R
import com.aristotele.foodappmvvm.data.database.FoodEntity
import com.aristotele.foodappmvvm.ui.detail.player.PlayerActivity
import com.aristotele.foodappmvvm.ui.list.FoodsListFragment
import com.aristotele.foodappmvvm.utils.*
import com.aristotele.foodappmvvm.viewmodel.FoodDetailViewModel
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.aristotele.foodappmvvm.databinding.FragmentFoodDetailBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class FoodDetailFragment : Fragment() {
    //Binding
    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding
    //اینم ویو مدلمون هست
    private val viewModel: FoodDetailViewModel by viewModels()

    //اتصال به اینترنت رو چک میکنه اگر تغییر کنه متوجه میشه
    @Inject
    lateinit var connection: CheckConnection

    //دیتابیس رو اضافه کردیم
    @Inject
    lateinit var entity: FoodEntity

    //این آرگومنتی که میرسه به این ویومدل
    private val args: FoodDetailFragmentArgs by navArgs()

    private var foodID = 0

    private var isFavorite = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFoodDetailBinding.inflate(layoutInflater)
        return binding!!.root
    }


    override fun onStop() {
        super.onStop()
        _binding = null
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //InitViews
        binding?.apply {


            //Get data از آرگومنت میایم و آیدی رو میگیریم
            foodID = args.foodId



            //InitView
            binding?.apply {


                //backدکمه بک بالای صفحه و ست کردن کلیک برای برگشت نویگیشن
                detailBack.setOnClickListener { findNavController().navigateUp() }


                //Call api میاد آیدی رو میفرسته تا بره از هاست داده ها رو بگیره و بعد با لایو دیتا اگر عوض شد کنترل کند
                viewModel.loadFoodDetail(foodID)

                //اینجا میاد کنترل میکنه اگر تغیر کرد
                viewModel.foodDetailData.observe(viewLifecycleOwner) {

                   // اگر استاتوس هر کدوم از این گزینه ها هست این کار ها رو انجام بده
                    when (it.status) {


                        //اگر لودینگه این کار
                        MyResponse.Status.LOADING -> {
                            detailLoading.isVisible(true, detailContentLay)
                        }


                        //اگر ساکسس هست بیا داده ها رو نمایش بده
                        MyResponse.Status.SUCCESS -> {
                            detailLoading.isVisible(false, detailContentLay)
                            //Set data
                            it.data?.meals?.get(0)?.let { itMeal ->
                                //Entity
                                entity.id = itMeal.idMeal!!.toInt()
                                entity.title = itMeal.strMeal.toString()
                                entity.img = itMeal.strMealThumb.toString()
                                //Set data
                                foodCoverImg.load(itMeal.strMealThumb) {
                                    crossfade(true)
                                    crossfade(500)
                                }
                                foodCategoryTxt.text = itMeal.strCategory
                                foodAreaTxt.text = itMeal.strArea
                                foodTitleTxt.text = itMeal.strMeal
                                foodDescTxt.text = itMeal.strInstructions
                                //Play
                                if (itMeal.strYoutube != null) {
                                    foodPlayImg.visibility = View.VISIBLE
                                    foodPlayImg.setOnClickListener {
                                        val videoId = itMeal.strYoutube.split("=")[1]
                                        Intent(requireContext(), PlayerActivity::class.java).also {
                                            it.putExtra(VIDEO_ID, videoId)
                                            startActivity(it)
                                        }
                                    }
                                } else {
                                    foodPlayImg.visibility = View.GONE
                                }
                                //Source
                                if (itMeal.strSource != null) {
                                    foodSourceImg.visibility = View.VISIBLE
                                    foodSourceImg.setOnClickListener {
                                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(itMeal.strSource)))
                                    }
                                } else {
                                    foodSourceImg.visibility = View.GONE
                                }
                            }
                            //Json Array
                            val jsonData = JSONObject(Gson().toJson(it.data))
                            val meals = jsonData.getJSONArray("meals")
                            val meal = meals.getJSONObject(0)
                            //Ingredient
                            for (i in 1..15) {
                                val ingredient = meal.getString("strIngredient$i")
                                if (ingredient.isNullOrEmpty().not()) {
                                    ingredientsTxt.append("$ingredient\n")
                                }
                            }
                            //Measure
                            for (i in 1..15) {
                                val measure = meal.getString("strMeasure$i")
                                if (measure.isNullOrEmpty().not()) {
                                    measureTxt.append("$measure\n")
                                }
                            }
                        }



                        // اینم وقتی خطا میرسه
                        MyResponse.Status.ERROR -> {
                            detailLoading.isVisible(false, detailContentLay)
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }



                    }
                }








                //Favorite
                // این میاد میبینه اگر این آیدی خاص در دیتابیس هست و و ثبت شده یعنی باید در علاقه مندی باشه
                //
                viewModel.existsFood(foodID)
                viewModel.isFavoriteData.observe(viewLifecycleOwner) {
                    isFavorite = it
                    if (it) {
                        detailFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.tartOrange))
                    } else {
                        detailFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
                    }
                }





                //Save / Delete
                detailFav.setOnClickListener {
                    if (isFavorite) {
                        viewModel.deleteFood(entity)
                    } else
                        viewModel.saveFood(entity)
                }




            }




            //Internet
            //اینم که لحظه عوض شدن اینترنت رو چک میکنه
            connection.observe(viewLifecycleOwner) {
                if (it) {
                    checkConnectionOrEmpty(false, FoodsListFragment.PageState.NONE)
                } else {
                    checkConnectionOrEmpty(true, FoodsListFragment.PageState.NETWORK)
                }
            }




        }
    }


    //برای کنترل اینترنت ساخته شده توضیحات در بخش
    //فرگمنت لیست هست
    private fun checkConnectionOrEmpty(isShownError: Boolean, state: FoodsListFragment.PageState) {
        binding?.apply {
            if (isShownError) {
                homeDisLay.isVisible(true, detailContentLay)
                when (state) {
                    FoodsListFragment.PageState.EMPTY -> {
                        statusLay.disImg.setImageResource(R.drawable.box)
                        statusLay.disTxt.text = getString(R.string.emptyList)
                    }
                    FoodsListFragment.PageState.NETWORK -> {
                        statusLay.disImg.setImageResource(R.drawable.disconnect)
                        statusLay.disTxt.text = getString(R.string.checkInternet)
                    }
                    else -> {}
                }
            } else {
                homeDisLay.isVisible(false, detailContentLay)
            }
        }
    }
}