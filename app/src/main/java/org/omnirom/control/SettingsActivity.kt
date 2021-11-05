package org.omnirom.control

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.w3c.dom.Text


class SettingsActivity : AppCompatActivity() {

    lateinit var titleView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, GridViewFragment())
                    .commit()
        }
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitle("")
        setSupportActionBar(toolbar)

        titleView = findViewById<TextView>(R.id.fragment_title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateFragmentTitle(title: String) {
        titleView.setText(title)
    }
}