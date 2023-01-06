package com.aristotele.foodappmvvm.ui.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.aristotele.foodappmvvm.R
import com.aristotele.foodappmvvm.databinding.FragmentFoodsListBinding
import com.aristotele.foodappmvvm.ui.list.adapters.CategoriesAdapter
import com.aristotele.foodappmvvm.ui.list.adapters.FoodsAdapter
import com.aristotele.foodappmvvm.utils.*
import com.aristotele.foodappmvvm.viewmodel.FoodsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * خوب این فرگمنت اصلی هست که صقحه اصلی ما هست
 * داخلش یه لایه هست  به اسم  FragmentFoodsListBinding که داخلش اینطوری که
 *
 * headerLay --> داخلش یه کتابخونه خاص عکس که بزرگ و کوچیک میشه headerImg + View طوسی رنگ
 * این بخش قسمت بالایی بود
 *
 * قسمت یعدی یه سرچ هست و یک اسپینر حروف که داخل یک ConstraintLayout هست
 * ConstraintLayout -> { Spinner -filterSpinner // EditText -searchEdt }
 *
 * بعدش یه NestedScrollView هست چون قرار توش 3 تا ریسایکلر ویو باشه
 * NestedScrollView -> homeContent {هست با 2 تا اسکرول و لودر داخلش یه LinearLayout}
 *
 * ConstraintLayout(homeCategoryLay) -> RecyclerView (categoryList)
 *                  -> ProgressBar (homeCategoryLoading)
 *
 * ConstraintLayout (homeFoodsLay) ->
 *                                  ConstraintLayout (homeFoodsContent) // توش یه لیبله تکس + ریسایکلر + لودر
 *                                                                      TextView (foodsTitle)
 *                                                                      RecyclerView (foodsList)
 *                                                                      -> ProgressBar (homeFoodsLoading)
 *
 *در پایان هم یک  ConstraintLayout هست که توش یه لایه دیگه اینکلود شده
 *status_lay2
 *
 */
@AndroidEntryPoint
class FoodsListFragment : Fragment() {

    //region تعریف ابزار و ماژول ها و کلاس های لازم

    //=========================================================
    //Binding تعریف بایندینگ این صفحه
    /**
     * این لایه از بالا یه عکس داره که کتابخونه خاصی داره که عکس بزرگ کوچیک میکنه
     * یه قسمت سرچ داره و یه اسپینر حروف انگلیسی
     * بعد یک اسکرول نستد که توش 2 تا اسکرول هست و 2 تا لودر
     * بعد ی لایه دیگه هم اضافه کرده که وقتی نت وصل نیست دیده میشه
     */
    private var _binding: FragmentFoodsListBinding? = null
    private val binding get() = _binding

    //--------------------------------
    //تزریق آدابتر ریسایکلر ویو افقی دسته بندی ها
    // ما چون برای ریسایکلر ویو به آدابتر احتیاج داریم باید اینجکت کنیم
    // با کلیک روی علامت بغل سمت چپ میبینید از کجا اینجکت شده
    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter

    //--------------------------------
    //تزریق آدابتر اسکرول عمودی
    //تزریق آدابتر ریسایکلر ویو افقی دسته بندی ها
    // ما چون برای ریسایکلر ویو به آدابتر احتیاج داریم باید اینجکت کنیم
    // با کلیک روی علامت بغل سمت چپ میبینید از کجا اینجکت شده
    @Inject
    lateinit var foodsAdapter: FoodsAdapter

    //--------------------------------
    //کنترل ایبنترنت با
    //این چی ؟ این میاد یه لایو دیتا بولین برمیگردونه
    // که میشه آبزرو کرد و لحظه ای چک کرد
    //که اگر فالس شد یعنی اینترنت قطع شده
    @Inject
    lateinit var connection: CheckConnection

    //--------------------------------
    //Other
    // اینم که ویو مدل داستان هست و تعریف ویو مدل ما هست
    private val viewModel: FoodsListViewModel by viewModels()
    //=========================================================

    //endregion

    //اینجا بایندینگ رو میدیم و در دیستروید هم خالی میکنیم
    // دقت کنید ویو ها رو اینجا تعریف نکنید و ... و برید تو onViewCreated تعریف کنید که مطمعن بشید لایوت ساخته شده در بایندینگ
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodsListBinding.inflate(layoutInflater)
        return binding!!.root
    }

    //اینم که آناستوپ فرگمنت هست و هر وقت صدا بشه میایم بایندینگ رو میبندیم و نال میکنیم
    override fun onStop() {
        super.onStop()
        _binding = null
    }


    //معمولا برای جلو گیری از خطا ها در این بخش مینویسیم چون اینجا دیگه ویو ها ساخته میشه و با خیال راحت میتونیم کار کنیم
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //InitViews این طبق معمول مینویسم که راحت بشیم هی تکرار نکنیم
        binding?.apply {


            //=========================================================
            //Random food
            //viewModel.loadFoodRandom()
            // اینجا میاد یه غذای رندم از سرور دریافت میکنه
            viewModel.randomFoodData.observe(viewLifecycleOwner) {
                it[0].let { meal ->
                    headerImg.load(meal.strMealThumb) {
                        crossfade(true)
                        crossfade(500)
                    }
                }
            }
            //=========================================================

            //=========================================================
            //Filters
            // اینگزینه میاد یه لیست از a تا z میسازه میده به یه پارامتر آبزرویبل به اسم viewModel.filtersListData
            // بعد سریع که عوض شد میاد میده به اسپینر که البته خودمون آدابترش رو انگولک کردیم قبلا
            viewModel.loadFilterList()

            viewModel.filtersListData.observe(viewLifecycleOwner) {
                // setupListWithAdapter(it  یه اکستنشن خودمون ساختیم برید روش توضیحات دار
                filterSpinner.setupListWithAdapter(it) { letter ->
                    viewModel.loadFoodsList(letter)
                }
            }
            //=========================================================






            //=========================================================
            //Category
            //viewModel.loadCategoriesList()
            // اینجا میایم کتگوری انواع غذا رو میگیریم  و میدیم به اسکرول افقی
            //اول میاد میبینه اگر در حالت لوده که لودینگ نشون میده
            //رفت تو جواب جواب رو نشون میده
            //ارور هم بده یه تست مسیج اراو نمایش میده

            viewModel.categoriesListData.observe(viewLifecycleOwner) {



                when (it.status) {

                   // خوب ببینید اگر لودینگ برسه لودینگ نمایش میده
                    MyResponse.Status.LOADING -> {
                        //فعال کردن چرخش یارو لودینگ
                        homeCategoryLoading.isVisible(true, categoryList)
                    }


                    //اگر ساکسس برسه یه مقدار دیتاهم باهاش رسیده که لیست غذاها کاتگوری هست که میگیریم استفاده میکنیم
                    MyResponse.Status.SUCCESS -> {

                        //اول چرخش لودینگ رو حذف میکنیم
                        homeCategoryLoading.isVisible(false, categoryList)

                        //داده ها رو میدیم به آدابتر ریسایکلر ویو که طراحی کردیم
                        categoriesAdapter.setData(it.data!!.categories)
                        categoryList.setupRecyclerView(
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            ), categoriesAdapter
                        )
                    }

                    //وقتی خطایی برسه
                    MyResponse.Status.ERROR -> {

                        // اول لودینگ رو حذف میکنیم
                        homeCategoryLoading.isVisible(false, categoryList)
                        //یه تست نمایش میدیم
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                    }



                }
            }

            //=========================================================
            //Category ClickListener
            // ست کردن کلیک لیسنر برای اسکرول افقی
            //وقتی روی یک گزینه کلیک میشه
            categoriesAdapter.setOnItemClickListener {
                //این میاد میره یه مقداری رو که درواقع اسم یه کتگوری هست میفرسته به ویو مدل تا بره درخواست بزنه
                viewModel.loadFoodByCategory(it.strCategory.toString())
            }

            //=========================================================
            //=========================================================
            //Foods
            // اینجا گفتیم توی اسکرول افقی پایینی بیا از غذاهای لیست A رو بگیر و نشون بده
            //ومتنظر میشیم که
            viewModel.loadFoodsList("A")


            //این یه لیست غذا هست که اینجا نظاره میکنیم
            // از 3 طریق در ویو مدل عوض میشه
            //اینجا هم جواب از  MyResponse میاد
            viewModel.foodsListData.observe(viewLifecycleOwner) {


                when (it.status) {


                    // این در حال لودینگه که میاد لودینگ رو نشون میده
                    MyResponse.Status.LOADING -> {
                        homeFoodsLoading.isVisible(true, foodsList)
                    }

                    //اینجا موفق آمیز بوده و داده ها ی رسیده رو میگیریم میدیم به ریسایکلر افقی
                    MyResponse.Status.SUCCESS -> {

                        //اول لودینگ رو خاموش میکنیم
                        homeFoodsLoading.isVisible(false, foodsList)


                        //حالا داده ها رو میدیم به ریسایکلر
                        if (it.data!!.meals != null) {


                            if (it.data.meals!!.isNotEmpty()) {
                                checkConnectionOrEmpty(false, PageState.NONE)
                                foodsAdapter.setData(it.data.meals)
                                foodsList.setupRecyclerView(
                                    LinearLayoutManager(
                                        requireContext(),
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    ), foodsAdapter
                                )
                            }


                        } else {
                            //اگر خطایی باشه از این فانکشن میگیریم نمایش بده مشکلی هست
                            checkConnectionOrEmpty(true, PageState.EMPTY)
                        }
                    }


                    //اینجا ارور میرسه
                    MyResponse.Status.ERROR -> {

                        //اول لودینگ رو میبندیم
                        homeFoodsLoading.isVisible(false, foodsList)

                        //تست نمایش میدیم
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }



                }
            }


            //اینم کلیک هست دیگه که ما رو میبره رو فرگمنت دیتیل
            foodsAdapter.setOnItemClickListener {
                val direction = FoodsListFragmentDirections.actionListToDetail(it.idMeal!!.toInt())
                findNavController().navigate(direction)
            }
            //=========================================================

            //=========================================================

            // سرچ اومده گفته زیر 2 هرف بود هیچ به محض بالا رفتن از 2 حرف حتما برو برای سرچ و این کلمه رو میفرسته اونطرف
            //Search
            searchEdt.addTextChangedListener {
                if (it.toString().length > 2) {
                    viewModel.loadFoodBySearch(it.toString())
                }
            }
            //=========================================================


            //=========================================================
            //Internet کنترل
            //ببین آقا جان این یه کلاسی که یه لایو دیتابرمیگرونه که بولین هست به محض تغیر کردن به ما خبر میده
            connection.observe(viewLifecycleOwner) {
                if (it) {
                    checkConnectionOrEmpty(false, PageState.NONE)
                } else {
                    checkConnectionOrEmpty(true, PageState.NETWORK)
                }
            }
            //=========================================================


        }
    }


    //region فانکشن کنترل اینترنت و داده های رسیده
    //================================================================================
    //================================================================================
    /**
     * این چی ؟ این چی چی ؟
     * این فانکشن 4 بار بالا استفاده شده
     * این یه فانکشنه که میاد 2 تا ورودی میگیره و براساس اون 2 ورودی یه ویو هایی رو تو صفحه اصلی نشون میده یا نشون نمیده
     * یه کلاس انم داریم 3 گزینه ای  که میگه شبکه خالی اومده یا رسیده یا یچی
     *و یک نمایش خطا رو با بولین بهش میدم
     *      * این چی ؟ این میاد میبینه اتصال اینترنت برقرار هست ؟
     * اگر برقراره آیا اطلاعات هم رسیده یا یا نه
     */
    //این رو استفاده میکنیم برای گفتن وضعیت اینترنت
    enum class PageState { EMPTY, NETWORK, NONE }

    private fun checkConnectionOrEmpty(isShownError: Boolean, state: PageState) {

        //اینو دیگه حفظ شدیم دیگه میاد راحت میکنه کار رو
        binding?.apply {

            if (isShownError) {

                // isVisible یک اکستنشنه که ما نوشتیم و یه ویو و یه بولین میفرستیم و اون رو محو میکنیم یا نمایش میدیم
                homeDisLay.isVisible(true, homeContent)

                // حالا میاد میبینه داده رسیده یا نه ؟
                //اگر خالی یه عکس نشون میده
                // اگر نتورک باشه یه عکس دیگه نشون میده
                when (state) {
                    PageState.EMPTY -> {
                        statusLay2.disImg.setImageResource(R.drawable.box)
                        statusLay2.disTxt.text = getString(R.string.emptyList)
                    }
                    PageState.NETWORK -> {
                        statusLay2.disImg.setImageResource(R.drawable.disconnect)
                        statusLay2.disTxt.text = getString(R.string.checkInternet)
                    }
                    else -> {}
                }

            } else {

                // isVisible یک اکستنشنه که ما نوشتیم و یه ویو و یه بولین میفرستیم و اون رو محو میکنیم یا نمایش میدیم
                homeDisLay.isVisible(false, homeContent)

            }
        }
    }
    //================================================================================
    //================================================================================
    //endregion

}