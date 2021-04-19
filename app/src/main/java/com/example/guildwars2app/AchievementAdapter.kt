package com.example.guildwars2app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AchievementAdapter(private val context: Context,
                         var dataSource: Array<AchievementDetails>): BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): AchievementDetails {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.achievement_row, parent, false)
        val nameView = rowView.findViewById(R.id.achNameView) as TextView
        val descView = rowView.findViewById(R.id.achDescView) as TextView
        val reqView = rowView.findViewById(R.id.achReqView) as TextView

        val achievement = getItem(position)
        if(achievement !==null){
            nameView.text = achievement.name
            descView.text = achievement.description
            reqView.text = achievement.requirement
        }


        return rowView
    }




}