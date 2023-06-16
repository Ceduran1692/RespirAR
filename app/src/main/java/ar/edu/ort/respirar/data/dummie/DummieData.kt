package ar.edu.ort.respirar.data.dummie

import android.content.Context
import android.util.Log
import ar.edu.ort.respirar.R
import ar.edu.ort.respirar.domain.models.ItemAboutUs

class DummieData() {

    fun getItemsDummie():MutableList<ItemAboutUs>{

        Log.i("datos dummie","entre")
        //----------------/ABOUT US/---------------------------------
        var aboutUsDescription:String= "El proyecto RespirAR es una plataforma abierta que comprende el monitoreo de la calidad medioambiental a través de las distintas estaciones meteorológicas"
        //var ic:Int= R.drawable.ic_about_us_foreground
        var anim:Int= R.raw.anim_about_us

        //----------------/ABOUT US/---------------------------------
        var fiwareDescription:String= "FIWARE es una plataforma abierta y estandarizada que facilita el desarrollo de aplicaciones y servicios inteligentes en el ámbito de las ciudades inteligentes, la industria 4.0 y el Internet de las Cosas. Su enfoque en la interoperabilidad, la reutilización de componentes y la escalabilidad la convierten en una opción atractiva para los desarrolladores que buscan construir soluciones innovadoras y eficientes"
        //var icFiware:Int= R.drawable.ic_fiware_foreground

        //----------------/ABOUT US/---------------------------------

        var ciudadesDescription:String= "Es una iniciativa del INSTITUTO CIUDADES DEL FUTURO, un organismo que acompaña a las comunidades locales de Argentina y Latinoamérica en el diseño e implementación de acciones novedosas para un desarrollo humano, apoyándose en nuevas tecnologías y modelos de gestión"
        //var icCiudades:Int= R.drawable.ic_ciudades_del_futuro_foreground

        Log.i("datos dummie","sali")
        return mutableListOf(
            //ItemAboutUs("about us",aboutUsDescription ,ic,null,anim),
            //ItemAboutUs("fiware", fiwareDescription,icFiware,icFiware,null),
            //ItemAboutUs("ciudades del futuro", ciudadesDescription,icCiudades,icCiudades,null)
            )

    }
}