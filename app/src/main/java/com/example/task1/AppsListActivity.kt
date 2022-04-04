package com.example.task1

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView


class AppsListActivity : AppCompatActivity() {

    private var manager: PackageManager? = null
    private var apps: MutableList<AppDetail>? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AppAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apps_list)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        loadApplication()

        val searchView = findViewById<SearchView>(R.id.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Log.d("onQueryTextChange", "query: " + query)
                adapter.filter.filter(query)
                return true
            }
        })
    }

    private fun loadApplication() {
        manager = packageManager
        apps = ArrayList<AppDetail>()
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        val availableActivities: List<ResolveInfo> = manager!!.queryIntentActivities(i, 0)
        for (ri in availableActivities) {
            val app = AppDetail()
            app.label = ri.loadLabel(manager).toString()
            app.name = ri.activityInfo.packageName
            app.icon = ri.activityInfo.loadIcon(manager)
            app.versionCode = manager!!.getPackageInfo(ri.activityInfo.packageName, 0).versionCode
            app.versionName = manager!!.getPackageInfo(ri.activityInfo.packageName, 0).versionName
            apps!!.add(app)
        }

        adapter = AppAdapter(apps!!, manager!!, this)
        recyclerView.adapter = adapter
    }
}