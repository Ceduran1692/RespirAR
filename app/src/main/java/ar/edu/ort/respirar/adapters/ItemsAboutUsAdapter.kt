package ar.edu.ort.respirar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.data.models.ItemAboutUs
import ar.edu.ort.respirar.holders.ItemAboutUsHolder

class ItemsAboutUsAdapter(
    val  itemList: MutableList<ItemAboutUs>
    ): RecyclerView.Adapter<ItemAboutUsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAboutUsHolder {
       val view= LayoutInflater.from(parent.context).inflate(R.layout.card_about_us_layout,parent,false)
        return ItemAboutUsHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemAboutUsHolder, position: Int) {
        var item= itemList[position]

        holder.render(item)
    }
}