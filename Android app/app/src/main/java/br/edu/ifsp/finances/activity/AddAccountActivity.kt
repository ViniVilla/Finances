package br.edu.ifsp.finances.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.finances.R
import br.edu.ifsp.finances.domain.request.AccountRequest
import br.edu.ifsp.finances.domain.response.AccountResponse
import br.edu.ifsp.finances.domain.response.AccountTypeResponse
import br.edu.ifsp.finances.endpoint.AccountEndpoint
import br.edu.ifsp.finances.endpoint.AccountTypeEndpoint
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import kotlinx.android.synthetic.main.activity_add_account.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAccountActivity : AppCompatActivity() {
    private lateinit var accountTypes : List<AccountTypeResponse>
    private lateinit var account : AccountResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)
        supportActionBar?.title = "Add Account"
        populateSpinner()

        button.setOnClickListener({create()})
        if(intent.getIntExtra("position", -1) != -1) configureUpdate()
    }

    private fun configureUpdate() {
        account = intent.getParcelableExtra("account")
        accountEdit.setText(account.name)
        button.setOnClickListener({update()})
    }

    fun update() {
        val type = spinner.selectedItem as AccountTypeResponse
        val request = AccountRequest(accountEdit.text.toString(), type.id)

        val retrofit = retrofitBuilder()
        val token = getToken(this)

        retrofit.build().create(AccountEndpoint::class.java)
            .update(token, request, account.id)
            .enqueue(object : Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, "Error while updating account", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.code() == 200){
                        val toast = Toast.makeText(applicationContext, "Account updated", Toast.LENGTH_LONG)
                        toast.show()
                        finish()
                    }
                    else{
                        val toast = Toast.makeText(applicationContext, "Error while updating account", Toast.LENGTH_LONG)
                        toast.show()
                    }
                }

            })
    }

    fun create() {
        val type = spinner.selectedItem as AccountTypeResponse
        val request = AccountRequest(accountEdit.text.toString(), type.id)

        val retrofit = retrofitBuilder()
        val token = getToken(this)

        retrofit.build().create(AccountEndpoint::class.java)
            .create(token, request)
            .enqueue(object : Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, "Error while creating account", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val toast = Toast.makeText(applicationContext, "Account created", Toast.LENGTH_LONG)
                    toast.show()
                    finish()
                }

            })
    }

    fun populateSpinner() {
        val retrofit = retrofitBuilder()
        val token = getToken(this)
        retrofit.build().create(AccountTypeEndpoint::class.java)
            .get(token)
            .enqueue(object : Callback<List<AccountTypeResponse>> {
                override fun onFailure(call: Call<List<AccountTypeResponse>>, t: Throwable) {
                    val toast = Toast.makeText(this@AddAccountActivity, "Error while getting AccountResponse Types", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<List<AccountTypeResponse>>, response: Response<List<AccountTypeResponse>>) {
                    accountTypes = response.body()!!
                    val adapter = ArrayAdapter(this@AddAccountActivity, R.layout.support_simple_spinner_dropdown_item, accountTypes)
                    adapter.setDropDownViewResource(R.layout.spinner_item)
                    spinner.adapter = adapter
                    if(intent.getIntExtra("position", -1) != -1)
                        spinner.setSelection(accountTypes.indexOf(account.type))
                }

            })
    }
}
