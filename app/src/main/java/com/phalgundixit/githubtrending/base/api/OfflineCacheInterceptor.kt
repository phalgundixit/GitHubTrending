package com.phalgundixit.githubtrending.base.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineCacheInterceptor @Inject constructor(
    @ApplicationContext val context: Context
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        if (!IsInternetAvailable(context)) {
            val cacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxAge(120, TimeUnit.MINUTES)
                .build()
            builder.removeHeader("Pragma")
                .cacheControl(cacheControl)
            builder.cacheControl(CacheControl.FORCE_CACHE);
        }
        return chain.proceed(builder.build())

    }


    private fun IsInternetAvailable(context: Context): Boolean {
        var isConnected: Boolean = false // Initial Value
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}


