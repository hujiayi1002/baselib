package com.ocse.baseandroid.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocse.baseandroid.R
import com.ocse.baseandroid.view.LoadingView

/**
 * BaseFragment，处理ViewModelProvider的初始化
 * */
open abstract class BaseFragment<V : ViewDataBinding>(getBindView: Int) : Fragment() {
    private var viewModelProvider: ViewModelProvider? = null
    private lateinit var loadingView: LoadingView
    private var hash: Int = 0
    private var lastClickTime: Long = 0
    private var spaceTime: Long = 2000
    lateinit var bindingUtil: V
    private lateinit var relBack: RelativeLayout
    private lateinit var tvTitle: TextView
    private lateinit var tvRight: TextView
    private lateinit var imgRight: ImageView
    private lateinit var toolbar: Toolbar
    private val getBindView = getBindView

    abstract fun onViewCreated()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingUtil =
            DataBindingUtil.inflate(inflater, getBindView, container, false)
        return bindingUtil.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelProvider = getViewModelProvider()
        loadingView = LoadingView(activity!!)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitleBar("")
        onViewCreated()
    }

    private fun initTitleBar(title: String) {
        toolbar = bindingUtil.root.findViewById<Toolbar>(R.id.toolbar) ?: return
        toolbar.setContentInsetsRelative(0, 0)
        relBack = toolbar.findViewById(R.id.relBack)
        tvTitle = toolbar.findViewById(R.id.tvTitle)
        tvRight = toolbar.findViewById(R.id.tvRight)
        imgRight = toolbar.findViewById(R.id.imgRight)
        relBack.setOnClickListener { activity?.finish() }
        TitleBuilder(title).setRightTextGone().setRightImgGone().setLeftBackGone()

    }

    fun setMainTextView(title: String): TitleBuilder {
        val titleBuilder = TitleBuilder(title)
        titleBuilder.setTitle()
        return titleBuilder
    }


    /**
     * 创建ViewModel对象
     * @param clazz
     * @return
     */
    open fun <T : ViewModel> get(clazz: Class<T>): T {
        return viewModelProvider!![clazz]
    }

    /**
     * 初始化ViewModelProvider对象
     * @return
     */
    private fun getViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this)
    }

    infix fun View.singleClick(clickAction: () -> Unit) {
        this.setOnClickListener {
            if (this.hashCode() != hash) {
                hash = this.hashCode()
                lastClickTime = System.currentTimeMillis()
                clickAction()
            } else {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime > spaceTime) {
                    lastClickTime = System.currentTimeMillis()
                    clickAction()
                }
            }
        }
    }

    open fun showLoading() {
        if (!loadingView.isShowing) {
            loadingView.show()
        }

    }
    open fun loge(message: String) {
        var TAG = "Tag:${activity?.localClassName}"
        Log.e(TAG, "hu--$message")
    }
    open fun loadingDismiss() {
        loadingView.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelProvider = null
    }

    inner class TitleBuilder(title: String) {
        private val myTitle = title

        open fun setTitle(): TitleBuilder {
            tvTitle.text = myTitle
            return this
        }

        open fun setLeftBackGone(): TitleBuilder {
            relBack.visibility = View.GONE
            return this
        }

        open fun setLeftBackVisible(): TitleBuilder {
            relBack.visibility = View.VISIBLE
            return this
        }

        open fun setRightTextGone(): TitleBuilder {
            tvRight.visibility = View.GONE
            return this
        }

        open fun setRightText(text: String): TitleBuilder {
            tvRight.visibility = View.VISIBLE
            tvRight.text = text
            return this
        }

        open fun setRightImgGone(): TitleBuilder {
            imgRight.visibility = View.GONE
            return this
        }

        open fun setBackgroundColor(color: Int): TitleBuilder {
            if (::toolbar.isInitialized) {
                toolbar.setBackgroundColor(color)
            }
            return this
        }

        open fun setRightImg(resource: Int): TitleBuilder {
            imgRight.visibility = View.VISIBLE
            imgRight.setImageResource(resource)
            return this
        }


    }
}