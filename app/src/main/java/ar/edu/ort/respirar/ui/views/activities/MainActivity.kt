package ar.edu.ort.respirar.ui.views.activities

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, NavController.OnDestinationChangedListener {

    private lateinit var binding:ActivityMainBinding

    private lateinit var navController: NavController

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //NAV CONTROLLER
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //NAVIGATION VIEW
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawer = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)
        navController.addOnDestinationChangedListener(this)


        val colorMenu = ContextCompat.getColorStateList(this, R.color.MENU)
        navigationView.setItemTextColor(colorMenu)
        navigationView.setItemIconTintList(colorMenu)

        navController.navigate(R.id.homeFragment)
    }


    //FUNCIONES NAVIGATION
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> navController.navigate(R.id.homeFragment)
            R.id.map -> navController.navigate(R.id.mapsFragment)
            R.id.stations -> navController.navigate(R.id.stationListFragment)
            R.id.favorite -> navController.navigate(R.id.stationFavoritesListFragment)
            R.id.about_us -> navController.navigate(R.id.aboutUsFragment)
            R.id.settings -> Toast.makeText(this,"Configuracion", Toast.LENGTH_SHORT).show()
            R.id.out -> finish()
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.setToolbarNavigationClickListener{
            navController.navigateUp()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        if (destination.id == R.id.stationDetailsFragment || destination.id == R.id.stationHistoricoFragment) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            toggle.isDrawerIndicatorEnabled = false
            toggle.setHomeAsUpIndicator(R.drawable.navigation_back)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            toggle.isDrawerIndicatorEnabled = true
        }
        toggle.syncState()
    }


}
