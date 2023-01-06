package com.aristotele.foodappmvvm.ui.list.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aristotele.foodappmvvm.data.model.ResponseFoodsList
import com.aristotele.foodappmvvm.databinding.ItemFoodsBinding
import javax.inject.Inject


/**
 * خوب این ادابتر ریسایکلر افقی غذاها پایین صفحه هست
 * ویو مخصوصش هم اسمش هست ItemFoodsBinding
 *
 * اینم آدابتر ریسایکر ویو هست و قسمت های خودشو داره
 */
class FoodsAdapter @Inject constructor() : RecyclerView.Adapter<FoodsAdapter.ViewHolder>() {

    // یه بایندینگ تعریف میکنیم که لایه رو بهش بدیم
    private lateinit var binding: ItemFoodsBinding

    //یه لیست خالی اینجا درست میکنیم و اینشیالایز میکنیم
    //این دقیقا همون روشی که ما میایم داده ها رو میدیم به این لیست رسیده
    //تعداد عضو هاش رو میبینیم و کارای روش رو انجام میدیم
    private var moviesList = emptyList<ResponseFoodsList.Meal>()



    //همیشه همینه و بایندینگ رو داریم اینجا
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemFoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }



    //اینجا یه ViewHolder داریم که همینجا اینر کلاس هست و ساختیم البته میشه بیرون هم ساخته میشد
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //getItem from PagingDataAdapter
        // یه فانکشنی توش نوشتیم که آیتم ها رو کنترل میکنه به اسم  fun bind(item: ResponseFoodsList.Meal)
        holder.bind(moviesList[position])
        //Not duplicate items
        //این متد ولی برای خود ریسایکلر ویو هست که فالس میکنیم
        holder.setIsRecyclable(false)
    }


//اینم که تعداد آیتم ها رو میده و مال خود ریسایکلر ویو هست
    override fun getItemCount() = moviesList.size

    //اینر کلاس ویو هولدر که میشه بیرونم نوشت هر طور راحت هستید
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ResponseFoodsList.Meal) {
            //داده ها رسیده و میریم بدیم به ویو
            binding.apply {


                itemFoodsImg.load(item.strMealThumb) {
                    crossfade(true)
                    crossfade(500)
                }


                itemFoodsTitle.text = item.strMeal


                //Category
                if (item.strCategory.isNullOrEmpty().not()) {
                    itemFoodsCategory.text = item.strCategory
                    itemFoodsCategory.visibility = View.VISIBLE
                } else {
                    itemFoodsCategory.visibility = View.GONE
                }



                //Area
                if (item.strArea.isNullOrEmpty().not()) {
                    itemFoodsArea.text = item.strArea
                    itemFoodsArea.visibility = View.VISIBLE
                } else {
                    itemFoodsArea.visibility = View.GONE
                }


                //Source
                if (item.strSource != null) {
                    itemFoodsCount.visibility = View.VISIBLE
                } else {
                    itemFoodsCount.visibility = View.GONE
                }

                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((ResponseFoodsList.Meal) -> Unit)? = null



    fun setOnItemClickListener(listener: (ResponseFoodsList.Meal) -> Unit) {
        onItemClickListener = listener
    }



    fun setData(data: List<ResponseFoodsList.Meal>) {
        val moviesDiffUtil = MoviesDiffUtils(moviesList, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        moviesList = data
        diffUtils.dispatchUpdatesTo(this)
    }



    class MoviesDiffUtils(private val oldItem: List<ResponseFoodsList.Meal>, private val newItem: List<ResponseFoodsList.Meal>) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }



        override fun getNewListSize(): Int {
            return newItem.size
        }



        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }



        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }



    }
}