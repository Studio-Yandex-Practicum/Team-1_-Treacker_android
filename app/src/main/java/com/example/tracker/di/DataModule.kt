package com.example.tracker.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.tracker.authorization.data.network.ApiClientAuthorization
import com.example.tracker.authorization.data.network.NetworkClientAuthorization
import com.example.tracker.authorization.data.network.RetrofitClientAuthorization
import com.example.tracker.registration.data.network.ApiClient
import com.example.tracker.registration.data.network.NetworkClient
import com.example.tracker.registration.data.network.RetrofitClient
import com.example.tracker.settings.data.api.SettingsStorage
import com.google.gson.Gson
import core.db.AppDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single<ApiClient> {
        Retrofit.Builder()
            .baseUrl("http://130.193.44.66:8080/")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiClient::class.java)
    }
    single<NetworkClient> {
        RetrofitClient(get())
    }
    single<ApiClientAuthorization> {
        Retrofit.Builder()
            .baseUrl("http://130.193.44.66:8080/")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiClientAuthorization::class.java)
    }
    single<NetworkClientAuthorization> {
        RetrofitClientAuthorization(get())
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    single {
        Room
            .databaseBuilder(androidContext(), AppDatabase::class.java, "tracker.db")
            .build()
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences("LocalStorage", MODE_PRIVATE)
    }

    factory { Gson() }

    single {
        SettingsStorage(androidContext().getSharedPreferences("SettingsStorage", MODE_PRIVATE))
    }

}
