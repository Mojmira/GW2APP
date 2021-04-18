package com.example.guildwars2app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CharacterAdapter(private val context: Context,
                        var dataSource: Array<CharacterDetails>): BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): CharacterDetails {
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

        val character = getItem(position)
        if( character !== null){
        nameView.text = character.name
        raceView.text = character.race
        genderView.text = character.gender
        levelView.text = character.level.toString()
        ageView.text = ConvertSecToDay(character.age)
            ChooseProfessionImg(character.profesion, profession)
        }
        else println("Character null")

        return rowView
    }

    fun ConvertSecToDay(n:Int):String
    {
        val day = n / (24*3600).toInt()
        val tmp = n % (24*3600)
        val hour = (tmp/3600).toInt()
        val tmp2  =tmp% 3600
        val minutes = tmp2/60
        val seconds = tmp2%60

        return ( "%d days %d hours %d minutes %d seconds").format(day,hour,minutes,seconds)


    }

    fun ChooseProfessionImg(prof:String, profView: ImageView)
    {
        println(prof)
        if(prof == "Mesmer")
            profView.setImageResource(R.drawable.mesmer_icon)
        else if (prof == "Revenant")
            profView.setImageResource(R.drawable.revenant_icon)
    }
}