package org.omnirom.control

import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat


class BarsSettingsFragment : PreferenceFragmentCompat() {

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? SettingsActivity)?.updateFragmentTitle(resources.getString(R.string.bars_settings_title))
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.bars_settings_preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        return super.onPreferenceTreeClick(preference)
    }
}