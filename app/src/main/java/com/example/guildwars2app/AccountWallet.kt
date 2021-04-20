package com.example.guildwars2app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import com.android.volley.toolbox.JsonArrayRequest
import com.example.guildwars2app.Adapters.CoinAdapter
import com.example.guildwars2app.DataDetails.CoinDetails


class AccountWallet : AppCompatActivity(), View.OnClickListener {
    private lateinit var listView :ListView
    internal lateinit var sharedPref: SharedPreferences
    lateinit var queue: RequestQueue
    lateinit var ApiKey:String
    lateinit var Wallet : Array<Pair<Int,Int>>
    lateinit var MergedMonster : Array<CoinDetails>
    lateinit var adapter : CoinAdapter
    var WalletDetailList = arrayListOf<CoinDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        sharedPref = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)
        ApiKey = sharedPref.getString(getString(R.string.shared_api_name), "No key").toString()
        Wallet = emptyArray()
        MergedMonster = emptyArray()


        getAccountInfo(applicationContext)


    }

    fun getAccountInfo(context: Context){

        val url = "https://api.guildwars2.com/v2/account/wallet?access_token=%s".format(ApiKey)
        queue = Volley.newRequestQueue(context)

        val howOld = JsonArrayRequest( Request.Method.GET,url, null,
            { response ->
                println("Done")
                loadAccountData(response)
            }, Response.ErrorListener{
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

        val url = "https://api.guildwars2.com/v2/currencies?ids=all"

        val req = JsonArrayRequest( Request.Method.GET,url, null,
                { response ->
                    println("Done")
                    loadCoinData(response)
                }, Response.ErrorListener {
            error ->
            Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
            println(error.toString())}

            )
            queue.add(req)

        }


    fun loadCoinData(response: JSONArray){

        response.let{

            for(obj in 0 until response.length()){
                val tmpCoin = CoinDetails(

                        response.getJSONObject(obj).getString("id").toString().toInt(),
                        response.getJSONObject(obj).getString("name").toString(),
                        response.getJSONObject(obj).getString("description").toString(),
                        0
                )
                WalletDetailList.add(tmpCoin)
            }
        }
        MergedMonster = MergeLists()
        listView = findViewById<ListView>(R.id.coin_list_view)
        adapter = CoinAdapter(this, MergedMonster)
        listView.adapter = adapter
    }

    fun MergeLists() : Array<CoinDetails>{
        var temp_list = arrayOfNulls<CoinDetails>(Wallet.size)

        for(i in 0 until Wallet.size - 1){
            for(j in 0 until WalletDetailList.size){
                if(Wallet.contains(Pair(WalletDetailList[j].id,Wallet[i].second))){
                    temp_list[i] = WalletDetailList[j]
                    temp_list[i]?.value = Wallet[i].second
                }
            }
        }

        return temp_list as Array<CoinDetails>
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}





