package com.ocse.androidbaselib.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.ocse.androidbaselib.R
import com.ocse.androidbaselib.databinding.FragmentHomeBinding
import com.ocse.baseandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View) {
        setMainTextView("")?.setOnBackPressed(object : BackPressed {
            override fun onBackPressed() {
                activity?.finish()
            }
        })
        view.tvHome.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_otherFragment)
        }
    }
}