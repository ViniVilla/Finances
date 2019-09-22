package br.edu.ifsp.finances.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.finances.R
import br.edu.ifsp.finances.domain.request.SignUpRequest
import br.edu.ifsp.finances.endpoint.LoginEndpoint
import br.edu.ifsp.finances.utils.retrofitBuilder
import kotlinx.android.synthetic.main.activity_signup.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupButton.setOnClickListener{
            signup()
        }
    }

    fun signup() {
        val name = nameEdit.text.toString()
        val username = userEdit.text.toString()
        val email = emailEdit.text.toString()
        val password = passwordEdit.text.toString()

        val retrofit = retrofitBuilder()

        retrofit.build().create(LoginEndpoint::class.java)
            .signup(SignUpRequest(name, username, email, password))
            .enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, getString(R.string.error_creating_account), Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val toast = Toast.makeText(applicationContext, getString(R.string.account_created), Toast.LENGTH_LONG)
                    toast.show()
                    finish()
                }

            })
    }
}
