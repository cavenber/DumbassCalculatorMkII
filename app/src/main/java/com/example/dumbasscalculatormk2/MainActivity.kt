package com.example.dumbasscalculatormk2

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewStub
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout

    lateinit var nv_side: NavigationView
    lateinit var tv_snv: TextView

    lateinit var toolbar: Toolbar
    lateinit var toggle: ActionBarDrawerToggle

    private lateinit var programContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout = findViewById(R.id.main)

        nv_side = findViewById(R.id.nv_side)
        tv_snv = findViewById(R.id.tv_snv)
        tv_snv.text = getString(R.string.arithmetic_operation)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        programContainer = findViewById(R.id.program_container)
        showLayout(R.layout.activity_arithmetic_operation)

        nv_side.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.snv_arithmetic_operation -> {
                    showLayout(R.layout.activity_arithmetic_operation)
                }
                R.id.snv_quadratic_equation -> {
                    showLayout(R.layout.activity_quadratic_equation)
                }
            }
            tv_snv.text = item.title
            drawerLayout.closeDrawers()
            drawerLayout.closeDrawers()
            true
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLayout(layoutResId: Int) {
        programContainer.removeAllViews()
        val view = layoutInflater.inflate(layoutResId, programContainer, false)
        programContainer.addView(view)
    }
}