package br.edu.ifsp.finances.endpoint

import br.edu.ifsp.finances.domain.request.TransactionCategoryRequest
import br.edu.ifsp.finances.domain.response.TransactionCategoryResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface TransactionCategoryEndpoint {

    @GET("transaction-categories")
    fun get(@Header("Authorization") token : String) : Call<List<TransactionCategoryResponse>>

    @POST("transaction-categories")
    fun create(@Header("Authorization") token : String, @Body category : TransactionCategoryRequest) : Call<ResponseBody>

    @DELETE("transaction-categories/{id}")
    fun delete(@Header("Authorization") token : String, @Path("id") id : Long) : Call<ResponseBody>

    @PUT("transaction-categories/{id}")
    fun update(@Header("Authorization") token : String, @Body category : TransactionCategoryRequest, @Path("id") id : Long) : Call<ResponseBody>
}