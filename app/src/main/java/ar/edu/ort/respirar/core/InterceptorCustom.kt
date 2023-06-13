package ar.edu.ort.respirar.core

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

object InterceptorCustom: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.i("InterceptorCustom", "intercept(Chain) -in")
        var request= chain.request()

        request= request.newBuilder()
            .header("Accept","application/json")
            .header("Content-Type","application/json")
            .build()

        Log.i("InterceptorCustom", "intercept(Chain) -in")
        return chain.proceed(request)
    }

}