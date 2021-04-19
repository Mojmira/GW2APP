package com.example.guildwars2app

import android.content.Context
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.guildwars2app.Adapters.AchievementAdapter
import com.example.guildwars2app.DataDetails.AchievementDetails
import org.json.JSONArray
import org.json.JSONObject

lateinit var listView: ListView
lateinit var queue: RequestQueue
private lateinit var achievements:Array<AchievementDetails>
internal lateinit var adapter: AchievementAdapter


class AchievementActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Daily Achievements"
        setContentView(R.layout.activity_achievements)
        listView = findViewById<ListView>(R.id.achievements_list_view)
        adapter = AchievementAdapter(this, emptyArray())
        listView.adapter = adapter
        getDailyIds(applicationContext)

    }

    fun getDailyIds(context: Context){

        val url = "https://api.guildwars2.com/v2/achievements/daily"


        queue = Volley.newRequestQueue(context)

        val dailyReq = JsonObjectRequest(
            Request.Method.GET,url, null,
            {
                    response ->
                println("Success!")
                prepareData(response, context)

            }, Response.ErrorListener {
                    error ->
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
                println(error.toString())}

        )
        queue.add(dailyReq)
    }

    private fun prepareData(response: JSONObject?, context: Context) {

        var IDs = IntArray(7)
        response?.let{

            val tmp = response.getJSONArray("pve")

            for(i in 0 until tmp.length())
            {
                IDs[i] = tmp.getJSONObject(i).getInt("id")
            }

        }

        var newUrl = "https://api.guildwars2.com/v2/achievements?ids=%d,%d,%d,%d,%d,%d".format(IDs[0],IDs[1],IDs[2],IDs[3],IDs[4],IDs[5],IDs[6])
        println(newUrl)
        loadAchievementData(newUrl, context)
    }

    private fun loadAchievementData(url: String, context: Context) {
        queue = Volley.newRequestQueue(context)

        val dailyReq = JsonArrayRequest(
            Request.Method.GET,url, null,
            {
                    response ->
                println("Success!")
                adapter.dataSource = processData(response, context)
                adapter.notifyDataSetChanged()

            }, Response.ErrorListener {
                    error ->
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
                println(error.toString())}

        )
        queue.add(dailyReq)

    }

    private fun processData(response: JSONArray, context: Context): Array<AchievementDetails> {


        response?.let{

            val size = response.length()
            var tmp = arrayOfNulls<AchievementDetails>(size)
            for(i in 0 until size){
                println(i)

                tmp[i] = AchievementDetails(
                        response.getJSONObject(i).getInt("id"),
                        response.getJSONObject(i).getString("name"),
                        response.getJSONObject(i).getString("description"),
                        response.getJSONObject(i).getString("requirement")
                )
            }
            return tmp as Array<AchievementDetails>
        }



    }
}