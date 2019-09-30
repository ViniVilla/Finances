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
import br.edu.ifsp.finances.activity.AddAccountTypeActivity
import br.edu.ifsp.finances.adapter.AccountTypeAdapter.AccountTypeViewHolder
import br.edu.ifsp.finances.domain.response.AccountTypeResponse
import br.edu.ifsp.finances.endpoint.AccountTypeEndpoint
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import kotlinx.android.synthetic.main.item_account_type.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountTypeAdapter (val parentFragment: Fragment, val accountTypeResponses : MutableList<AccountTypeResponse>) : RecyclerView.Adapter<AccountTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountTypeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_account_type, parent, false)

        return AccountTypeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return accountTypeResponses.size
    }

    override fun onBindViewHolder(holder: AccountTypeViewHolder, position: Int) {
        holder.view.name
            .text = accountTypeResponses[position].name
        holder.view.imageView.setOnClickListener {delete(accountTypeResponses[position])}
        holder.view.setOnLongClickListener {update(accountTypeResponses[position], position)}
    }

    fun update(accountTypeResponse: AccountTypeResponse, position: Int) : Boolean{
        var intentNew = Intent(parentFragment.context, AddAccountTypeActivity::class.java)
        intentNew.putExtra("position", position)
        intentNew.putExtra("accountType", accountTypeResponse)
        parentFragment.startActivity(intentNew)

        return true
    }

    fun delete(accountType : AccountTypeResponse) {
        val retrofit = retrofitBuilder()
        val token = getToken(parentFragment.context)
        retrofit.build().create(AccountTypeEndpoint::class.java)
            .delete(token, accountType.id)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(parentFragment.context, "Error while deleting account type", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.code() == 200){
                        accountTypeResponses.remove(accountType)
                        this@AccountTypeAdapter.notifyDataSetChanged()
                        val toast = Toast.makeText(parentFragment.context, "Account type deleted", Toast.LENGTH_LONG)
                        toast.show()
                    }
                    else{
                        val toast = Toast.makeText(parentFragment.context, "Error while deleting account type", Toast.LENGTH_LONG)
                        toast.show()
                    }
                }

            })
    }

    class AccountTypeViewHolder(@NonNull val view : View) : RecyclerView.ViewHolder(view)

}