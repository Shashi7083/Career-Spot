package com.intern_job.notification.activity.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.intern_job.notification.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailsBinding

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("notification", Context.MODE_PRIVATE)

        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn",false)

        if(isLoggedIn){
            val intent = Intent(this@DetailsActivity, MainActivity::class.java)
            startActivity(intent)
            intent.putExtra("name",binding.name.text.toString())
            intent.putExtra("email",binding.email.text.toString())
            finish()
        }


        binding.start.setOnClickListener {
            savePreference()
            val intent = Intent(this@DetailsActivity, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }



    private fun savePreference(){
        sharedPreferences.edit().putBoolean("isLoggedIn",true).apply()
        sharedPreferences.edit().putString("name",binding.name.text.toString()).apply()
        sharedPreferences.edit().putString("email",binding.email.text.toString()).apply()
    }
}