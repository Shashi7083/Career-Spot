package com.intern_job.notification.activity.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.intern_job.notification.R
import com.intern_job.notification.activity.Fragment.*

import com.intern_job.notification.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
     var p_name :String?="hello"
    var p_email :String ? =""
    var previousMenuItem:MenuItem?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("notification", Context.MODE_PRIVATE)

            p_name = sharedPreferences.getString("name","Name")
            p_email = sharedPreferences.getString("email","abc@gmail.com")
           // Toast.makeText(this@MainActivity,p_name+" " +p_email,Toast.LENGTH_SHORT).show()

        supportFragmentManager.beginTransaction().replace(R.id.frame,HomeFragment())
            .commit()




    val header :View = binding.navigationView.getHeaderView(0)
        val name = header.findViewById<TextView>(R.id.profile_name)
        val email = header.findViewById<TextView>(R.id.profile_email)
        name.text = p_name
        email.text = p_email





        setUpToolbar()

        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this@MainActivity,
            binding.drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()


        binding.navigationView.setNavigationItemSelectedListener(this@MainActivity);
        binding.navigationView.bringToFront();
        binding.navigationView.setCheckedItem(R.id.home)


    }

    fun setUpToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "CarrierSpot"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if(previousMenuItem !=null){
            previousMenuItem?.isChecked=false
        }
        item.isCheckable = true
        item.isChecked = true
        previousMenuItem = item

        when (item.itemId) {
            R.id.home->{

                supportFragmentManager.beginTransaction().replace(R.id.frame,HomeFragment())
                    .commit()
                supportActionBar?.title = "CarrierSpot"
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }


            R.id.topCompany->
            {
                supportFragmentManager.beginTransaction().replace(R.id.frame,TopCompanyFragment())
                    .commit()
                supportActionBar?.title = "CarrierSpot"
                binding.drawerLayout.closeDrawer(GravityCompat.START)


            }

            R.id.about->{
                supportFragmentManager.beginTransaction().replace(R.id.frame,AboutFragment()).commit()
                supportActionBar?.title = "About App"

                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }


        }
         return true
    }

    override fun onBackPressed() {

        val frag = supportFragmentManager.findFragmentById(R.id.frame)
        when(frag)
        {
            !is HomeFragment->     openHomeFrag()

                else -> super.onBackPressed()
        }

    }

    fun openHomeFrag(){
        supportFragmentManager.beginTransaction().replace(R.id.frame,HomeFragment())
            .commit()
        supportActionBar?.title = "CarrierSpot"
    }


}