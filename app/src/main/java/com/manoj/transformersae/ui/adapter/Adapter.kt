package com.manoj.transformersae.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.manoj.transformersae.R
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.ui.MainActivity
import com.manoj.transformersae.util.AppUtill.bindImage
import kotlinx.android.synthetic.main.item_list_content.view.*

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
class Adapter (private val parentActivity: MainActivity, values: List<BotModel>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<Adapter.ViewHolder>() {

    private var listBots:ArrayList<BotModel> = ArrayList()

    init {
        listBots.addAll(values)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    fun updateList(list: List<BotModel>) {
        listBots = ArrayList()
        listBots.addAll(list)
        notifyDataSetChanged()
    }

    fun updateItem(botModel: BotModel) {
        var index = -1
        for (bot : BotModel in listBots) {
            index++
            if (bot.id == botModel.id) {
                break
            }
        }
        listBots.removeAt(index)
        notifyDataSetChanged()
    }

    fun removeItem(botModel: BotModel) {
        notifyDataSetChanged()
    }

    fun addItem(botModel: BotModel) {
        listBots.add(botModel)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listBots[position]
        holder.nameTextView.text = item.name
        holder.strengthTextView.text = getCriteriaText(item)

        bindImage(item.teamIcon, holder.iconImageView, false)
        holder.itemView.setOnClickListener(parentActivity)

        holder.iconEdit.setOnClickListener(parentActivity)
        holder.iconDelete.setOnClickListener(parentActivity)
        holder.iconEdit.setTag(R.id.item_list_layout, item.id)
        holder.iconDelete.setTag(R.id.item_list_layout, item.id)
        holder.itemView.setTag(R.id.item_list_layout, item.id)
        holder.iconImageView.setTag(R.id.item_list_layout, item.id)
    }

    private fun getCriteriaText(botModel: BotModel) : String {
        return parentActivity.getString(R.string.criteria_strength) + " "+ botModel.strength + ", " +
                parentActivity.getString(R.string.criteria_intelligence) + " "+ botModel.intelligence + ", " +
                parentActivity.getString(R.string.criteria_speed)+ " "+ botModel.speed + ", " +
                parentActivity.getString(R.string.criteria_endurance)+ " "+ botModel.endurance + ", " +
                parentActivity.getString(R.string.criteria_rank)+ " "+ botModel.rank + ", " +
                parentActivity.getString(R.string.criteria_courage)+ " "+ botModel.courage + ", " +
                parentActivity.getString(R.string.criteria_firepower)+ " "+ botModel.firepower + ", " +
                parentActivity.getString(R.string.criteria_skill)+ " "+ botModel.skill + ", " +
                parentActivity.getString(R.string.team)+ " "+ botModel.team
    }

    override fun getItemCount() = listBots.size

    inner class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val iconImageView:ImageView = view.image
        val iconEdit:ImageView = view.edit
        val iconDelete:ImageView = view.delete
        val nameTextView: TextView = view.name
        val strengthTextView: TextView = view.criteria
    }
}