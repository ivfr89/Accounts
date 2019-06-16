package com.fernandez.players.data.server

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import java.io.IOException


interface ApiService {


    companion object Factory {

        const val ACCOUNTS_ENDPOINT = "accounts"


    }

    @retrofit2.http.GET(ACCOUNTS_ENDPOINT) fun getAccounts(): Call<String>


}


//Header interceptor for debug purposes

class HeaderInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder: Request.Builder


        requestBuilder = request.newBuilder()
            .addHeader("Content-Type", "application/json")

        val response = chain.proceed(requestBuilder.build())


        val body = ResponseBody.create(response.body()?.contentType(), response.body()!!.string())
        return response.newBuilder().body(body).build()
    }
}

