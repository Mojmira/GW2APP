package com.example.guildwars2app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest



class AccountAge : AppCompatActivity(){

    internal lateinit var sharedPref: SharedPreferences
    lateinit var queue: RequestQueue
    lateinit var ApiKey:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
       ApiKey = sharedPref.getString(getString(R.string.shared_api_name), "No key").toString()
    }


    fun getAccountInfo(context: Context){

        val url = "https://api.guildwars2.com/v2/account?access_token=%s".format(ApiKey)
        queue = Volley.newRequestQueue(context)

        val charactersList = JsonArrayRequest( Request.Method.GET,url, null,
            { response ->
                println("Success!")
            }, Response.ErrorListener {
                    error ->
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
                println(error.toString())}

        )
        queue.add(charactersList)
    }


}