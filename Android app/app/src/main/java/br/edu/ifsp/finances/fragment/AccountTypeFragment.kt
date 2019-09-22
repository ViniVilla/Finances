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
import br.edu.ifsp.finances.adapter.AccountTypeAdapter
import br.edu.ifsp.finances.domain.response.AccountTypeResponse
import br.edu.ifsp.finances.endpoint.AccountTypeEndpoint
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_account_type.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountTypeFragment : Fragment() {
    private lateinit var accountTypeResponses : List<AccountTypeResponse>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_account_type, container, false)
        view.findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener({fabClick()})

        getAccountTypes()

        return view
    }

    fun getAccountTypes(){
        val retrofit = retrofitBuilder()
        val token = getToken(context)
        retrofit.build().create(AccountTypeEndpoint::class.java)
            .get(token)
            .enqueue(object : Callback<List<AccountTypeResponse>> {
                override fun onFailure(call: Call<List<AccountTypeResponse>>, t: Throwable) {
                    val toast = Toast.makeText(context, "Error while getting AccountResponse Types", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<List<AccountTypeResponse>>, response: Response<List<AccountTypeResponse>>) {
                    accountTypeResponses = response.body()!!
                    val viewAdapter =
                        AccountTypeAdapter(this@AccountTypeFragment, accountTypeResponses as MutableList<AccountTypeResponse>)

                    val recyclerView = recycler_account_type
                    recyclerView.adapter = viewAdapter
                    recyclerView.layoutManager = LinearLayoutManager(this@AccountTypeFragment.context)
                }

            })
    }

    fun fabClick(){

    }

    @Override
    override fun onResume() {
        getAccountTypes()

        super.onResume()
    }

}
