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

    fun goToAccountWallet(view: View) {
        val intent = Intent(this, AccountWallet::class.java)
        startActivity(intent)
    }

    fun goToGamestones(view: View) {
        val intent = Intent(this, GemExchangeActivity::class.java)
        startActivity(intent)
    }

    fun goToStatistics(view: View) {
        val intent = Intent(this, StatisticsActivity::class.java)
        startActivity(intent)
    }

    fun goToAchievements(view: View) {
        val intent = Intent(this, AchievementActivity::class.java)
        startActivity(intent)
    }

}
