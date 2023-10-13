package com.hlandim.marvelheroes.network.di

import com.hlandim.marvelheroes.network.MarvelApi
import com.hlandim.marvelheroes.network.util.NetworkCheck
import com.hlandim.marvelheroes.network.util.SessionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule: Module = module {
    single<MarvelApi> {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(SessionInterceptor())
            .addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(MarvelApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client.build())
            .build()
            .create(MarvelApi::class.java)
    }

    single<NetworkCheck> { NetworkCheck(get()) }
}
