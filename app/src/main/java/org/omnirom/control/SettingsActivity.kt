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

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment


class SettingsActivity : AppCompatActivity() {

    lateinit var titleView: TextView
    private val CURRENT_FRAGMENT = "current_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        var fragment: Fragment = GridViewFragment()
        if (savedInstanceState != null) {
            val savedFragment =
                supportFragmentManager.getFragment(savedInstanceState, CURRENT_FRAGMENT);
            if (savedFragment != null)
                fragment = savedFragment
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.settings, fragment)
            .commit()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        titleView = findViewById(R.id.fragment_title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateFragmentTitle(title: String) {
        titleView.text = title
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentFragment = supportFragmentManager.fragments.last()
        supportFragmentManager.putFragment(outState, CURRENT_FRAGMENT, currentFragment)
    }
}