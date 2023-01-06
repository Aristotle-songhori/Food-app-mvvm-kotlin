package com.aristotele.foodappmvvm.data.repository

import com.aristotele.foodappmvvm.data.database.FoodDao
import com.aristotele.foodappmvvm.data.database.FoodEntity
import com.aristotele.foodappmvvm.data.model.ResponseFoodsList
import com.aristotele.foodappmvvm.data.server.ApiServices
import com.aristotele.foodappmvvm.utils.MyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


/**
 * این جا رپزیتوری فرگمنت دیتیل است
 *این به 2 بخش دست رسی داره یکی هاست که میره به سرور یه آیدی میفرسته و داده ها رو میگیره
 *
 * و
 *
 *
 *
 *
 *
 */



class FoodDetailRepository @Inject constructor(private val api: ApiServices, private val dao: FoodDao) {




//این میره از سرور داده ها رو میگیره
//    و اگر 200 بود جواب رسیده و ..............
    suspend fun foodDetail(id: Int): Flow<MyResponse<ResponseFoodsList>> {

        return flow {
            //میره لودینگ رو فعال میکنه
            emit(MyResponse.loading())
            //کنترل میکنه اگر 200 باشه یعنی جواب درست رسیده و حله
            when (api.foodDetail(id).code()) {
                in 200..202 -> {
                    emit(MyResponse.success(api.foodDetail(id).body()))
                }
            }

        }.catch { emit(MyResponse.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }


    /**
     * اما این 3 تا فانکشن میاد و روی دیتابیس کار میکنه
     * ذخیره تو دیتابیس
     * پاک کردن از دیتابیس
     * وجود داشتن یک آیدی و غذا در دیتابیس
     */
    suspend fun saveFood(entity: FoodEntity) = dao.saveFood(entity)
    suspend fun deleteFood(entity: FoodEntity) = dao.deleteFood(entity)
    fun existsFood(id: Int) = dao.existsFood(id)
}