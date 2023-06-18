package ar.edu.ort.respirar.ui.views.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.domain.models.ItemAboutUs
import com.airbnb.lottie.LottieAnimationView

data class ItemAboutUsHolder(private val view: View):RecyclerView.ViewHolder(view) {

    val title= view.findViewById<TextView>(R.id.tv_title_about_us)
    val description= view.findViewById<TextView>(R.id.tv_description_about_us)
    val icAboutUs= view.findViewById<ImageView>(R.id.ic_info_about_us)
    val ivAboutUs= view.findViewById<ImageView>(R.id.iv_info_about_us)!!
    val animAboutUs= view.findViewById<LottieAnimationView>(R.id.anim_info_about_us)!!

    fun render(item: ItemAboutUs) {
        title.text= item.title
        description.text= item.description
        icAboutUs.setImageDrawable(ContextCompat.getDrawable(view.context,item.icon))
        if(item.expandAnim != null) animAboutUs.setAnimation(item.expandAnim)
        if(item.expandIcon != null) ivAboutUs.setImageDrawable(ContextCompat.getDrawable(view.context,item.expandIcon))
    }

}
