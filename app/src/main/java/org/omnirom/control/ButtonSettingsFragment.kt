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

import android.content.res.Resources
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen


class ButtonSettingsFragment : AbstractSettingsFragment() {
    private val CATEGORY_POWER = "button_power"
    private val KEY_ADVANCED_REBOOT = "advanced_reboot"

    override fun getFragmentTitle(): String {
        return resources.getString(R.string.button_settings_title)
    }

    override fun getFragmentSummary(): String {
        return resources.getString(R.string.button_settings_summary)
    }

    override fun getFragmentIcon(): Int {
        return R.drawable.ic_settings_buttons
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.button_settings_preferences, rootKey)

        val powerCategory: PreferenceCategory? = findPreference(CATEGORY_POWER)
        if (powerCategory != null) {
            val id = Resources.getSystem().getIdentifier("config_rebootActionsList", "array", "android")
            val rebootList = Resources.getSystem().getStringArray(id)
            if (rebootList.isEmpty()) {
                val advancedReboot: Preference? = findPreference(KEY_ADVANCED_REBOOT)
                if (advancedReboot != null) {
                    powerCategory.removePreference(advancedReboot)
                }
            }
            if (powerCategory.preferenceCount == 0){
                // TODO
            }
        }
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        return super.onPreferenceTreeClick(preference)
    }
}