package com.aristotele.foodappmvvm.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aristotele.foodappmvvm.R
import com.aristotele.foodappmvvm.databinding.ActivityFoodsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoodsActivity : AppCompatActivity() {


    //================================================================
    //================================================================
    //تعریف ابزار لازم برای این اکتیوتی
    //1-لایه رو با بایندینگ معرفی میکنیم
    //2-نوهاست برای پایین صفحه
    //----------------------------------------------------------------
    //Binding به این روش تعریف میشه که کبسوله بشه و نال هم بگیره راحت تره
    //دقت کنید در ساب دیستروید اومدیم نال کردیم
    private var _binding: ActivityFoodsBinding? = null
    private val binding get() = _binding!!

    //----------------------------------------------------------------
    //Other یه نوهاست هم تعریف میکنیم و پایین تر اینشیالز میکنیم به نوهاست خودمون در لایه
    private lateinit var navHost: NavHostFragment
    //================================================================
    //================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //----------------------------------------------------------------
        // تعریف لایه بایندینگ و ست کردن آن در اول کار
        _binding = ActivityFoodsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //----------------------------------------------------------------
        //----------------------------------------------------------------
        //Nav controller اینشیالایز کردن نوهاستی که اون بالا تعریف کرده بودیم
        navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        //----------------------------------------------------------------
        //Bottom nav تعریف دکمه های نوهاست که باید حتما منو تعریف شده باشد
        binding.bottomNav.setupWithNavController(navHost.navController)
        //----------------------------------------------------------------
        //روش خاموش کردن نوهاست برای اسپلش و برای هر صفحه دیگه ای که لازم دیده نشه
        //Hide bottom nav میگیم وقتی میری توی دیتیل این بخش رو هیدن کن تا دیده نشه و زیبا تر باشه
        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.splashFragment || destination.id == R.id.foodDetailFragment) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }


    //گفتن مثل بک عمل مبکنه برای فرگمنتها
    override fun onNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }

    // اینم تکلیفش معلومه دیگه اکتیوتی دیستروید میشه
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}