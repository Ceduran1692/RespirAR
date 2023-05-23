package ar.edu.ort.respirar.holders

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.data.models.CustomEstation

class CustomViewHolder(view: View): RecyclerView.ViewHolder(view) {
    //val itemImage: ImageView = view.findViewById(R.id.card_image)
    val itemTitle: TextView = view.findViewById(R.id.card_title)
    val itemTemperature: TextView = view.findViewById(R.id.card_temperature)
    val itemHumidity: TextView = view.findViewById(R.id.card_humidity)
    var favoriteButton: CheckBox = itemView.findViewById(R.id.favoriteButton)


    fun render(card: CustomEstation){
        //itemImage.setImageDrawable(ContextCompat.getDrawable(this.itemView.context, card.image))
        itemTitle.text = card.titulo
        itemTemperature.text = card.temperatura.toString()
        itemHumidity.text = card.humedad.toString()
    }
}