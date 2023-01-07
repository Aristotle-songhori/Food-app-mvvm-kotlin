package com.aristotele.foodappmvvm.ui.splash

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.aristotele.foodappmvvm.R
import com.aristotele.foodappmvvm.databinding.FragmentSplashBinding
import com.aristotele.foodappmvvm.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay




@Suppress("DEPRECATION")
@AndroidEntryPoint
class SplashFragment : Fragment() {


    /**
     * این لایه از بالا یه عکس داره که کتابخونه خاصی داره که عکس بزرگ کوچیک میکنه
     * یه قسمت سرچ داره و یه اسپینر حروف انگلیسی
     * بعد یک اسکرول نستد که توش 2 تا اسکرول هست و 2 تا لودر
     * بعد ی لایه دیگه هم اضافه کرده که وقتی نت وصل نیست دیده میشه
     */
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding

    //--------------------------------
    //Other
    // اینم که ویو مدل داستان هست و تعریف ویو مدل ما هست
    private val viewModel: SplashViewModel by viewModels()
    //=========================================================




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding!!.root
    }

    //اینم که آناستوپ فرگمنت هست و هر وقت صدا بشه میایم بایندینگ رو میبندیم و نال میکنیم
    override fun onStop() {
        super.onStop()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.startTimer()
        }

        viewModel.timerInt.observe(viewLifecycleOwner) {
            binding?.textView?.text = it.toString()
            binding?.progressBar?.progress=it
            if (it == 100) {
                findNavController().navigate(R.id.actionTofoodsListFragment)
            }

        }





        //Set delay
      //  lifecycle.coroutineScope.launchWhenCreated {

//for test
           // findNavController().navigate(R.id.actionTofoodsListFragment)

//            //Check user token
//            storeUserData.getUserToken().collect {
//                if (it.isEmpty()) {
//                    findNavController().navigate(R.id.action_splashFragment_to_registerFragment)
//                } else {
//                    findNavController().navigate(R.id.actionToHome)
//                }
       // }
    }

}