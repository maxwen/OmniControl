package org.omnirom.control;

import android.content.Context
import android.content.pm.PackageManager

object Utils {
    fun isAvailableApp(context: Context, packageName: String): Boolean {
            val pm: PackageManager = context.getPackageManager()
            return try {
                    val enabled = pm.getApplicationEnabledSetting(packageName)
                    enabled != PackageManager.COMPONENT_ENABLED_STATE_DISABLED &&
                            enabled != PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
            } catch (e: Exception) {
                    false
            }
    }
}
