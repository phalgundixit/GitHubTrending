package com.phalgundixit.githubtrending.base.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class HeaderInterceptor @Inject constructor(@Named("authToken") val authToken: String) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("Authorization", authToken)
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
                .build()
        )
    }
}