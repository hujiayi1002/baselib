package com.ocse.baseandroid.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ocse.baseandroid.R
import kotlinx.android.synthetic.main.pop_choose.view.*

open class ChooseTakeBottomSheetDialog(context: Context) : BottomSheetDialogFragment() {
    private lateinit var choosePop: ChooseTake
    private lateinit var dataBind: ViewDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val dataBind: PopChooseBinding =
//            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.pop_choose, null, false)
//        val parms = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.MATCH_PARENT
//        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBind =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.pop_choose, null, false)
        val parms = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        return dataBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvChooseTake=dataBind.root.tv_choose_take.setOnClickListener {
              choosePop.take()
          }
          val tvChooseAlbum=dataBind.root.tv_choose_album.setOnClickListener {
              choosePop.album()
          }
          val tvChooseCancle=dataBind.root.tv_choose_cancle.setOnClickListener { choosePop.dismiss()  }
        dataBind.root.tv_choose_take.setOnClickListener {  }
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////          val dataBind: PopChooseBinding =
////              DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.pop_choose, null, false)
////          setContentView(dataBind.root)
////          dataBind.tvChooseTake.setOnClickListener { choosePop.take() }
////          dataBind.tvChooseAlbum.setOnClickListener { choosePop.album() }
////          dataBind.tvChooseCancle.setOnClickListener { choosePop.dismiss() }
////        window?.setLayout(
////            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//          val view=LayoutInflater.from(context).inflate(R.layout.pop_choose, null)
//          setContentView(view)
//          val tvChooseTake=view.tv_choose_take.setOnClickListener {
//              choosePop.take()
//          }
//          val tvChooseAlbum=view.tv_choose_album.setOnClickListener {
//              choosePop.album()
//          }
//          val tvChooseCancle=view.tv_choose_cancle.setOnClickListener { choosePop.dismiss()  }
//
//      }

    open fun setTakePop(pop: ChooseTake) {
        this.choosePop = pop
    }

    interface ChooseTake {
        fun take()
        fun album()
        fun dismiss()
    }
}