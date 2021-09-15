package com.xj.anchortask.asyncInflate.page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xj.anchortask.R
import com.xj.anchortask.asyncInflate.AsyncInflateManager
import com.xj.anchortask.asyncInflate.LaunchInflateKey.LAUNCH_FRAGMENT_MAIN

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "AsyncFragment"


/**
 * A simple [Fragment] subclass.
 * Use the [AsyncFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AsyncFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val startTime = System.currentTimeMillis()
        val homeFragmentOpen = AsyncUtils.isHomeFragmentOpen()
        val inflatedView: View

        val isOpen = AsyncUtils.isHomeFragmentOpen()
        if (isOpen){
            AsyncUtils.asyncInflate(context)
        }

//        inflatedView = AsyncInflateManager.instance.getInflatedView(
//            context,
//            R.layout.fragment_asny,
//            container,
//            LAUNCH_FRAGMENT_MAIN,
//            inflater
//        )
//
//        Log.i(
//            TAG,
//            "onCreateView: homeFragmentOpen is $homeFragmentOpen, timeInstance is ${System.currentTimeMillis() - startTime}, ${inflatedView.context}"
//        )
//        return inflatedView
        return inflater.inflate(R.layout.fragment_asny, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AsnyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AsyncFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}