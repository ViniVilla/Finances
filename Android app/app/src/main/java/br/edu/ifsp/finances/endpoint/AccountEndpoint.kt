package br.edu.ifsp.finances.endpoint

import br.edu.ifsp.finances.domain.request.AccountRequest
import br.edu.ifsp.finances.domain.response.AccountResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AccountEndpoint {

    @GET("accounts")
    fun get(@Header("Authorization") token : String) : Call<List<AccountResponse>>

    @POST("accounts")
    fun create(@Header("Authorization") token : String, @Body account : AccountRequest) : Call<ResponseBody>

    @DELETE("accounts/{id}")
    fun delete(@Header("Authorization") token : String, @Path("id") id : Long) : Call<ResponseBody>

    @PUT("accounts/{id}")
    fun update(@Header("Authorization") token : String, @Body account : AccountRequest, @Path("id") id : Long) : Call<ResponseBody>
}