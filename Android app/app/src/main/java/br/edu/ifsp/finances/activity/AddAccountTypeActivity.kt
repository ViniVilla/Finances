package br.edu.ifsp.finances.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.finances.R
import br.edu.ifsp.finances.domain.request.AccountTypeRequest
import br.edu.ifsp.finances.domain.response.AccountTypeResponse
import br.edu.ifsp.finances.endpoint.AccountTypeEndpoint
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import kotlinx.android.synthetic.main.activity_add_account_type.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAccountTypeActivity : AppCompatActivity() {
    private lateinit var accountType : AccountTypeResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account_type)
        supportActionBar?.title = "Add Account Type"

        button.setOnClickListener{create()}


        if(intent.getIntExtra("position", -1) != -1){
            configureUpdate()
            button.setOnClickListener{update()}
        }
    }

    private fun update() {
        val request = AccountTypeRequest(typeEdit.text.toString())

        val retrofit = retrofitBuilder()
        val token = getToken(this)

        retrofit.build().create(AccountTypeEndpoint::class.java)
            .update(token, request, accountType.id)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, "Error while creating account type", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val toast = Toast.makeText(applicationContext, "Account type created", Toast.LENGTH_LONG)
                    toast.show()
                    finish()
                }

            })
    }

    fun configureUpdate(){
        accountType = intent.getParcelableExtra("accountType")
        typeEdit.setText(accountType.name)
    }

    fun create(){
        val request = AccountTypeRequest(typeEdit.text.toString())

        val retrofit = retrofitBuilder()
        val token = getToken(this)

        retrofit.build().create(AccountTypeEndpoint::class.java)
            .create(token, request)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, "Error while creating account type", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val toast = Toast.makeText(applicationContext, "Account type created", Toast.LENGTH_LONG)
                    toast.show()
                    finish()
                }

            })
    }
}
