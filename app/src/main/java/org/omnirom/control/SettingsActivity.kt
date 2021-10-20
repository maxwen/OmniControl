package org.omnirom.control

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        val toolbar:Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        toolbar.setTitleTextAppearance(this, R.style.Theme_OmniControl_ToolBar_TitleTextStyle)
        setSupportActionBar(toolbar)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        val KEY_OMNISTORE = "omnistore"
        val KEY_APPS_LIST = "apps_list"
        val OMNISTORE_APP_PKG = "org.omnirom.omnistore"
        val OMNISTORE_INSTALL_PKG = "org.omnirom.omnistoreinstaller"
        val OMNISTORE_ACTIVITY = ".MainActivity"

        fun isAvailableApp(packageName: String): Boolean {
            val pm: PackageManager = requireContext().getPackageManager()
            return try {
                val enabled = pm.getApplicationEnabledSetting(packageName)
                enabled != PackageManager.COMPONENT_ENABLED_STATE_DISABLED &&
                        enabled != PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        }

        fun getAppIcon(packageName: String): Drawable {
            val pm: PackageManager = requireContext().getPackageManager()
            return try {
                pm.getApplicationIcon(packageName)
            } catch (e: PackageManager.NameNotFoundException) {
                pm.defaultActivityIcon
            }
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            var omniStoreEntry: Preference? = findPreference(KEY_OMNISTORE)
            if (omniStoreEntry != null) {
                omniStoreEntry.isVisible =
                    isAvailableApp(OMNISTORE_APP_PKG) || isAvailableApp(OMNISTORE_INSTALL_PKG)
                if (isAvailableApp(OMNISTORE_APP_PKG))
                    omniStoreEntry.icon = getAppIcon(OMNISTORE_APP_PKG);
                if (isAvailableApp(OMNISTORE_INSTALL_PKG))
                    omniStoreEntry.icon = getAppIcon(OMNISTORE_INSTALL_PKG);
            }

            var appCategory: PreferenceCategory? = findPreference(KEY_APPS_LIST)
            if (appCategory != null) {
                for(i in 0 until appCategory.preferenceCount) {
                    val app = appCategory.getPreference(i)
                    if (app.intent != null){
                        val intent = app.intent
                        if (intent != null && intent.component != null) {
                            val pkg = intent.component!!.packageName
                            if (pkg != null) {
                                app.icon = getAppIcon(pkg)
                            }
                        }
                    }
                }
            }
        }

        override fun onPreferenceTreeClick(preference: Preference?): Boolean {
            if (preference?.key.equals(KEY_OMNISTORE)) {
                if (isAvailableApp(OMNISTORE_APP_PKG)) {
                    val name =
                        ComponentName(OMNISTORE_APP_PKG, OMNISTORE_APP_PKG + OMNISTORE_ACTIVITY)
                    val intent = Intent()
                    intent.component = name
                    requireActivity().startActivity(intent)
                } else if (isAvailableApp(OMNISTORE_INSTALL_PKG)) {
                    val name = ComponentName(
                        OMNISTORE_INSTALL_PKG,
                        OMNISTORE_INSTALL_PKG + OMNISTORE_ACTIVITY
                    )
                    val intent = Intent()
                    intent.component = name
                    requireActivity().startActivity(intent)
                }
                return true
            }
            return super.onPreferenceTreeClick(preference)
        }
    }
}