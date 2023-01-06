package com.aristotele.foodappmvvm.utils


import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.aristotele.foodappmvvm.R


/**
 * اینجا کجاسسسسسس؟؟
 * اینجا بخش اکستنشن ها هست یعنی ما میایم یک سری فانکشن مینویسیم که به درد بخوره همه جا
 * و عمومی
 * مثلا یه ویو میگیره اون ویو رو حذف میکنه یا نمایش میده
 * یا میاد اسپینر ها رو همه رو شکل هم میکنه
 * یا ....
 *
 */


/**
 * این اکستنشن چی کار میکنه
 * میاد یه ادابتر اختصاصی وبرای اسپینر میسازه
 * با لایه ایکه ما طراحی کردیم و میده به اسپینر
 * چه قدر تو بیسیک به این احتیاج داشتیم و نمیشد و اینجا به سادگی شده
 * و بعد اون لایه رو دادیم به اسپینر به جای آدابتر پیش فرضش
 * مثلا پرچم بفل نوشته ها یا آیکن یا ...
 * خیلی کارها میشه کرد خلاصه
 */
fun Spinner.setupListWithAdapter(list: MutableList<out Any>, callback: (String) -> Unit) {
    val adapter = ArrayAdapter(context, R.layout.item_spinner, list)
    adapter.setDropDownViewResource(R.layout.item_spinner_list)
    this.adapter = adapter
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            callback(list[p2].toString())
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }

    }
}


/**
 * این چی ؟ این یه اکستنشنه
 * خوب اکستنشن چی کار میکنه ؟
 * یه ویو میگیره و غیر فعالش میکنه همین
 */
fun View.isVisible(isShowLoading: Boolean, container: View) {
    if (isShowLoading) {
        this.visibility = View.VISIBLE
        container.visibility = View.GONE
    } else {
        this.visibility = View.GONE
        container.visibility = View.VISIBLE
    }
}

fun RecyclerView.setupRecyclerView(layoutManager: RecyclerView.LayoutManager, adapter: RecyclerView.Adapter<*>) {
    this.layoutManager = layoutManager
    this.setHasFixedSize(true)
    this.isNestedScrollingEnabled = false
    this.adapter = adapter
}