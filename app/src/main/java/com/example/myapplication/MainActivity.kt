package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat.START
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.ui.about.AboutFragment
import com.example.myapplication.ui.examples.ExamplesFragment
import com.example.myapplication.ui.formulas.FormulasFragment
import com.google.android.material.navigation.NavigationView
import com.yariksoffice.lingver.Lingver


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navController : NavController
    private lateinit var toolbar: Toolbar
    private val fragmentManager: FragmentManager = supportFragmentManager


    companion object {
        var lineList = mutableListOf<String>()
        var exampleList = mutableListOf<String>()
        var aboutText= mutableListOf<String>()
        var termsText = mutableListOf<String>()
        var calcText = mutableListOf<String>()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val localPrefix:String = Lingver.getInstance().getLanguage()
        Lingver.getInstance().setLocale(this, localPrefix)
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.black)
        val bufferedReader = applicationContext.assets.open("$localPrefix/formulasFile.txt").bufferedReader()
        bufferedReader.useLines { lines -> lines.forEach { lineList.add(it) } }
        val bufferedReaderTwo = applicationContext.assets.open("$localPrefix/examplesFile.txt").bufferedReader()
        bufferedReaderTwo.useLines { lines -> lines.forEach { exampleList.add(it) } }
        val bufferedReaderThree = applicationContext.assets.open("$localPrefix/aboutApp.txt").bufferedReader()
        bufferedReaderThree.useLines { lines -> lines.forEach { aboutText.add(it) } }
        val bufferedReaderFour = applicationContext.assets.open("$localPrefix/termsUse.txt").bufferedReader()
        bufferedReaderFour.useLines { lines -> lines.forEach { termsText.add(it) } }
        val bufferedReaderFive = applicationContext.assets.open("en/calcFile.txt").bufferedReader()
        bufferedReaderFive.useLines { lines -> lines.forEach { calcText.add(it) } }
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.main)
        toolbar.setTitleTextColor(Color.DKGRAY)
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        navController = findNavController(R.id.container)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_formulas, R.id.nav_examples, R.id.nav_about
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    override fun onBackPressed() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(START)) {
            drawer.closeDrawer(START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment = Fragment()
        when (item.itemId) {
            R.id.nav_formulas -> {
                fragment = FormulasFragment()
            }
            R.id.nav_examples -> {
                fragment = ExamplesFragment()
            }
            R.id.nav_about -> {
                fragment = AboutFragment()
            }
        }
        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commitNow()
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val englishLanguageChosenButton: MenuItem = toolbar.menu.findItem(R.id.chooseEnglish)
        val russianLanguageChosenButton: MenuItem = toolbar.menu.findItem(R.id.chooseRussian)
        when(Lingver.getInstance().getLanguage())
        {
            "ru" -> russianLanguageChosenButton.isChecked = true
            "en" -> englishLanguageChosenButton.isChecked = true
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.container)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        when(item.itemId)
        {
            R.id.chooseRussian -> {
                val englishLanguageChosenButton: MenuItem =
                    toolbar.menu.findItem(R.id.chooseEnglish)
                englishLanguageChosenButton.isChecked = false
                setNewLocale(App.LANGUAGE_RUSSIAN)
                return true
            }
            R.id.chooseEnglish -> {
                val russianLanguageChosenButton: MenuItem =
                    toolbar.menu.findItem(R.id.chooseRussian)
                russianLanguageChosenButton.isChecked = false
                setNewLocale(App.LANGUAGE_ENGLISH)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setNewLocale(language: String) {
        Lingver.getInstance().setLocale(this, language)
        restart()
    }

    private fun restart() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
        showMessage()
        recreate()
    }

    private fun showMessage()
    {
        val toast = Toast.makeText(this, getString(R.string.restartMessage), Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        val view: View? = toast.view
        view?.setBackgroundResource(R.drawable.toast_drawable)
        val text = view?.findViewById<View>(android.R.id.message) as TextView
        text.setBackgroundColor(Color.LTGRAY)
        text.setTextAppearance(R.style.toastTextStyle)
        text.gravity = Gravity.CENTER
        toast.show()
    }
}