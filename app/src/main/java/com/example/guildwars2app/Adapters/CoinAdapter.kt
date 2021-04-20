package com.example.guildwars2app.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.guildwars2app.DataDetails.CharacterDetails
import com.example.guildwars2app.DataDetails.CoinDetails
import com.example.guildwars2app.R

class CoinAdapter(private val context: Context, var dataSource: Array<CoinDetails>): BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): CoinDetails {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.coin_row, parent, false)
        val nameView = rowView.findViewById(R.id.CoinName) as TextView
        val descView = rowView.findViewById(R.id.CoinDescription) as TextView
        val reqView = rowView.findViewById(R.id.CoinValue) as TextView

        val coin = getItem(position)
        if(coin !==null){
            nameView.text = coin.name
            descView.text = coin.description
            reqView.text = coin.value.toString()
        }


        return rowView
    }




}