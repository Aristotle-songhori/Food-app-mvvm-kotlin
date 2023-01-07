package com.aristotele.foodappmvvm.viewmodel

import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor () : ViewModel() {

    val timerInt = MutableLiveData<Int>()



    private fun ShowloadTimer() = viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
        }


    private val progressBar: ProgressBar? = null
    private var progressStatus = 0
    private val textView: TextView? = null
    private var handler = Handler()



     suspend fun startTimer() = viewModelScope.launch(Dispatchers.Main) {
        //Log.i("###", "viewmodel fun xxxx() =>$progressStatus")
        while (progressStatus < 100) {
            progressStatus += 2
            timerInt.value = progressStatus

            delay(50)
        }

    }







        init {

            //سریععععععععععع زود تند برو این 2 تا کار رو انجام بده
            //اول یه فود رندم لود کن که یک MutableLiveData هست و تو خود فرگمنت داره نگاه میشه
            // یه لیست برمیگردونه از  ResponseFoodsList.Meal که یه مدل هست و اطلاعات یه غذا رو برمیگردونه
            // بقیه توضیحات رو روی خود فانکشن بخونید
            Log.i("###", "viewmodel init ----------------->>>>")

        }
    }