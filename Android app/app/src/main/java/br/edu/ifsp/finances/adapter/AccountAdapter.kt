package br.edu.ifsp.finances.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.finances.R
import br.edu.ifsp.finances.domain.response.AccountResponse
import br.edu.ifsp.finances.endpoint.AccountEndpoint
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import kotlinx.android.synthetic.main.item_account.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountAdapter (val parentFragment: Fragment, val accountResponses : MutableList<AccountResponse>) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account, parent, false)

        return AccountViewHolder(view)
    }

    override fun getItemCount(): Int {
        return accountResponses.size
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.view.name.
            text = accountResponses[position].name
        holder.view.accountName.
            text = accountResponses[position].type.name
        holder.view.imageView.setOnClickListener {delete(accountResponses[position])}
        holder.view.setOnLongClickListener { update(accountResponses[position], position) }
    }

    fun update(accountResponse: AccountResponse, position: Int) : Boolean{

        return true
    }


    fun delete(account : AccountResponse) {
        val retrofit = retrofitBuilder()
        val token = getToken(parentFragment.context)
        retrofit.build().create(AccountEndpoint::class.java)
            .delete(token, account.id)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(parentFragment.context, "Error while deleting account", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.code() == 200){
                        accountResponses.remove(account)
                        this@AccountAdapter.notifyDataSetChanged()
                        val toast = Toast.makeText(parentFragment.context, "Account deleted", Toast.LENGTH_LONG)
                        toast.show()
                    }
                    else{
                        val toast = Toast.makeText(parentFragment.context, "Error while deleting account", Toast.LENGTH_LONG)
                        toast.show()
                    }
                }

            })
    }

    class AccountViewHolder(@NonNull val view : View) : RecyclerView.ViewHolder(view)

}