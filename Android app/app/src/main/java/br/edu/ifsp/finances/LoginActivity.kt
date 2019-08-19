package br.edu.ifsp.finances

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.finances.domain.Jwt
import br.edu.ifsp.finances.domain.Login
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private val loginButton by lazy { findViewById<Button>(R.id.loginButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener{
            login()
        }

        signupButton.setOnClickListener{
            openSignup()
        }
    }

    fun login(){
        val user = userEdit.text.toString()
        val password = passwordEdit.text.toString()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.28.241.87:8080")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))

        retrofit.build().create(LoginEndpoint::class.java)
            .login(Login(user, password))
            .enqueue(object : retrofit2.Callback<Jwt> {
                override fun onFailure(call: Call<Jwt>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, getString(R.string.error_logging_in), Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<Jwt>, response: Response<Jwt>) {
                    val accessToken = response.body()?.accessToken
                    val sharedPref = getSharedPreferences("finances", Context.MODE_PRIVATE)
                    with(sharedPref.edit()){
                        putString("acess_token", accessToken)
                        apply()
                    }
                }

            })
    }
    fun openSignup(){
        startActivity(Intent(this, SignupActivity::class.java))
    }
}