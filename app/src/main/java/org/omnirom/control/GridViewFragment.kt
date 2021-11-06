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
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class GridViewFragment() : Fragment() {
    private lateinit var gridView: GridView
    private val gridItems: ArrayList<GridItem> = ArrayList()

    abstract class GridItem(title: Int, summary: Int, icon: Int) {
        val gridTitle: Int = title
        val gridSummary: Int = summary
        val gridIcon: Int = icon
    }

    class FragmentGridItem(title: Int, summary: Int, icon: Int, fragment: Fragment) :
        GridItem(title, summary, icon) {
        val gridFragment: Fragment = fragment
    }

    class IntentGridItem(title: Int, summary: Int, icon: Int, intent: Intent) :
        GridItem(title, summary, icon) {
        val gridIntent: Intent = intent
    }

    class GridViewAdapter(context: Context, gridItems: List<GridItem>) :
        ArrayAdapter<GridItem>(context, 0, gridItems) {

        private val vi: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val viewItem: View = convertView ?: vi.inflate(R.layout.grid_item, null)

            val gridItem: GridItem = getItem(position)!!

            viewItem.findViewById<TextView>(R.id.grid_item_title).setText(gridItem.gridTitle)
            viewItem.findViewById<ImageView>(R.id.grid_item_icon)
                .setImageResource(gridItem.gridIcon)
            val summary = context.resources.getString(gridItem.gridSummary)
            if (summary.isNotEmpty())
                viewItem.findViewById<TextView>(R.id.grid_item_summary)
                    .setText(gridItem.gridSummary)
            else
                viewItem.findViewById<TextView>(R.id.grid_item_summary).visibility = View.GONE
            return viewItem
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gridItems.add(
            FragmentGridItem(
                R.string.applist_settings_title,
                R.string.applist_settings_summary,
                R.drawable.applist_icon,
                AppListFragment()
            )
        )
        gridItems.add(
            FragmentGridItem(
                R.string.bars_settings_title,
                R.string.bars_settings_summary,
                R.drawable.ic_bars_tile,
                BarsSettingsFragment()
            )
        )
        gridItems.add(
            FragmentGridItem(
                R.string.button_settings_title,
                R.string.button_settings_summary,
                R.drawable.ic_settings_buttons,
                ButtonSettingsFragment()
            )
        )
        if (Utils.isAvailableApp(requireContext(), "org.omnirom.omnistyle")) {
            val wallpaperIntent = Intent()
            wallpaperIntent.component = ComponentName(
                "org.omnirom.omnistyle",
                "org.omnirom.omnistyle.WallpaperActivity"
            )
            gridItems.add(
                IntentGridItem(
                    R.string.wallpaper_title,
                    R.string.wallpaper_summary,
                    R.drawable.ic_wallpaper,
                    wallpaperIntent
                )
            )
        }
        gridItems.add(
            FragmentGridItem(
                R.string.more_settings_title,
                R.string.more_settings_summary,
                R.drawable.ic_settings_more,
                MoreSettingsFragment()
            )
        )
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? SettingsActivity)?.updateFragmentTitle(resources.getString(R.string.app_name))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.grid_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridView = view.findViewById(R.id.grid_view)
        gridView.numColumns = resources.getInteger(R.integer.grid_view_columns)
        gridView.adapter = GridViewAdapter(requireContext(), gridItems)
        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val gridItem: GridItem = gridItems.get(position)
                if (gridItem is FragmentGridItem)
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.settings, gridItem.gridFragment)
                        .addToBackStack(null)
                        .commit()
                else if (gridItem is IntentGridItem)
                    requireActivity().startActivity(gridItem.gridIntent)
            }
    }


}