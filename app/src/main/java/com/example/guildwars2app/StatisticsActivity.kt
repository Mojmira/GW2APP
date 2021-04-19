package com.example.guildwars2app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class StatisticsActivity: AppCompatActivity() {

    lateinit var rankText: TextView
    lateinit var rankPointsText: TextView
    lateinit var winsText: TextView
    lateinit var lossesText: TextView
    lateinit var rankImage: ImageView

    internal lateinit var sharedPref: SharedPreferences
    lateinit var ApiKey:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "PvP Statistics"
        setContentView(R.layout.activity_statistics)

        sharedPref = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)
        ApiKey = sharedPref.getString(getString(R.string.shared_api_name), "No key").toString()

        rankText = findViewById(R.id.rankTextView)
        rankPointsText = findViewById(R.id.rankPointsView)
        winsText = findViewById(R.id.winsTextView)
        lossesText = findViewById(R.id.lossesTextView)
        rankImage = findViewById(R.id.rankImageView)

        getStatsInfo(applicationContext)

    }

    private fun getStatsInfo(context: Context) {
        val url = "https://api.guildwars2.com/v2/pvp/stats?access_token=%s".format(ApiKey)

        queue = Volley.newRequestQueue(context)

        val statisticsReq = JsonObjectRequest(
                Request.Method.GET,url, null,
                {
                    response ->
                    println("Success!")
                    showData(response)

                }, Response.ErrorListener {
            error ->
            Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
            println(error.toString())}

        )
        queue.add(statisticsReq)


        }

    private fun showData(response: JSONObject?) {
        response?.let{
            rankText.text = "Rank: %d".format(response.getInt("pvp_rank"))
            rankPointsText.text = "Points: %d".format(response.getInt("pvp_rank_points"))
            winsText.text = "Wins: %d".format(response.getJSONObject("aggregate").getInt("wins"))
            lossesText.text = "Losses: %d".format(response.getJSONObject("aggregate").getInt("losses"))
            loadRankImg(response.getInt("pvp_rank"))

        }

    }

    private fun loadRankImg(rank:Int) {
        if(rank<10)
            rankImage.setImageResource(R.drawable.rabbit_rank)
        else if(rank<20)
            rankImage.setImageResource(R.drawable.deer_rank)
        else if(rank<20)
            rankImage.setImageResource(R.drawable.dolyak_rank)
        else
            rankImage.setImageResource(R.drawable.wolf_rank)
    }


}
