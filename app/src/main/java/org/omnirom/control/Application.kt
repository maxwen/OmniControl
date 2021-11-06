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
package org.omnirom.control

import android.content.ComponentName

class Application(packageName: String, activity: String, title: String, summary: String) {
    val mPackage: String = packageName
    val mActivity:String = activity
    val mTitle: String = title
    val mSummary: String = summary

    fun getComponentName() : ComponentName{
        return ComponentName(mPackage, mActivity)
    }
}