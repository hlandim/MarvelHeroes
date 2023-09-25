package com.hlandim.marvelheroes.network

import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest
import java.util.Date

/**
 * Created by Hugo Santos on 20/09/2023.
 */

class SessionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val ts: String = Date().time.toString()
        val url = chain.request().url.newBuilder()
            .addQueryParameter("ts", ts)
            .addQueryParameter("apikey", BuildConfig.MARVEL_PUBLIC_API_KEY)
            .addQueryParameter("hash", getHashMd5(ts))
            .build()
        val request = chain.request().newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }

    private fun getHashMd5(timeStamp: String): String {
        val bodyHash =
            "$timeStamp${BuildConfig.MARVEL_PRIVATE_API_KEY}${BuildConfig.MARVEL_PUBLIC_API_KEY}"
        return bodyHash.md5()
    }

    @Suppress("ImplicitDefaultLocale")
    private fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digested = md.digest(toByteArray())
        return digested.joinToString("") {
            String.format("%02x", it)
        }
    }

}