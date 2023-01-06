package com.aristotele.foodappmvvm.utils


/**
 * یا قمر بنی هاشممممممممممممممممم
 * این چی حالا
 * این یه کلاسی هست که یه T میگیره
 * یعنی چی ؟ یعنی یه تابع یا هرچیزی  میتونه جنس این کلاس باشه
 * حالا
 *داخلش 3 تا تابع داریم که میاد 3 تا کار و 3 تا جواب برمیگردونه
 * یه لودینگ داره مینویسه در حال لود
 * یه ساکسی داره که مطمعنا موفق بوده و یه داده ای هم برمیگردونه
 * یه ارور داره که اونم میاد ارور رو برمیگردونه
 */



class MyResponse<out T>(val status: Status, val data: T? = null, val message: String? = null) {

    enum class Status { LOADING, SUCCESS, ERROR }

    companion object {
        fun <T> loading(): MyResponse<T> {
            return MyResponse(Status.LOADING)
        }
        fun <T> success(data: T?): MyResponse<T> {
            return MyResponse(Status.SUCCESS, data)
        }
        fun <T> error(error: String): MyResponse<T> {
            return MyResponse(Status.ERROR, message = error)
        }
    }
}