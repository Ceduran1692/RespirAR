package ar.edu.ort.respirar.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.entities.data.CustomEstation
import ar.edu.ort.respirar.fragments.StationListFragment

class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val itemImage: ImageView = itemView.findViewById(R.id.card_image)
    val itemTitle: TextView = itemView.findViewById(R.id.card_title)
    val itemDetails: TextView = itemView.findViewById(R.id.card_details)

    fun render(card:CustomEstation){
        itemImage.setImageDrawable(ContextCompat.getDrawable(this.itemView.context, card.image))
        itemTitle.text = card.titulo
        itemDetails.text = StationListFragment.getDetails(card)
    }
}