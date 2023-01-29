package com.phalgundixit.githubtrending.base.di

import android.content.Context
import com.phalgundixit.githubtrending.base.api.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

private const val CACHE_SIZE = 5 * 1024 * 1024L // 5 MB

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = LoggingInterceptor.create()

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor,
        cacheInterceptor: CacheInterceptor,
        offlineCacheInterceptor: OfflineCacheInterceptor,
        cache: Cache
    ): OkHttpClient {
        return HttpClient.setupOkhttpClient(
            httpLoggingInterceptor,
            headerInterceptor,
            cacheInterceptor,
            offlineCacheInterceptor,
            cache
        )
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
    fun provideAuthToken(): String = "Add Your Key Here"

    @Provides
    fun provideCachingInterceptor(): CacheInterceptor = CacheInterceptor()

    @Provides
    fun provideHttpCache(@ApplicationContext context: Context
    ): Cache {
        return Cache(context.cacheDir, CACHE_SIZE)
    }

}