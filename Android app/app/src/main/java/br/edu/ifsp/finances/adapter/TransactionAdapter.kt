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
import br.edu.ifsp.finances.activity.AddAccountActivity
import br.edu.ifsp.finances.activity.AddTransactionActivity
import br.edu.ifsp.finances.domain.response.TransactionResponse
import br.edu.ifsp.finances.endpoint.TransactionEndpoint
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import kotlinx.android.synthetic.main.item_transaction.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat

class TransactionAdapter (val parentFragment : Fragment, val transactionResponses : MutableList<TransactionResponse>) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return transactionResponses.size
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.view.name
            .text = transactionResponses[position].name
        val formatter = NumberFormat.getCurrencyInstance()
        holder.view.amountValue
            .text = formatter.format(transactionResponses[position].amount)
        holder.view.accountName
            .text = transactionResponses[position].account.name
        holder.view.categoryName
            .text = transactionResponses[position].category.name
        holder.view.imageView.setOnClickListener {delete(transactionResponses[position])}
        holder.view.setOnLongClickListener { update(transactionResponses[position], position) }
    }

    private fun update(transactionResponse: TransactionResponse, position: Int) : Boolean{
        var intentNew = Intent(parentFragment.context, AddTransactionActivity::class.java)
        intentNew.putExtra("position", position)
        intentNew.putExtra("transaction", transactionResponse)
        parentFragment.startActivity(intentNew)

        return true
    }

    fun delete(transaction : TransactionResponse) {
        val retrofit = retrofitBuilder()
        val token = getToken(parentFragment.context)
        retrofit.build().create(TransactionEndpoint::class.java)
            .delete(token, transaction.id)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(parentFragment.context, "Error while deleting transaction", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.code() == 200){
                        transactionResponses.remove(transaction)
                        this@TransactionAdapter.notifyDataSetChanged()
                        val toast = Toast.makeText(parentFragment.context, "Transaction deleted", Toast.LENGTH_LONG)
                        toast.show()
                    }
                    else{
                        val toast = Toast.makeText(parentFragment.context, "Error while deleting transaction", Toast.LENGTH_LONG)
                        toast.show()
                    }
                }

            })
    }

    class TransactionViewHolder(@NonNull val view : View) : RecyclerView.ViewHolder(view)
}