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



class AccountWallet : AppCompatActivity(){

    internal lateinit var sharedPref: SharedPreferences
    lateinit var queue: RequestQueue
    lateinit var ApiKey:String
    lateinit var Wallet : Array<Pair<Int,Int>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        sharedPref = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)
        ApiKey = sharedPref.getString(getString(R.string.shared_api_name), "No key").toString()
        getAccountInfo(applicationContext)
    }


    fun getAccountInfo(context: Context){

        val url = "https://api.guildwars2.com/v2/account/wallet?access_token=%s".format(ApiKey)
        queue = Volley.newRequestQueue(context)

        val howOld = JsonArrayRequest( Request.Method.GET,url, null,
            { response ->
                print("Done")
                loadAccountData(response)
            }, Response.ErrorListener {
                    error ->
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
                println(error.toString())}

        )
        queue.add(howOld)
    }

    fun loadAccountData(response: JSONArray){

        response?.let{
            val walletCount = response.length()
            val tmpData = arrayOfNulls<Pair<Int,Int>>(walletCount)
            for(i in 0 until walletCount){
                val id = response.getJSONObject(i).getString("id").toInt()
                val value = response.getJSONObject(i).getString("value").toInt()

                tmpData[i] = Pair(id,value)
                print(tmpData[i]?.second)
            }
            Wallet = tmpData as Array<Pair<Int, Int>>
        }

    }

}