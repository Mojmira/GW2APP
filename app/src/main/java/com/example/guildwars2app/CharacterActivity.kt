package com.example.guildwars2app

import android.content.Context
import android.content.SharedPreferences
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
import com.example.guildwars2app.Adapters.CharacterAdapter
import com.example.guildwars2app.DataDetails.CharacterDetails
import org.json.JSONArray
import org.json.JSONObject

class CharacterActivity : AppCompatActivity() {

    lateinit var listView: ListView
    internal lateinit var sharedPref: SharedPreferences
    lateinit var queue: RequestQueue
    private lateinit var characterNames:Array<String>
    private lateinit var characters:Array<CharacterDetails>
    lateinit var ApiKey:String
    internal lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Characters"
        setContentView(R.layout.activity_characters)
        listView = findViewById<ListView>(R.id.character_list_view)
        sharedPref = getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE)
        ApiKey = sharedPref.getString(getString(R.string.shared_api_name), "No key").toString()
        adapter = CharacterAdapter(this, emptyArray())
        listView.adapter = adapter


        getCharacters(applicationContext)


    }

    fun getCharacters(context: Context){

        val url = "https://api.guildwars2.com/v2/characters?access_token=%s".format(ApiKey)
        println(url)

        queue = Volley.newRequestQueue(context)

        val charactersList = JsonArrayRequest(
            Request.Method.GET,url, null,
            {
                    response ->
                println("Success!")
                loadCharacterData(response, context)

            }, Response.ErrorListener {
                    error ->
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
                println(error.toString())}

        )
        queue.add(charactersList)
    }

    private fun loadCharacterData(response: JSONArray?, context: Context) {

        response?.let{

            val namesCount = response.length()
            val tmpData = arrayOfNulls<String>(namesCount)
            for(i in 0 until namesCount){
                val date = response.getString(i).toString()


                tmpData[i] = date
                println(date)
            }
            characterNames = tmpData as Array<String>

        }

        val tmpCharacters = arrayOfNulls<CharacterDetails>(characterNames.size)
        for(i in 0 until characterNames.size) {
            val character = characterNames.get(i)
            val url = "https://api.guildwars2.com/v2/characters/%s?access_token=%s".format(character,ApiKey)
            queue = Volley.newRequestQueue(context)

            val characterResponse = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    println(character)
                    adapter.dataSource = adapter.dataSource + loadCharacterDetails(response, i)
                    adapter.notifyDataSetChanged()

                }, Response.ErrorListener { error ->
                    Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
                    println(error.toString())
                }

            )
            queue.add(characterResponse)
            characters = tmpCharacters as Array<CharacterDetails>


        }


    }

    private fun loadCharacterDetails(response: JSONObject?, index:Int): CharacterDetails {

        response?.let{

            val tmpCharacter = CharacterDetails(

                    response.getString("name").toString(),
                    response.getString("race").toString(),
                    response.getString("gender").toString(),
                    response.getString("profession").toString(),
                    response.getString("level").toString().toInt(),
                    response.getString("age").toString().toInt(),
                    response.getString("created").toString()
            )

            return tmpCharacter
            }

        return CharacterDetails("error", "error", "error", "error", 0, 0, "error")
    }

}
