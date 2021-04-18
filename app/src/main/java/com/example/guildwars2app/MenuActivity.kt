package com.example.guildwars2app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Menu"
        setContentView(R.layout.activity_menu)

    }

    fun goToCharacterDetails(view: View) {
        val intent = Intent(this, CharacterActivity::class.java)
        startActivity(intent)
    }

    fun goToAccountAge(view: View) {
        val intent = Intent(this, AccountAge::class.java)
        startActivity(intent)
    }

}
