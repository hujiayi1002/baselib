package com.ocse.baseandroid.utils

import android.os.Environment
import java.io.File
import java.math.BigDecimal

class CleanUtil private constructor() {
    companion object {
        /**
         * @return
         * @throws Exception 获取当前缓存
         */
        @get:Throws(Exception::class)
        val totalCacheSize: String
            get() {
                var cacheSize =
                    getFolderSize(ObtainApplication.getApp()!!.cacheDir)
                if (Environment.getExternalStorageState() ==
                    Environment.MEDIA_MOUNTED
                ) {
                    cacheSize += getFolderSize(
                        ObtainApplication.getApp()!!.externalCacheDir
                    )
                }
                return getFormatSize(cacheSize.toDouble())
            }

        // 获取文件
        // Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/
        // 目录，一般放一些长时间保存的数据
        // Context.getExternalCacheDir() -->
        // SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
        @Throws(Exception::class)
        fun getFolderSize(file: File?): Long {
            var size: Long = 0
            try {
                val fileList = file!!.listFiles()
                for (i in fileList.indices) {
                    // 如果下面还有文件
                    size = if (fileList[i].isDirectory) {
                        size + getFolderSize(fileList[i])
                    } else {
                        size + fileList[i].length()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return size
        }

        /**
         * 格式化单位
         *
         * @param size
         * @return
         */
        fun getFormatSize(size: Double): String {
            val kiloByte = size / 1024
            if (kiloByte < 1) {
                return size.toString() + "Byte"
            }
            val megaByte = kiloByte / 1024
            if (megaByte < 1) {
                val result1 =
                    BigDecimal(java.lang.Double.toString(kiloByte))
                return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB"
            }
            val gigaByte = megaByte / 1024
            if (gigaByte < 1) {
                val result2 =
                    BigDecimal(java.lang.Double.toString(megaByte))
                return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB"
            }
            val teraBytes = gigaByte / 1024
            if (teraBytes < 1) {
                val result3 =
                    BigDecimal(java.lang.Double.toString(gigaByte))
                return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB"
            }
            val result4 = BigDecimal(teraBytes)
            return (result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                    + "TB")
        }

        /**
         * 清除内部缓存
         *
         * /data/data/com.xxx.xxx/cache
         *
         * @return `true`: 清除成功<br></br>`false`: 清除失败
         */
        fun cleanInternalCache(): Boolean {
            return FileUtil.instance
                .delAllFile(ObtainApplication.getApp()!!.cacheDir.path)
        }

        /**
         * 清除内部文件
         *
         * /data/data/com.xxx.xxx/files
         *
         * @return `true`: 清除成功<br></br>`false`: 清除失败
         */
        fun cleanInternalFiles(): Boolean {
            return FileUtil.instance
                .deleteFile(ObtainApplication.getApp()!!.filesDir.path)
        }

        /**
         * 清除内部数据库
         *
         * /data/data/com.xxx.xxx/databases
         *
         * @return `true`: 清除成功<br></br>`false`: 清除失败
         */
        fun cleanInternalDbs(): Boolean {
            return FileUtil.instance.deleteFile(
                ObtainApplication.getApp()!!.filesDir
                    .parent + File.separator + "databases"
            )
        }

        /**
         * 根据名称清除数据库
         *
         * /data/data/com.xxx.xxx/databases/dbName
         *
         * @param dbName 数据库名称
         * @return `true`: 清除成功<br></br>`false`: 清除失败
         */
        fun cleanInternalDbByName(dbName: String?): Boolean {
            return ObtainApplication.getApp()!!.deleteDatabase(dbName)
        }

        /**
         * 清除内部 SP
         *
         * /data/data/com.xxx.xxx/shared_prefs
         *
         * @return `true`: 清除成功<br></br>`false`: 清除失败
         */
        fun cleanInternalSP(): Boolean {
            return FileUtil.instance.deleteFile(
                ObtainApplication.getApp()!!.filesDir
                    .parent + File.separator + "shared_prefs"
            )
        }

        /**
         * 清除外部缓存
         *
         * /storage/emulated/0/android/data/com.xxx.xxx/cache
         *
         * @return `true`: 清除成功<br></br>`false`: 清除失败
         */
        fun cleanExternalCache(): Boolean {
            return Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() && FileUtil.instance
                .deleteFile(ObtainApplication.getApp()!!.externalCacheDir!!.path)
        }
    }

    init {
        throw UnsupportedOperationException("cannot be instantiated")
    }
}