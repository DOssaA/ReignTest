package com.darioossa.reignandroidtest.core.di

import android.content.Context
import com.darioossa.data.repository.PostsRepositoryImpl
import com.darioossa.domain.core.platform.LocalDB
import com.darioossa.domain.repository.PostsRepository
import com.darioossa.reignandroidtest.AndroidApp
import com.darioossa.reignandroidtest.core.platform.LocalDbImpl
import com.darioossa.reignandroidtest.core.platform.NetworkHandler
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApp) {

    @Provides @Singleton fun provideApplicationContext(): Context = application

    @Provides @Singleton fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://hn.algolia.com")
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        //if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        //}
        return okHttpClientBuilder.build()
    }

    @Provides @Singleton fun providesLocalDB(localDB: LocalDbImpl): LocalDB = localDB

    @Provides @Singleton fun providesNetworkHandler(networkHandler: NetworkHandler): com.darioossa.domain.core.platform.NetworkHandler = networkHandler

    @Provides @Singleton fun providePostsRepository(dataSource: PostsRepositoryImpl): PostsRepository = dataSource
}
