package com.example.guildwars2app

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.doOnTextChanged
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.guildwars2app.R
import com.github.mikephil.charting.charts.LineChart
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_exchange.*

class GemExchangeActivity : AppCompatActivity(){
    private lateinit var form: LinearLayout
    private lateinit var coinCurrencyInput: EditText
    private lateinit var gemCurrencyInput: EditText
    var gemValue : Float = 0.0F
    var coinValue : Float = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange)

        currency_top_value.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                currency_bottom_value.setText(calculateValue(coinValue, s as String).toString())
            }
        })



        form = findViewById(R.id.form_container)
        coinCurrencyInput = findViewById(R.id.currency_top_value)
        gemCurrencyInput = findViewById(R.id.currency_bottom_value)
        getRates(applicationContext)
    }

    fun getRates(context: Context){

        val url = "https://api.guildwars2.com/v2/commerce/exchange/coins?quantity=100000"
        queue = Volley.newRequestQueue(context)

        val req = JsonObjectRequest( Request.Method.GET,url, null,
                { response ->
                    println("Done")
                    loadData(response)
                }, Response.ErrorListener{
            error ->
            Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_LONG).show()
            println(error.toString())}

        )
        queue.add(req)
    }

    private fun loadData(response: JSONObject){
        response?.let{
            gemValue = response.getString("coins_per_gem").toFloat()
            coinValue = 1/gemValue
        }
    }

    fun calculateValue(what: Float,text : String) :Float{
        return text.toFloat()*what
    }
}