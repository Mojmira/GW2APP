package com.example.guildwars2app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    internal lateinit var apiKeyText: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Guild Wars 2 App"
        setContentView(R.layout.activity_main)
        apiKeyText = findViewById(R.id.APIkeyView)
        val sharedPref = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)
    }

    fun SaveAPIKey(view: View){
        println(apiKeyText.text)

    }
}