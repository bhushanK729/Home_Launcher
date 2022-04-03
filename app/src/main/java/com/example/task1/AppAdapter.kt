package com.example.task1

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppAdapter(private val mList: List<AppDetail>, private val manager: PackageManager, private val context: Context) : RecyclerView.Adapter<AppAdapter.ViewHolder>(),
    Filterable {

    var appFilterList = ArrayList<AppDetail>()
    // exampleListFull . exampleList

    init {
        appFilterList = mList as ArrayList<AppDetail>
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val appItem = appFilterList[position]

        holder.imgIcon.setImageDrawable(appItem.icon)
        holder.txtLabel.setText(appItem.label)
        holder.txtName.setText(appItem.name)
        holder.txtVersion.setText(appItem.versionCode.toString()+" | "+appItem.versionName)

        holder.itemView.setOnClickListener {
            val i = manager.getLaunchIntentForPackage(appItem.name.toString());
            context.startActivity(i)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return appFilterList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imgIcon: ImageView = itemView.findViewById(R.id.imgIcon)
        val txtLabel: TextView = itemView.findViewById(R.id.txtLabel)
        val txtName: TextView = itemView.findViewById(R.id.txtName)
        val txtVersion: TextView = itemView.findViewById(R.id.txtVersion)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    appFilterList = mList as ArrayList<AppDetail>
                } else {
                    val resultList = ArrayList<AppDetail>()
                    for (row in mList) {
                        if (row.name!!.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    appFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = appFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                appFilterList = results?.values as ArrayList<AppDetail>
                notifyDataSetChanged()
            }
        }
    }
}