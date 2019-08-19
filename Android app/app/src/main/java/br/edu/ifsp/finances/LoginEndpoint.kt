package br.edu.ifsp.finances

import br.edu.ifsp.finances.domain.Jwt
import br.edu.ifsp.finances.domain.Login
import br.edu.ifsp.finances.domain.SignUp
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginEndpoint {

    @POST("/auth/signin")
    fun login(@Body login : Login) : Call<Jwt>

    @POST("/auth/signup")
    fun signup(@Body signup : SignUp) : Call<ResponseBody>
}