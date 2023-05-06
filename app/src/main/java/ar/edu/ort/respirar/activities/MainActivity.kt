package ar.edu.ort.respirar.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.databinding.ActivityMainBinding
import ar.edu.ort.respirar.fragments.EstationListFragment
import ar.edu.ort.respirar.fragments.MapsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(MapsFragment())

        binding.navBarMain.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_map -> replaceFragment(MapsFragment())
                R.id.nav_estationList -> replaceFragment(EstationListFragment())

                else -> {}
            }

            true
        }
    }

    private fun replaceFragment(fragment:Fragment){
        val fragmentManager= supportFragmentManager
        val fragmentTransaction= fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frame_layout,fragment)
        fragmentTransaction.commit()
    }
}