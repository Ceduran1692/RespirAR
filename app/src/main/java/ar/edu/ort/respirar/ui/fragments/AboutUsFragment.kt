package ar.edu.ort.respirar.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import ar.edu.ort.respirar.adapters.ItemsAboutUsAdapter
import ar.edu.ort.respirar.databinding.FragmentAboutUsBinding
import ar.edu.ort.respirar.data.dummie.DummieData
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.card.MaterialCardView


class AboutUsFragment : Fragment() {

    private var _binding: FragmentAboutUsBinding? = null

    //---------------/ Test data/------------------------------------
    private var itemsList=  DummieData().getItemsDummie()


    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        Log.i("about us fragment", "on create")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i("about us fragment", "on create view")
        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        val view = binding.root
        initComponents()
        //val linearLayoutManager= LinearLayoutManager(context)

        /*binding.rvItemsAboutUs.adapter= ItemsAboutUsAdapter(itemsList)
        binding.rvItemsAboutUs.layoutManager= linearLayoutManager*/
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initComponents(){

        setCardViewListener(binding.cardAboutUs,binding.aboutUsTextDescription,binding.imgInfoAboutUs,binding.icInfoAboutUs,null)
        setCardViewListener(binding.cardFyware,binding.TextDescriptionFyware,binding.imgInfoFyware,binding.icInfoFyware,null)
        setCardViewListener(binding.cardCiudadesDelFuturo,binding.TextDescriptionCiudadesDelFuturo,binding.imgInfoCiudadesDelFuturo,binding.icInfoCiudadesDelFuturo,null)

        /* val linearLayoutManager= LinearLayoutManager(context)

         binding.rvItemsAboutUs.adapter= ItemsAboutUsAdapter(itemsList)
         binding.rvItemsAboutUs.layoutManager= linearLayoutManager


         binding.cardAboutUs.setOnClickListener {
            if(binding.aboutUsTextDescription.visibility == View.GONE) {
                Log.i("cardPress","expando")
                TransitionManager.beginDelayedTransition(binding.cardAboutUs, AutoTransition())
                binding.icInfoAboutUs.setVisibility(View.GONE)
                binding.animInfoAboutUs.setVisibility(View.VISIBLE)
                binding.aboutUsTextDescription.setVisibility(View.VISIBLE)
            }else{
                Log.i("cardPress","contraigo")
                TransitionManager.beginDelayedTransition(binding.cardAboutUs, AutoTransition())
                binding.icInfoAboutUs.setVisibility(View.VISIBLE)
                binding.animInfoAboutUs.setVisibility(View.GONE)
                binding.aboutUsTextDescription.setVisibility(View.GONE)
            }
         }*/
    }




    private fun setCardViewListener(card: CardView, textDescription: LinearLayout, imgViewExpand: ImageView?, imgViewContract:ImageView, anim: LottieAnimationView?){
        card.setOnClickListener {
            if(imgViewContract.visibility == View.VISIBLE) {
                Log.i("cardPress","expando")
                TransitionManager.beginDelayedTransition(binding.cardAboutUs, AutoTransition())
                imgViewContract.setVisibility(View.GONE)
                if (imgViewExpand != null) imgViewExpand.setVisibility(View.VISIBLE)
                if(anim != null) anim.setVisibility(View.VISIBLE)
                textDescription.setVisibility(View.VISIBLE)
            }else{
                Log.i("cardPress","contraigo")
                TransitionManager.beginDelayedTransition(binding.cardAboutUs, AutoTransition())
                imgViewContract.setVisibility(View.VISIBLE)
                if (imgViewExpand != null) imgViewExpand.setVisibility(View.GONE)
                if(anim != null) anim.setVisibility(View.GONE)
                textDescription.setVisibility(View.GONE)
            }
        }
    }


}