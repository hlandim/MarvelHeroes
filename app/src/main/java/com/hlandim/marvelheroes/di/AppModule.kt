package com.hlandim.marvelheroes.di

import android.app.Application
import androidx.room.Room
import com.hlandim.marvelheroes.network.SessionInterceptor
import com.hlandim.marvelheroes.data.local.HeroDatabase
import com.hlandim.marvelheroes.data.remote.MarvelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by Hugo Santos on 20/09/2023.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMarvelApi(): MarvelApi {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(SessionInterceptor())
            .addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(MarvelApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client.build())
            .build()
            .create(MarvelApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHeroDatabase(app: Application): HeroDatabase {
        return Room.databaseBuilder(
            app,
            HeroDatabase::class.java,
            "herodb.db"
        ).build()
    }
}