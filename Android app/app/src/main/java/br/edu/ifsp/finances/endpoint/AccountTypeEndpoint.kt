package br.edu.ifsp.finances.endpoint

import br.edu.ifsp.finances.domain.request.AccountTypeRequest
import br.edu.ifsp.finances.domain.response.AccountTypeResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AccountTypeEndpoint {

    @GET("account-types")
    fun get(@Header("Authorization") token : String) : Call<List<AccountTypeResponse>>

    @POST("account-types")
    fun create(@Header("Authorization") token : String, @Body accountType : AccountTypeRequest) : Call<ResponseBody>

    @DELETE("account-types/{id}")
    fun delete(@Header("Authorization") token : String, @Path("id") id : Long) : Call<ResponseBody>

    @PUT("account-types/{id}")
    fun update(@Header("Authorization") token : String, @Body accountType : AccountTypeRequest, @Path("id") id : Long) : Call<ResponseBody>
}