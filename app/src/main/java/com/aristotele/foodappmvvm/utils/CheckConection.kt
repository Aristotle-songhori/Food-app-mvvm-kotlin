package com.aristotele.foodappmvvm.utils

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import javax.inject.Inject

/**
 * این چی ؟
 * این یه کلاس هست که میاد اتصال به اینترنت رو چک میکنه
 * دقت کنید که یک لایو دیتا برمیگردونه که بولین هست یعنی قابل آبزرو شدن خواهد بود
 * و این هر وقت عوض بشه میشه متوجه شد و آبزرو کرد
 * خوب اومدیم به هیلت اضافه کردیم که بتونیم هر جا خواستیم استفاده کنیم
 * و اگر تغیری انجام شد همه جا تغییر انجام بشه این مثال خوبی برای اینجکشن
 * برای دیدن اینکه چند جا و کجا استفاده شده کافی
 * روی علامت سمت چپ کلیک کنید ببینید کجا ها استفاده بشه
 */
class CheckConnection @Inject constructor(private val cm: ConnectivityManager) : LiveData<Boolean>() {

    private val networkCallback = object : ConnectivityManager.NetworkCallback(){

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }

    }

    override fun onActive() {
        super.onActive()
        val request = NetworkRequest.Builder()
        cm.registerNetworkCallback(request.build(), networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        cm.unregisterNetworkCallback(networkCallback)
    }
}