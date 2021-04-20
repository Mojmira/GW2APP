package com.example.guildwars2app

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_exchange.*
import org.json.JSONObject


class GemExchangeActivity : AppCompatActivity(){
    private lateinit var form: LinearLayout
    private lateinit var coinCurrencyInput: EditText
    private lateinit var gemCurrencyInput: EditText
    var change_coin_active : Boolean = false
    var change_gem_active : Boolean = false
    var gemValue : Float = 0.0F
    var coinValue : Float = 0.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange)
        form = findViewById(R.id.form_container)
        coinCurrencyInput = findViewById(R.id.currency_top_value)
        gemCurrencyInput = findViewById(R.id.currency_bottom_value)

        currency_top_value.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){
                if (change_coin_active){
                    var number = 0.0F
                    if(s.toString().isNotEmpty()){
                        try {
                            number = s?.toString().toFloat()
                        }catch (e :ArithmeticException){
                            println(e)
                        }
                    }
                    currency_bottom_value.setText(calculateValue(coinValue, number).toString())
                }
            }
        })

        currency_top_value.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            change_coin_active = hasFocus
        }

        currency_bottom_value.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            change_gem_active = hasFocus
        }

        currency_bottom_value.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){
                if(change_gem_active){
                    var number = 0.0F
                    if(s.toString().isNotEmpty()){
                        try {
                            number = s?.toString().toFloat()
                        }catch (e :ArithmeticException){
                            println(e)
                        }
                    }
                    currency_top_value.setText(calculateValue(gemValue, number).toString())
                }

            }
        })


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
        response.let{
            gemValue = response.getString("coins_per_gem").toFloat()
            coinValue = 1/gemValue
        }
    }

    fun calculateValue(what: Float,text : Float) :Float{
        return text*what
    }
}