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
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceCategory

// TODO maybe better to use a GridView
class AppListFragment : AbstractSettingsFragment() {
    private val KEY_APPS_LIST = "apps_list"
    lateinit var appManager: ApplicationManager
    var updateAppList: Boolean = false

    override fun getFragmentTitle(): String {
        return resources.getString(R.string.applist_settings_title)
    }

    override fun getFragmentSummary(): String {
        return resources.getString(R.string.applist_settings_summary)
    }

    override fun getFragmentIcon(): Int {
        return R.drawable.applist_icon
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.applist_preferences, rootKey)

        appManager = ApplicationManager(requireContext())

        appManager.addApp(
            "org.omnirom.omniswitch",
            "org.omnirom.omniswitch.SettingsActivity",
            resources.getString(R.string.omniswitch_title),
            resources.getString(R.string.omniswitch_summary)
        )

        appManager.addApp(
            "org.omnirom.omnichange",
            "org.omnirom.omnichange.OmniMain",
            resources.getString(R.string.changelog_title),
            resources.getString(R.string.changelog_summary)
        )

        appManager.addApp(
            "org.omnirom.omnijaws",
            "org.omnirom.omnijaws.SettingsActivity",
            resources.getString(R.string.weather_config_title),
            resources.getString(R.string.weather_config_summary)
        )

        appManager.addApp(
            "org.omnirom.logcat",
            "com.pluscubed.logcat.ui.LogcatActivity",
            resources.getString(R.string.matlog_title),
            resources.getString(R.string.matlog_summary)
        )

        appManager.addApp(
            "org.omnirom.omniremote",
            "org.omnirom.omniremote.MainActivity",
            resources.getString(R.string.omni_remote_title),
            resources.getString(R.string.omni_remote_summary)
        )

        appManager.addApp(
            "org.omnirom.omnistore",
            "org.omnirom.omnistore.MainActivity",
            resources.getString(R.string.omnistore_title),
            resources.getString(R.string.omnistore_summary)
        )

        createAppList()
    }

    private fun createAppList() {
        var appCategory: PreferenceCategory? = findPreference(KEY_APPS_LIST)
        if (appCategory != null) {
            appCategory.removeAll()
            for (app in appManager.mAppList) {
                // store is always visible cause installer is always there
                if (app.mPackage != "org.omnirom.omnistore") {
                    if (!Utils.isAvailableApp(requireContext(), app.mPackage)) {
                        continue
                    }
                }

                val preference = Preference(requireContext())
                preference.key = app.mPackage
                preference.title = app.mTitle
                preference.summary = app.mSummary
                val intent = Intent()
                intent.component = app.getComponentName()
                preference.intent = intent
                preference.icon = appManager.getAppIcon(app)

                appCategory.addPreference(preference)
            }
            updateAppList = false
        }
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        if (preference?.key != null) {
            var app: Application? = appManager.getAppOfPackage(preference.key)
            if (app != null) {
                if (app.mPackage == "org.omnirom.omnistore") {
                    if (!Utils.isAvailableApp(requireContext(), app.mPackage)) {
                        // start installer instead
                        appManager.startApp(ComponentName("org.omnirom.omnistoreinstaller",
                            "org.omnirom.omnistoreinstaller.MainActivity"))
                        updateAppList = true
                        return true
                    }
                }
                appManager.startApp(app)
                return true
            }
        }
        return super.onPreferenceTreeClick(preference)
    }

    override fun onResume() {
        super.onResume()
        if (updateAppList)
            createAppList()
    }
}