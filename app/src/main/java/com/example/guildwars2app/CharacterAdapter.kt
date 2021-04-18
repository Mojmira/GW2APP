package com.example.guildwars2app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CharacterAdapter(private val context: Context,
                       private val dataSource: Array<CharacterDetails>): BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.character_row, parent, false)
        val nameView = rowView.findViewById(R.id.nameView) as TextView
        val raceView = rowView.findViewById(R.id.raceView) as TextView
        val genderView = rowView.findViewById(R.id.genderView) as TextView
        val levelView = rowView.findViewById(R.id.levelView7) as TextView
        val ageView = rowView.findViewById(R.id.ageView) as TextView
        val profession = rowView.findViewById(R.id.professionView) as ImageView

        val character = getItem(position) as CharacterDetails

        return rowView
    }
}