package com.hlandim.marvelheroes.network.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

class NetworkCheck(private val applicationContext: Context) {
    fun isOnline(): Boolean {
        val connMgr =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: Network = connMgr.activeNetwork ?: return false
        val capabilities = connMgr.getNetworkCapabilities(activeNetwork)
        return capabilities != null &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}
