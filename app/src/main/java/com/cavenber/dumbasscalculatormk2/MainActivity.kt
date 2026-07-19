package com.cavenber.dumbasscalculatormk2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.program_container,
                ArithmeticOperation()).commit()
        }

        // sidebar item selection logic
        nv_side.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.snv_arithmetic_operation -> {
                    supportFragmentManager.beginTransaction().replace(R.id.program_container,
                        ArithmeticOperation()).commit()

                }
                R.id.snv_quadratic_equation -> {
                    supportFragmentManager.beginTransaction().replace(R.id.program_container,
                        QuadraticEquation()).commit()
                }
                R.id.snv_variation_direct -> {
                    supportFragmentManager.beginTransaction().replace(R.id.program_container,
                        DirectVariation()).commit()
                }
                R.id.snv_variation_inverse -> {
                    supportFragmentManager.beginTransaction().replace(R.id.program_container,
                        InverseVariation()).commit()
                }
                R.id.snv_arithmetic_sequence -> {
                    supportFragmentManager.beginTransaction().replace(R.id.program_container,
                        ArithmeticSequence()).commit()
                }
                R.id.snv_arithmetic_series -> {
                    supportFragmentManager.beginTransaction().replace(R.id.program_container,
                        ArithmeticSeries()).commit()
                }
                R.id.snv_geometric_sequence -> {
                    supportFragmentManager.beginTransaction().replace(R.id.program_container,
                        GeometricSequence()).commit()
                }
                R.id.snv_geometric_series -> {
                    supportFragmentManager.beginTransaction().replace(R.id.program_container,
                        GeometricSeries()).commit()
                }
            }
            tv_snv.text = item.title
            drawerLayout.closeDrawers()
            true
        })
    }

    // options menu aka 3 dots thingy's initiation logic
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    // options menu item selection logic
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.om_display_answer -> {
                supportFragmentManager.beginTransaction().replace(R.id.program_container, AnswerLog()).commit()
            }
        }
        tv_snv.text = item.title
        return true
    }
}