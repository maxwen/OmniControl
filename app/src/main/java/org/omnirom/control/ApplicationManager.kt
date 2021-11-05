package org.omnirom.control

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.graphics.drawable.BitmapDrawable

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

import android.graphics.PaintFlagsDrawFilter




class ApplicationManager {

    val mContext: Context
    val mAppList: ArrayList<Application> = ArrayList()
    val mIconSize: Int

    constructor(context: Context) {
        mContext = context
        mIconSize = mContext.resources.getDimensionPixelSize(R.dimen.applist_icon_size)
    }

    fun getAppIcon(app: Application): Drawable {
        val pm: PackageManager = mContext.getPackageManager()
        return try {
            resizeAppIcon(pm.getApplicationIcon(app.mPackage), mIconSize, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            pm.defaultActivityIcon
        }
    }

    fun startApp(app: Application) {
        val intent = Intent()
        intent.component = app.getComponentName()
        try {
            mContext.startActivity(intent)
        } catch (e: Exception) {

        }
    }

    fun addApp(packageName: String, activity: String, title: String, summary: String) {
        mAppList.add(Application(packageName, activity, title, summary))
    }

    fun getAppOfPackage(packageName: String): Application? {
        for (app in mAppList) {
            if (app.mPackage.equals(packageName)) return app
        }
        return null
    }

    fun isAvailableApp(app: Application): Boolean {
        val pm: PackageManager = mContext.getPackageManager()
        return try {
            val enabled = pm.getApplicationEnabledSetting(app.mPackage)
            enabled != PackageManager.COMPONENT_ENABLED_STATE_DISABLED &&
                    enabled != PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun resizeAppIcon(image: Drawable,
        size: Int, border: Int): Drawable {
        val canvas = Canvas()
        canvas.setDrawFilter(
            PaintFlagsDrawFilter(
                Paint.ANTI_ALIAS_FLAG,
                Paint.FILTER_BITMAP_FLAG
            )
        )
        val bmResult = Bitmap.createBitmap(
            size + border, size + border,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bmResult)
        image.setBounds(border / 2, border / 2, size, size)
        image.draw(canvas)
        return BitmapDrawable(mContext.resources, bmResult)
    }

}