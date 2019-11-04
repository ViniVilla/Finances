package br.edu.ifsp.finances.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.finances.R
import br.edu.ifsp.finances.domain.request.TransactionRequest
import br.edu.ifsp.finances.domain.response.AccountResponse
import br.edu.ifsp.finances.domain.response.TransactionCategoryResponse
import br.edu.ifsp.finances.domain.response.TransactionResponse
import br.edu.ifsp.finances.endpoint.AccountEndpoint
import br.edu.ifsp.finances.endpoint.TransactionCategoryEndpoint
import br.edu.ifsp.finances.endpoint.TransactionEndpoint
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import kotlinx.android.synthetic.main.activity_add_account.button
import kotlinx.android.synthetic.main.activity_add_transaction.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

class AddTransactionActivity : AppCompatActivity() {
    private lateinit var accounts : List<AccountResponse>
    private lateinit var categories : List<TransactionCategoryResponse>
    private lateinit var transaction : TransactionResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)
        populateAccountSpinner()
        populateCategorySpinner()

        button.setOnClickListener({create()})
        if(intent.getIntExtra("position", -1) != -1) configureUpdate()
    }

    private fun configureUpdate() {
        transaction = intent.getParcelableExtra("transaction")
        transactionEdit.setText(transaction.name)
        amountEdit.setText(transaction.amount.toPlainString())
        button.setOnClickListener({update()})
    }

    fun update() {
        val account = accountSpinner.selectedItem as AccountResponse
        val category = categorySpinner.selectedItem as TransactionCategoryResponse
        val request = TransactionRequest(transactionEdit.text.toString(),
            BigDecimal(amountEdit.text.toString()),
            account.id,
            category.id)

        val retrofit = retrofitBuilder()
        val token = getToken(this)

        retrofit.build().create(TransactionEndpoint::class.java)
            .update(token, request, transaction.id)
            .enqueue(object : Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, "Error while updating transaction", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.code() == 200){
                        val toast = Toast.makeText(applicationContext, "Transaction updated", Toast.LENGTH_LONG)
                        toast.show()
                        finish()
                    }
                    val toast = Toast.makeText(applicationContext, "Error while updating transaction", Toast.LENGTH_LONG)
                    toast.show()
                }

            })
    }

    fun create() {
        val account = accountSpinner.selectedItem as AccountResponse
        val category = categorySpinner.selectedItem as TransactionCategoryResponse
        val request = TransactionRequest(transactionEdit.text.toString(),
                                        BigDecimal(amountEdit.text.toString()),
                                        account.id,
                                        category.id)

        val retrofit = retrofitBuilder()
        val token = getToken(this)

        retrofit.build().create(TransactionEndpoint::class.java)
            .create(token, request)
            .enqueue(object : Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(applicationContext, "Error while creating transaction", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    val toast = Toast.makeText(applicationContext, "Transaction created", Toast.LENGTH_LONG)
                    toast.show()
                    finish()
                }

            })
    }

    fun populateAccountSpinner() {
        val retrofit = retrofitBuilder()
        val token = getToken(this)
        retrofit.build().create(AccountEndpoint::class.java)
            .get(token)
            .enqueue(object : Callback<List<AccountResponse>> {
                override fun onFailure(call: Call<List<AccountResponse>>, t: Throwable) {
                    val toast = Toast.makeText(this@AddTransactionActivity, "Error while getting Accounts", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<List<AccountResponse>>, response: Response<List<AccountResponse>>) {
                    accounts = response.body()!!
                    val adapter = ArrayAdapter(this@AddTransactionActivity, R.layout.support_simple_spinner_dropdown_item, accounts)
                    adapter.setDropDownViewResource(R.layout.spinner_item)
                    accountSpinner.adapter = adapter
                    if(intent.getIntExtra("position", -1) != -1)
                        accountSpinner.setSelection(accounts.indexOf(transaction.account))
                }

            })
    }

    fun populateCategorySpinner() {
        val retrofit = retrofitBuilder()
        val token = getToken(this)
        retrofit.build().create(TransactionCategoryEndpoint::class.java)
            .get(token)
            .enqueue(object : Callback<List<TransactionCategoryResponse>> {
                override fun onFailure(call: Call<List<TransactionCategoryResponse>>, t: Throwable) {
                    val toast = Toast.makeText(this@AddTransactionActivity, "Error while getting TransactionCategories", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<List<TransactionCategoryResponse>>, response: Response<List<TransactionCategoryResponse>>) {
                    categories = response.body()!!
                    val adapter = ArrayAdapter(this@AddTransactionActivity, R.layout.support_simple_spinner_dropdown_item, categories)
                    adapter.setDropDownViewResource(R.layout.spinner_item)
                    categorySpinner.adapter = adapter
                    if(intent.getIntExtra("position", -1) != -1)
                        categorySpinner.setSelection(categories.indexOf(transaction.category))
                }

            })
    }

}
