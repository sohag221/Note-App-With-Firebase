package com.example.noteappwithfirebase

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.noteappwithfirebase.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {




    lateinit var binding: ActivityMainBinding
    lateinit var firebaseAuth: FirebaseAuth

    lateinit var drawerLayout: DrawerLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout=findViewById(R.id.drawer_layout)

        firebaseAuth = FirebaseAuth.getInstance()
        binding.creatnote.setOnClickListener {
            val creatIntent = Intent(this, creatNote::class.java)
            startActivity(creatIntent)
        }

        setSupportActionBar(binding.toolbar)
        binding.navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this,drawerLayout,binding.toolbar,R.string.open_nav,R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home_nav -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                if (currentFragment!is profile){
                    drawerLayout.closeDrawer(GravityCompat.START)}
                else{
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                
            }
            R.id.profile_nav ->{
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,profile()).commit()
            }
            R.id.setting_nav ->Toast.makeText(this,"Setting is clicked",Toast.LENGTH_LONG).show()
            R.id.share_nav ->Toast.makeText(this,"Share is clicked",Toast.LENGTH_LONG).show()
            R.id.info_nav ->Toast.makeText(this,"Info is clicked",Toast.LENGTH_LONG).show()


            R.id.logout_nav ->{
                firebaseAuth.signOut()
                finish()
                val intent = Intent(this, login::class.java)
                startActivity(intent)

            }

        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return  true
    }


    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
        super.onBackPressed()
    }

}