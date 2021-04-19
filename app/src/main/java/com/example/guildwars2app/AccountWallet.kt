package com.example.guildwars2app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock.sleep
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
    lateinit var WalletDetailList : Array<CoinDetails>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        sharedPref = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)
        ApiKey = sharedPref.getString(getString(R.string.shared_api_name), "No key").toString()
        Wallet = emptyArray()
        WalletDetailList = emptyArray()
        getAccountInfo(applicationContext)
    }

    fun getAccountInfo(context: Context){

        val url = "https://api.guildwars2.com/v2/account/wallet?access_token=%s".format(ApiKey)
        queue = Volley.newRequestQueue(context)

        val howOld = JsonArrayRequest( Request.Method.GET,url, null,
            { response ->
                println("Done")
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
            }
            Wallet = tmpData as Array<Pair<Int, Int>>
            getCoinsDetails(applicationContext)
        }
    }

    fun getCoinsDetails(context: Context){

        queue = Volley.newRequestQueue(context)

        WalletDetailList = Array<CoinDetails>(Wallet.size){ CoinDetails(0,"","",0) }
        val url = "https://api.guildwars2.com/v2/currencies?id=%d"

        for(i in 0 until Wallet.size){
            var tmp_coin : CoinDetails
            val req = JsonObjectRequest( Request.Method.GET,url.format(Wallet[i].first), null,
                    { response ->
                        println("Done")
                        tmp_coin = loadCoinData(response)
                        tmp_coin.value = Wallet[i].second
                        WalletDetailList[i].id = tmp_coin.id
                        WalletDetailList[i].name = tmp_coin.name
                        WalletDetailList[i].description = tmp_coin.description
                        WalletDetailList[i].value = tmp_coin.value
                    }, Response.ErrorListener {
                error ->
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
                println(error.toString())}

            )
            queue.add(req)

        }
        for(el in WalletDetailList){
            println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + el.name +" "+ el.description+" "+el.value.toString())
        }


    }

    fun loadCoinData(response: JSONObject) : CoinDetails{
        response?.let{

            val tmpCoin = CoinDetails(

                    response.getString("id").toString().toInt(),
                    response.getString("name").toString(),
                    response.getString("description").toString(),
                    0
            )
            return tmpCoin
        }
        return CoinDetails(0,"error","error",0)
    }

}