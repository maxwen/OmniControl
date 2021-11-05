package org.omnirom.control

import android.content.ComponentName

class Application {
    val mPackage: String
    val mActivity:String
    val mTitle: String
    val mSummary: String

    constructor(packageName: String, activity: String, title: String, summary: String){
        mActivity=activity
        mPackage=packageName
        mTitle=title
        mSummary=summary
    }
    fun getComponentName() : ComponentName{
        return ComponentName(mPackage, mActivity)
    }
}