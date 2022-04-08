package ui.smartpro.sequenia.data.api

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class ApiHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val request = originalRequest.newBuilder()
            .url(originalHttpUrl)
            .build()
        Timber.d(request.toString())
        return chain.proceed(request)
    }
}