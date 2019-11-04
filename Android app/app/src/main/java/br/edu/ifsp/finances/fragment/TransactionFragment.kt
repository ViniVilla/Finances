package br.edu.ifsp.finances.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.finances.R
import br.edu.ifsp.finances.activity.AddTransactionActivity
import br.edu.ifsp.finances.adapter.TransactionAdapter
import br.edu.ifsp.finances.domain.response.TransactionResponse
import br.edu.ifsp.finances.endpoint.TransactionEndpoint
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_transaction.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TransactionFragment : Fragment() {

    private lateinit var transactionResponses : List<TransactionResponse>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)
        view.findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener({fabClick()})

        getTransactions()

        return view
    }

    private fun getTransactions() {
        val retrofit = retrofitBuilder()
        val token = getToken(context)
        retrofit.build()
            .create(TransactionEndpoint::class.java)
            .get(token)
            .enqueue(object : Callback<List<TransactionResponse>>{
                override fun onFailure(call: Call<List<TransactionResponse>>, t: Throwable) {
                    val toast = Toast.makeText(context, "Error while getting Transactions", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<List<TransactionResponse>>, response: Response<List<TransactionResponse>>) {
                    transactionResponses = response.body()!!
                    val viewAdapter =
                        TransactionAdapter(this@TransactionFragment, transactionResponses as MutableList<TransactionResponse>)

                    val recyclerView = recycler_transaction
                    recyclerView.adapter = viewAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this@TransactionFragment.context)
                }

            })
    }

    fun fabClick(){
        startActivity(Intent(this.context, AddTransactionActivity::class.java))
    }

    @Override
    override fun onResume() {
        getTransactions()

        super.onResume()
    }

}
