package com.phalgundixit.githubtrending.base.di

import com.phalgundixit.githubtrending.base.api.GithubApi
import com.phalgundixit.githubtrending.base.api.HeaderInterceptor
import com.phalgundixit.githubtrending.base.api.HttpClient
import com.phalgundixit.githubtrending.base.api.LoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = LoggingInterceptor.create()

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        return HttpClient.setupOkhttpClient(httpLoggingInterceptor, headerInterceptor)
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, @Named("baseUrl") baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTrendingApi(retrofit: Retrofit): GithubApi =
        retrofit.create(GithubApi::class.java)

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String = "https://api.github.com/"

    @Provides
    @Named("authToken")
    fun provideAuthToken(): String = "Add Your Token Here"
}