package ar.edu.ort.respirar.ui.views.holders

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.domain.models.CustomStation

class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
    //val itemImage: ImageView = view.findViewById(R.id.card_image)
    val itemTitle: TextView = view.findViewById(R.id.card_title)
    val viewPager: ViewPager2 = itemView.findViewById(R.id.viewPager2StationCard)
    var favoriteButton: CheckBox = itemView.findViewById(R.id.favoriteButton)

    private var currentStation: CustomStation? = null
    fun render(card: CustomStation){
        //itemImage.setImageDrawable(ContextCompat.getDrawable(this.itemView.context, card.image))
        currentStation = card
        itemTitle.text = card.titulo
    }

    fun setItemClickListener(listener: (CustomStation?) -> Unit) {
        itemView.setOnClickListener {
            listener.invoke(currentStation)
        }
    }

}