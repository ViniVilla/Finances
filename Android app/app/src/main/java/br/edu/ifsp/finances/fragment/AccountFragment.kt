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
import br.edu.ifsp.finances.adapter.AccountAdapter
import br.edu.ifsp.finances.domain.response.AccountResponse
import br.edu.ifsp.finances.endpoint.AccountEndpoint
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_account.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFragment : Fragment() {
    private lateinit var accountResponses : List<AccountResponse>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        view.findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener({fabClick()})

        getAccounts()

        return view
    }

    fun getAccounts() {
        val retrofit = retrofitBuilder()
        val token = getToken(context)
        retrofit.build().create(AccountEndpoint::class.java)
            .get(token)
            .enqueue(object : Callback<List<AccountResponse>>{
                override fun onFailure(call: Call<List<AccountResponse>>, t: Throwable) {
                    val toast = Toast.makeText(context, "Error while getting Accounts", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<List<AccountResponse>>, response: Response<List<AccountResponse>>) {
                    accountResponses = response.body()!!
                    val viewAdapter = AccountAdapter(this@AccountFragment, accountResponses as MutableList<AccountResponse>)

                    val recyclerView = recycler_account
                    recyclerView.adapter = viewAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this@AccountFragment.context)
                }

            })
    }

    fun fabClick(){

    }

    @Override
    override fun onResume() {
        getAccounts()

        super.onResume()
    }

}
