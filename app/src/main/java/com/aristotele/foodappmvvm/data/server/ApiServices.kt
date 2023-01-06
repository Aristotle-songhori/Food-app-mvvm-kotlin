package com.aristotele.foodappmvvm.data.server

import com.aristotele.foodappmvvm.data.model.ResponseCategoriesList
import com.aristotele.foodappmvvm.data.model.ResponseFoodsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * اما خط آخر که میاد روت های درخواستی رو مشخص میکنه برای سرور
 * ببین اینجا ته ته خطه که با flow و retrofit درست میشه و  میاد به ما داده ها رومیده
 * با وشهای مختلف گت و پست و  غیره ما داده ها رو ارسال یا دریافت میکنیم
 *
 * بین ما لینک اصلی رو در بخش دادیم
 * const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
 *const val NETWORK_TIMEOUT = 60L
 * حالا شما هرکدوم اینا رو تهش اضافه کنی میشه لینک اصلی و متدش هم بالاش هست که معمولا گت هست
 * چون انوتیشن @GET داره و برای retrofit 2 هست یه ضرب میره تو رتروفیت
 * در بخش دیپندنسی اینجکشن که با di هست و اسمش هست  NetworkModule
 *

 */
interface ApiServices {
    @GET("random.php") //https://www.themealdb.com/api/json/v1/1/random.php
    suspend fun foodRandom(): Response<ResponseFoodsList>

    @GET("categories.php") //https://www.themealdb.com/api/json/v1/1/categories.php
    suspend fun categoriesList(): Response<ResponseCategoriesList>

    @GET("search.php")
    suspend fun foodsList(@Query("f") letter: String): Response<ResponseFoodsList>

    @GET("search.php")
    suspend fun searchFood(@Query("s") letter: String): Response<ResponseFoodsList>

    @GET("filter.php")
    suspend fun foodsByCategory(@Query("c") letter: String): Response<ResponseFoodsList>

    @GET("lookup.php")
    suspend fun foodDetail(@Query("i") id: Int): Response<ResponseFoodsList>
}