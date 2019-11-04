package br.edu.ifsp.finances.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.finances.R
import br.edu.ifsp.finances.domain.request.TransactionCategoryRequest
import br.edu.ifsp.finances.domain.response.TransactionCategoryResponse
import br.edu.ifsp.finances.endpoint.TransactionCategoryEndpoint
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import kotlinx.android.synthetic.main.activity_add_category.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCategoryActivity : AppCompatActivity() {
    private lateinit var transactionCategory : TransactionCategoryResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)
        supportActionBar?.title = "Add Transaction Category"

        button.setOnClickListener({create()})
        if(intent.getIntExtra("position", -1) != -1) {
            configureUpdate()
            button.setOnClickListener{update()}
        }
    }

    fun configureUpdate() {
        transactionCategory = intent.getParcelableExtra("transactionCategory")
        categoryEdit.setText(transactionCategory.name)
    }

    fun update() {
        val request = TransactionCategoryRequest(categoryEdit.text.toString())

        val retrofit = retrofitBuilder()
        val token = getToken(this)
        retrofit.build().create(TransactionCategoryEndpoint::class.java)
            .update(token, request, transactionCategory.id)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, "Error while updating transaction category", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val toast = Toast.makeText(applicationContext, "Transaction category updated", Toast.LENGTH_LONG)
                    toast.show()
                    finish()
                }

            })
    }

    fun create() {
        val request = TransactionCategoryRequest(categoryEdit.text.toString())

        val retrofit = retrofitBuilder()
        val token = getToken(this)

        retrofit.build().create(TransactionCategoryEndpoint::class.java)
            .create(token, request)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, "Error while creating transaction category", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val toast = Toast.makeText(applicationContext, "Transaction category created", Toast.LENGTH_LONG)
                    toast.show()
                    finish()
                }

            })
    }
}
