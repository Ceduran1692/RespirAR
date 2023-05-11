package ar.edu.ort.respirar.activities


import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.databinding.ActivityMainBinding
import ar.edu.ort.respirar.fragments.AboutUsFragment
import ar.edu.ort.respirar.fragments.EstationListFragment
import ar.edu.ort.respirar.fragments.MapsFragment
import ar.edu.ort.respirar.fragments.NavigationFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding:ActivityMainBinding

    private lateinit var drawer: DrawerLayout
    private lateinit var toogle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(MapsFragment())
        //replaceFragment(NavigationFragment())

//        binding.navBarMain.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.nav_map -> replaceFragment(MapsFragment())
//                R.id.nav_estationList -> replaceFragment(EstationListFragment())
//
//                else -> {}
//            }
//
//            true
//        }

        //NAVIGATION VIEW
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawerLayout)

        toogle = ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toogle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


        val navigationView: NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)


    }

    private fun replaceFragment(fragment:Fragment){
        val fragmentManager= supportFragmentManager
        val fragmentTransaction= fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frame_layout,fragment)
        fragmentTransaction.commit()
    }

    //FUNCIONES NAVIGATION
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> replaceFragment(MapsFragment())
            R.id.map -> replaceFragment(MapsFragment())
            R.id.stations -> replaceFragment(EstationListFragment())
            R.id.favorite -> Toast.makeText(this,"Favoritos", Toast.LENGTH_SHORT).show()
            R.id.about_us -> replaceFragment(AboutUsFragment())
            R.id.settings -> Toast.makeText(this,"Configuracion", Toast.LENGTH_SHORT).show()
            R.id.out -> Toast.makeText(this,"Salir", Toast.LENGTH_SHORT).show()
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toogle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toogle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
