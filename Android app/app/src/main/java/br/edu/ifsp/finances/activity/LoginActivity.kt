package br.edu.ifsp.finances.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.finances.R
import br.edu.ifsp.finances.domain.request.LoginRequest
import br.edu.ifsp.finances.domain.response.Jwt
import br.edu.ifsp.finances.endpoint.LoginEndpoint
import br.edu.ifsp.finances.utils.retrofitBuilder
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val loginButton by lazy { findViewById<Button>(R.id.loginButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton.setOnClickListener{
            loginAct()
        }

        signupButton.setOnClickListener{
            openSignup()
        }

        val sharedPref = this.getSharedPreferences("finances", Context.MODE_PRIVATE)
        val user = sharedPref.getString("login", "")
        if(user != ""){
            login()
        }
    }

    fun loginAct(){
        val user = userEdit.text.toString()
        val password = passwordEdit.text.toString()

        val sharedPref = getSharedPreferences("finances", Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putString("login", user)
            putString("password", password)
            apply()
        }

        login()

        startActivity(Intent(this, DrawerActivity::class.java))
    }
    fun openSignup(){
        startActivity(Intent(this, SignupActivity::class.java))
    }

    fun login() {
        val sharedPref = this.getSharedPreferences("finances", Context.MODE_PRIVATE)
        val user = sharedPref.getString("login", "")
        val password = sharedPref.getString("password", "")


        val retrofit = retrofitBuilder()

        retrofit.build().create(LoginEndpoint::class.java)
            .login(LoginRequest(user, password))
            .enqueue(object : Callback<Jwt> {
                override fun onFailure(call: Call<Jwt>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, getString(R.string.error_logging_in), Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<Jwt>, response: Response<Jwt>) {
                    if(response.code() == 200){
                        val accessToken = response.body()?.accessToken
                        with(sharedPref.edit()){
                            putString("access_token", accessToken)
                            apply()
                            startActivity(Intent(this@LoginActivity, DrawerActivity::class.java))
                        }
                    }
                    else{
                        val toast = Toast.makeText(applicationContext, getString(R.string.error_logging_in), Toast.LENGTH_LONG)
                        toast.show()
                    }
                }
            })
    }
}