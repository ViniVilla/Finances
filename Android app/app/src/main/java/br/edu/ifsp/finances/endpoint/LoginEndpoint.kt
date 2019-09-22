package br.edu.ifsp.finances.endpoint

import br.edu.ifsp.finances.domain.User
import br.edu.ifsp.finances.domain.request.LoginRequest
import br.edu.ifsp.finances.domain.request.SignUpRequest
import br.edu.ifsp.finances.domain.response.Jwt
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginEndpoint {

    @POST("/auth/signin")
    fun login(@Body loginRequest : LoginRequest) : Call<Jwt>

    @POST("/auth/signup")
    fun signup(@Body signup : SignUpRequest) : Call<ResponseBody>

    @GET("auth/me")
    fun getUser(@Header("Authorization") token : String) : Call<User>
}