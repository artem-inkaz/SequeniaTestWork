package ui.smartpro.sequenia.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://s3-eu-west-1.amazonaws.com/"

object RetrofitModule {
    private val duration = 10000L
    private val client = OkHttpClient().newBuilder()
        .connectTimeout(duration, TimeUnit.MILLISECONDS)
        .readTimeout(duration, TimeUnit.MILLISECONDS)
        .writeTimeout(duration, TimeUnit.MILLISECONDS)
        .addInterceptor(ApiHeaderInterceptor())
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    val apiClient: Api by lazy {

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return@lazy retrofit.create(Api::class.java)
    }
}
