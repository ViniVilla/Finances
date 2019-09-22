package br.edu.ifsp.finances.endpoint

import br.edu.ifsp.finances.domain.request.TransactionRequest
import br.edu.ifsp.finances.domain.response.TransactionResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface TransactionEndpoint {

    @GET("transactions")
    fun get(@Header("Authorization") token : String) : Call<List<TransactionResponse>>

    @POST("transactions")
    fun create(@Header("Authorization") token : String, @Body transaction : TransactionRequest) : Call<ResponseBody>

    @DELETE("transactions/{id}")
    fun delete(@Header("Authorization") token : String, @Path("id") id : Long) : Call<ResponseBody>

    @PUT("transactions/{id}")
    fun update(@Header("Authorization") token : String, @Body transaction : TransactionRequest, @Path("id") id : Long) : Call<ResponseBody>
}