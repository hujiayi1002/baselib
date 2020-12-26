package com.ocse.baseandroid.view.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.ocse.baseandroid.R
import com.ocse.baseandroid.utils.DownLoadFileUtils
import com.ocse.baseandroid.utils.PermissionUtils
import com.tencent.smtt.sdk.TbsReaderView
import kotlinx.android.synthetic.main.activity_word_view_acivity.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import okhttp3.*
import java.io.File

const val FILENAME = "fileName"
const val URL = "url"

class WordViewActivity : AppCompatActivity(), TbsReaderView.ReaderCallback {
    private var url = ""
    private var fileName = ""
    private lateinit var loadingView: LoadingView
    private var mTbsReaderView: TbsReaderView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_view_acivity)
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init()
        intent.getStringExtra(FILENAME)?.let { fileName = it }
        intent.getStringExtra(URL)?.let { url = it;downLoadFile() }
        mTbsReaderView = TbsReaderView(this@WordViewActivity, this)
        tvTitle.text = "$fileName"
        relBack.setOnClickListener { finish() }
        getPermission()
        initView()

    }

    private fun initView() {
        loadingView = LoadingView(this)
        loadingView.setCanceledOnTouchOutside(true)
        if (!loadingView.isShowing) {
            loadingView.show()
        }
        wordView.addView(
            mTbsReaderView,
            RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )


    }

    private fun downLoadFile() {
        DownLoadFileUtils.download(url, fileName, object : DownLoadFileUtils.OnDownloadListener {
            override fun onDownloadSuccess(file: File) {
                //下载完成
                val message = Message()
                message.what = 6
                message.obj = file
                mHandler.sendMessage(message)
            }

            override fun onDownloading(progress: Int) {

            }

            override fun onDownloadFailed() {
            }

        })
    }

    @SuppressLint("CheckResult")
    private fun getPermission() {
        PermissionUtils.addPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }


    var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                6 -> {
                    loadingView.dismiss()
                    displayFile(msg.obj as File)
                }
            }
            super.handleMessage(msg)
        }
    }


    private fun displayFile(file: File) {
        val bundle = Bundle()
        val path = file.absolutePath
        val name = path.substring(path.lastIndexOf(".") + 1)
        val tempPath = path.substring(0, path.lastIndexOf("/")) + "/temp." + name
        val renameFile = File(tempPath)
        if (renameFile.exists()) {
            renameFile.deleteOnExit()
        }
        file.renameTo(renameFile)
        val o = renameFile.exists()
        val l = renameFile.length()
        bundle.putString("filePath", renameFile.path)
        bundle.putString("tempPath", filesDir.path)

        val result =
            mTbsReaderView!!.preOpen(parseFormat(renameFile.name), false)
        if (result) {
            mTbsReaderView!!.openFile(bundle)
        } else {
            tvNoType.visibility = View.VISIBLE
//            QbSdk.openFileReader(this,renameFile.path,null
//            ) { p0 -> Log.e("TAG", "onReceiveValue: $p0" ) }
        }
    }

    private fun parseFormat(fileName: String): String? {
        return fileName.substring(fileName.lastIndexOf(".") + 1)
    }

    override fun onCallBackAction(p0: Int?, p1: Any?, p2: Any?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        mTbsReaderView!!.onStop()
    }
}