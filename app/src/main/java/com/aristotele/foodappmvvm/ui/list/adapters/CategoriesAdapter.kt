package com.aristotele.foodappmvvm.ui.list.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aristotele.foodappmvvm.R
import com.aristotele.foodappmvvm.data.model.ResponseCategoriesList
import com.aristotele.foodappmvvm.databinding.ItemCategoriesBinding
import javax.inject.Inject

/**
 * همینطور که میبینیم اومدیم این کلاس رو وصل کردیم به هیلت با نوتیشن اینجکت کانسترکتور
 * یعنی هر جا بخواهیم میتونیم تزریقش کنیم
 * اگر روی اون علامت بقل سمت چپ کلیک کنید میبینید کجاها استفاده شده
 *
 * خوب این آدابتر برای اسکرول هست
 * اسکرولی که میاد دسته بندی غذا ها رو نشون میده
 */
//
class CategoriesAdapter @Inject constructor() :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
//باندینگ و لایه رو تعریف کردیم
    private lateinit var binding: ItemCategoriesBinding

    //یه لیست خالی درست میکنیم اینجا
    private var moviesList = emptyList<ResponseCategoriesList.Category>()

    //اینم خودمون درست کردیم برای سلکت شدن آیتم ها
    private var selectedItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //getItem from PagingDataAdapter
        holder.bind(moviesList[position])
        //Not duplicate items
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = moviesList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ResponseCategoriesList.Category) {
            binding.apply {
                itemCategoriesImg.load(item.strCategoryThumb) {
                    crossfade(true)
                    crossfade(500)
                }
                itemCategoriesTxt.text = item.strCategory
                //Click
                root.setOnClickListener {
                    selectedItem = adapterPosition
                    notifyDataSetChanged()
                    onItemClickListener?.let {
                        it(item)
                    }
                }
                //Change color
                if (selectedItem == adapterPosition) {
                    root.setBackgroundResource(R.drawable.bg_rounded_selcted)
                } else {
                    root.setBackgroundResource(R.drawable.bg_rounded_white)
                }
            }
        }
    }

    private var onItemClickListener: ((ResponseCategoriesList.Category) -> Unit)? = null

    fun setOnItemClickListener(listener: (ResponseCategoriesList.Category) -> Unit) {
        onItemClickListener = listener
    }

    fun setData(data: List<ResponseCategoriesList.Category>) {
        val moviesDiffUtil = MoviesDiffUtils(moviesList, data)
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)
        moviesList = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class MoviesDiffUtils(private val oldItem: List<ResponseCategoriesList.Category>, private val newItem: List<ResponseCategoriesList.Category>) : DiffUtil.Callback() {
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