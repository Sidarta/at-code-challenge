package com.arctouch.codechallenge.util

import android.content.Context
import android.net.ConnectivityManager

object InternetUtils {

    /***
     * utils to verify internet availability
     */
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
