package org.omnirom.control
/*
 *  Copyright (C) 2021 The OmniROM Project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.graphics.drawable.BitmapDrawable

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

import android.graphics.PaintFlagsDrawFilter




class ApplicationManager(context: Context) {
    private val mContext: Context = context
    val mAppList: ArrayList<Application> = ArrayList()
    private val mIconSize: Int = mContext.resources.getDimensionPixelSize(R.dimen.applist_icon_size)

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
            if (app.mPackage == packageName) return app
        }
        return null
    }

    private fun resizeAppIcon(image: Drawable,
                              size: Int, border: Int): Drawable {
        val canvas = Canvas()
        canvas.drawFilter = PaintFlagsDrawFilter(
            Paint.ANTI_ALIAS_FLAG,
            Paint.FILTER_BITMAP_FLAG
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