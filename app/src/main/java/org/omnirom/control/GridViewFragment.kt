package org.omnirom.control

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class GridViewFragment() : Fragment() {
    lateinit var gridView: GridView
    val gridItems: ArrayList<GridItem>

    init {
        this.gridItems = ArrayList<GridItem>()
        gridItems.add(
            GridItem(
                R.string.applist_settings_title,
                R.drawable.applist_icon,
                AppListFragment()
            )
        )
        gridItems.add(
            GridItem(
                R.string.bars_settings_title,
                R.drawable.ic_bars_tile,
                BarsSettingsFragment()
            )
        )
        gridItems.add(
            GridItem(
                R.string.more_settings_title,
                R.drawable.ic_settings_more,
                MoreSettingsFragment()
            )
        )
    }

    class GridItem(text: Int, icon: Int, fragment: Fragment) {
        val gridText: Int
        val gridIcon: Int
        val gridFragment: Fragment

        init {
            gridText = text
            gridIcon = icon
            gridFragment = fragment
        }
    }

    class GridViewAdapter(context: Context, gridItems: List<GridItem>) :
        ArrayAdapter<GridItem>(context, 0, gridItems) {

        val vi: LayoutInflater

        init {
            this.vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val viewItem: View = if (convertView == null) {
                vi.inflate(R.layout.grid_item, null)
            } else {
                convertView
            }

            val gridItem: GridItem? = getItem(position)
            viewItem.findViewById<TextView>(R.id.grid_item_text).setText(gridItem?.gridText!!)
            viewItem.findViewById<ImageView>(R.id.grid_item_icon)
                .setImageResource(gridItem?.gridIcon!!)

            return viewItem
        }
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

        gridView.adapter = GridViewAdapter(requireContext(), gridItems)

        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val gridItem: GridItem = gridItems.get(position)
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, gridItem.gridFragment)
                    .addToBackStack(null)
                    .commit()
            }
    }


}