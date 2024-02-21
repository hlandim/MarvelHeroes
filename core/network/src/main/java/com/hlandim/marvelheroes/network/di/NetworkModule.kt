package com.hlandim.marvelheroes.network.di

import android.content.Context
import com.hlandim.marvelheroes.network.MarvelApi
import com.hlandim.marvelheroes.network.util.NetworkCheck
import com.hlandim.marvelheroes.network.util.SessionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMarvelApi(
        client: OkHttpClient,
    ) = Retrofit.Builder().baseUrl(MarvelApi.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create()).client(client).build()
        .create(MarvelApi::class.java)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(SessionInterceptor()).addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkCheck(@ApplicationContext context: Context): NetworkCheck =
        NetworkCheck(context)
}
