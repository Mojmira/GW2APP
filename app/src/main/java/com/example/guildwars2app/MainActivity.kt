package com.example.guildwars2app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    internal lateinit var apiKeyText: EditText
    internal lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Guild Wars 2 App"
        setContentView(R.layout.activity_main)
        apiKeyText = findViewById(R.id.APIkeyView)
        sharedPref = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)
        val key = sharedPref.getString(getString(R.string.shared_api_name), "No key")
        println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        println(key)
        if(key !== "No key"){
            apiKeyText.setText(key)
        }
    }

    fun SaveAPIKey(view: View){
        with (sharedPref.edit()) {
            putString(getString(R.string.shared_api_name), apiKeyText.text.toString())
            apply()
        }

        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)

        //val API = sharedPref.getString(getString(R.string.shared_api_name), "No key")
        //println(API)

    }
}