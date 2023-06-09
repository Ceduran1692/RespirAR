package ar.edu.ort.respirar.di

import ar.edu.ort.respirar.core.Config
import ar.edu.ort.respirar.core.InterceptorCustom
import ar.edu.ort.respirar.data.service.interfaces.StationServiceInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val interceptor: HttpLoggingInterceptor= HttpLoggingInterceptor().apply {
        level= HttpLoggingInterceptor.Level.BODY
    }

    var client: OkHttpClient= OkHttpClient.Builder().apply {
        addInterceptor(interceptor).addInterceptor(InterceptorCustom)
    }.build()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val baseUrl= Config.orionUrl

        client.newBuilder().addNetworkInterceptor(Interceptor { chain ->
            val request= chain.request()
                .newBuilder()
                .build()

            chain.proceed(request)
        })

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Singleton
    @Provides
    fun provideCarApiClient(retrofit: Retrofit): StationServiceInterface {
        return retrofit.create(StationServiceInterface::class.java)
    }
}