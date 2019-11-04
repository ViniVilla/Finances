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
import br.edu.ifsp.finances.activity.AddCategoryActivity
import br.edu.ifsp.finances.adapter.TransactionCategoryAdapter
import br.edu.ifsp.finances.domain.response.TransactionCategoryResponse
import br.edu.ifsp.finances.endpoint.TransactionCategoryEndpoint
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_transaction_category.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionCategoryFragment : Fragment() {
    private lateinit var categoryResponses : List<TransactionCategoryResponse>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_category, container, false)
        view.findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener({fabClick()})

        getTransactionCategories()

        return view
    }

    fun getTransactionCategories() {
        val retrofit = retrofitBuilder()
        val token = getToken(context)
        retrofit.build()
            .create(TransactionCategoryEndpoint::class.java)
            .get(token)
            .enqueue(object : Callback<List<TransactionCategoryResponse>>{
                override fun onFailure(call: Call<List<TransactionCategoryResponse>>, t: Throwable) {
                    val toast = Toast.makeText(context, "Error while getting TransactionResponse Categories", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<List<TransactionCategoryResponse>>, response: Response<List<TransactionCategoryResponse>>) {
                    categoryResponses = response.body()!!
                    val viewAdapter =
                        TransactionCategoryAdapter(this@TransactionCategoryFragment, categoryResponses as MutableList<TransactionCategoryResponse>)

                    val recyclerView = recycler_transaction_category
                    recyclerView.adapter = viewAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this@TransactionCategoryFragment.context)
                }

            })
    }

    fun fabClick(){
        startActivity(Intent(this.context, AddCategoryActivity::class.java))
    }

    @Override
    override fun onResume() {
        getTransactionCategories()

        super.onResume()
    }

}
