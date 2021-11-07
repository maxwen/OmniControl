package org.omnirom.control

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import org.omnirom.omnilib.preference.SecureCheckBoxPreference
import org.omnirom.omnilib.preference.SecureSettingSwitchPreference
import org.omnirom.omnilib.preference.SystemCheckBoxPreference
import org.omnirom.omnilib.preference.SystemSettingSwitchPreference

abstract class AbstractSettingsFragment() : PreferenceFragmentCompat() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO this is just to create some reference to OmniLib
        val sysSwitch = SystemSettingSwitchPreference(requireContext())
        val sysCheck = SystemCheckBoxPreference(requireContext())
        val secSwitch = SecureSettingSwitchPreference(requireContext())
        val secCheck = SecureCheckBoxPreference(requireContext())
    }

    abstract fun getFragmentTitle(): String

    abstract fun getFragmentSummary(): String

    abstract fun getFragmentIcon(): Int

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? SettingsActivity)?.updateFragmentTitle(
            getFragmentTitle(),
            getFragmentSummary(),
            getFragmentIcon()
        )
    }
}