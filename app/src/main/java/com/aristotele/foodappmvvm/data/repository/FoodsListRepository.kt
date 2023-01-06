package com.aristotele.foodappmvvm.data.repository


import com.aristotele.foodappmvvm.data.model.ResponseCategoriesList
import com.aristotele.foodappmvvm.data.model.ResponseFoodsList
import com.aristotele.foodappmvvm.data.server.ApiServices
import com.aristotele.foodappmvvm.utils.MyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject


/**
 * این رپوزیتوری یا مخن چی هست ؟
 * این رو ما ساختیم که یک سری دریافت از سرور دار
 * یه ورودی داره که api های مربوط به سرور داخلشه
 * و باApiServices شناخته شده
 * تعداد 5 تا فانکشن در این مخزن یا رپوزیتوری داریم که
 * همشون از flow استفاده شده
 *
 * دقت کنیم که 2 جور پاسخ برمیگردونه
 * اولی Response که مستقیم از رتروفیت میاد
 * دومی MyResponse هست که یک کلاسی که خودمون ساختیم در بخش utils که 3 تا فانکشن داره
 * fun <T> loading(): MyResponse<T>
 * fun <T> success(data: T?): MyResponse<T>
 * fun <T> error(error: String): MyResponse<T>
 *
 *
 */








class FoodsListRepository @Inject constructor(private val api: ApiServices) {

    suspend fun randomFood(): Flow<Response<ResponseFoodsList>> {
        return flow {
            emit(api.foodRandom())
        }.flowOn(Dispatchers.IO)
    }



    suspend fun categoriesList(): Flow<MyResponse<ResponseCategoriesList>> {


        return flow {




            //آماده لود کردن و درحال لود قرار دادن لودینگ
            emit(MyResponse.loading())


            //کنترل جواب رسیدهمیاد میگه وقتی این مقدار کد رسیده رو چک کن
            //code() => یه کدی برمیگردونه مثلا 202
            //Response
            when (api.categoriesList().code()) {

                //اگر کد بین این 2 تا بود یعنی عالی همه چی خوب جواب رسیده
                //بنابراین body جواب رو بنداز تو فانکشن خودمون بخش ساکسس که حالشو ببریم
                in 200..202 -> {
                    emit(MyResponse.success(api.categoriesList().body()))
                }
                422 -> {
                    emit(MyResponse.error("error 422"))
                }
                in 400..499 -> {
                    emit(MyResponse.error("error 400-449"))
                }
                in 500..599 -> {
                    emit(MyResponse.error("error 500-599"))
                }
            }



            //در صورتی که فلو به مشکل خورد هم این خطا رو نشون بده
        }.catch {
            emit(MyResponse.error(it.message.toString()))
        }.flowOn(Dispatchers.IO)



    }


    /**
     * اینجا یه استرینگ میاد و فقط جواب کد 202 کنترل میشه
     * که میتونست همش کنترل بشه ولی نشد که اگر چیزی نرسید تغییری نکن لیست داده های ما
     */
    suspend fun foodsList(letter: String): Flow<MyResponse<ResponseFoodsList>> {
        return flow {
            emit(MyResponse.loading())
            when (api.foodsList(letter).code()) {
                in 200..202 -> {
                    emit(MyResponse.success(api.foodsList(letter).body()))
                }
            }
        }.catch { emit(MyResponse.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }



    /**
     * اینجا یه استرینگ میاد و فقط جواب کد 202 کنترل میشه
     * که میتونست همش کنترل بشه ولی نشد که اگر چیزی نرسید تغییری نکن لیست داده های ما
     */

    suspend fun foodsBySearch(letter: String): Flow<MyResponse<ResponseFoodsList>> {
        return flow {
            emit(MyResponse.loading())
            when (api.searchFood(letter).code()) {
                in 200..202 -> {
                    emit(MyResponse.success(api.searchFood(letter).body()))
                }
            }
        }.catch { emit(MyResponse.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }


    /**
     * اینجا یه استرینگ میاد و فقط جواب کد 202 کنترل میشه
     * که میتونست همش کنترل بشه ولی نشد که اگر چیزی نرسید تغییری نکن لیست داده های ما
     */

    suspend fun foodsByCategory(letter: String): Flow<MyResponse<ResponseFoodsList>> {
        return flow {
            emit(MyResponse.loading())
            when (api.foodsByCategory(letter).code()) {
                in 200..202 -> {
                    emit(MyResponse.success(api.foodsByCategory(letter).body()))
                }
            }
        }.catch { emit(MyResponse.error(it.message.toString())) }
            .flowOn(Dispatchers.IO)
    }
}