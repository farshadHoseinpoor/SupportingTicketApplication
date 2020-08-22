package com.example.ticketmodule.api

import android.util.Log
import com.example.ticketmodule.model.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("ticket-head")
    fun getTicketHead(@Query("user_id") userID: Int): Call<HttpGenericResponse>

    @GET("ticket-details")
    fun getTicketDetail(@Query("ticket_id") ticket_id: Int): Call<HttpGenericResponse>


    @FormUrlEncoded
    @POST("new-ticket")
    fun createTicket(
        @Field("user_id") userId: Int,
        @Field("subject") subject: String,
        @Field("body") body: String
    ): Call<HttpGenericResponse>

    @FormUrlEncoded
    @POST("new-ticket-detail")
    fun createTicketDetail(
        @Field("user_id") userId: Int,
        @Field("ticket_id") ticketId: Int,
        @Field("body") body: String
    ): Call<HttpGenericResponse>

    companion object {

        const val BASE_URL = "http://93.118.100.44:3370/ticket/api/"

        fun create(): ApiService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

        }
    }
}

fun getTicketHeadApi(
    userID: Int,
    apiService: ApiService,
    onSuccess: (tickets: ArrayList<TicketHead>?) -> Unit
) {

    apiService.getTicketHead(userID).enqueue(object : Callback<HttpGenericResponse> {
        override fun onResponse(
            call: Call<HttpGenericResponse>,
            response: Response<HttpGenericResponse>
        ) {
            Log.d("ticketHead", response.body().toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess(Gson().fromJson(it.data, GetTicketHeads::class.java).ticket_heads)
                }
            } else {
                onSuccess(null)
            }
        }

        override fun onFailure(call: Call<HttpGenericResponse>, t: Throwable) {
            Log.d("ticketHead", "onFailure")
            onSuccess(null)

        }

    })
}

fun getTicketDetailApi(
    ticket_id: Int,
    apiService: ApiService,
    onSuccess: (details: ArrayList<TicketDetail>?) -> Unit
) {
    apiService.getTicketDetail(ticket_id).enqueue(object : Callback<HttpGenericResponse> {

        override fun onResponse(
            call: Call<HttpGenericResponse>,
            response: Response<HttpGenericResponse>
        ) {
            Log.d("ticketDetail", response.body().toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess(Gson().fromJson(it.data, GetTicketDetail::class.java).ticket_details)
                }

            } else onSuccess(null)

        }

        override fun onFailure(call: Call<HttpGenericResponse>, t: Throwable) {
            Log.d("ticketDetail", "onFailure")
            onSuccess(null)

        }

    })
}


fun createNewTicketApi(
    userID: Int,
    subject: String,
    body: String,
    apiService: ApiService,
    onSuccess: (texts: String) -> Unit
) {
    apiService.createTicket(userID, subject, body).enqueue(object : Callback<HttpGenericResponse> {

        override fun onResponse(
            call: Call<HttpGenericResponse>,
            response: Response<HttpGenericResponse>
        ) {
            Log.d("createTicket", response.body().toString())
            if (response.isSuccessful) {
                response.body()?.let {
                    onSuccess(it.message)
                }
            } else {
                onSuccess("")
            }
        }

        override fun onFailure(call: Call<HttpGenericResponse>, t: Throwable) {
            Log.d("createTicket", "onFailure")
            onSuccess("")
        }

    })


}


fun createTicketDetailApi(
    userId: Int,
    ticketId: Int,
    body: String,
    apiService: ApiService,
    onSuccess: (texts: String) -> Unit
) {
    apiService.createTicketDetail(userId, ticketId, body)
        .enqueue(object : Callback<HttpGenericResponse> {

            override fun onResponse(
                call: Call<HttpGenericResponse>,
                response: Response<HttpGenericResponse>
            ) {
                Log.d("createTicketDetail", response.body().toString())
                if (response.isSuccessful) {
                    response.body()?.let {
                        onSuccess(it.message)

                    }
                } else {
                    onSuccess("")
                }

            }

            override fun onFailure(call: Call<HttpGenericResponse>, t: Throwable) {
                Log.d("createTicketDetail", "onFailure")
                onSuccess("")

            }
        })
}
