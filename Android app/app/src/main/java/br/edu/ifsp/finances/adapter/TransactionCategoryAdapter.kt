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
import br.edu.ifsp.finances.activity.AddCategoryActivity
import br.edu.ifsp.finances.adapter.TransactionCategoryAdapter.TransactionCategoryViewHolder
import br.edu.ifsp.finances.domain.response.TransactionCategoryResponse
import br.edu.ifsp.finances.endpoint.TransactionCategoryEndpoint
import br.edu.ifsp.finances.utils.getToken
import br.edu.ifsp.finances.utils.retrofitBuilder
import kotlinx.android.synthetic.main.item_category.view.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionCategoryAdapter (val parentFragment: Fragment, val categoryResponses : MutableList<TransactionCategoryResponse>) : RecyclerView.Adapter<TransactionCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionCategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return TransactionCategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryResponses.size
    }

    override fun onBindViewHolder(holder: TransactionCategoryViewHolder, position: Int) {
        holder.view.name
            .text = categoryResponses[position].name
        holder.view.imageView.setOnClickListener {delete(categoryResponses[position])}
        holder.view.setOnLongClickListener {update(categoryResponses[position], position)}
    }

    fun update(transactionCategoryResponse: TransactionCategoryResponse, position: Int) : Boolean{
        var intentNew = Intent(parentFragment.context, AddCategoryActivity::class.java)
        intentNew.putExtra("position", position)
        intentNew.putExtra("transactionCategory", transactionCategoryResponse)
        parentFragment.startActivity(intentNew)

        return true
    }

    fun delete(category : TransactionCategoryResponse) {
        val retrofit = retrofitBuilder()
        val token = getToken(parentFragment.context)
        retrofit.build().create(TransactionCategoryEndpoint::class.java)
            .delete(token, category.id)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val toast = Toast.makeText(parentFragment.context, "Error while deleting transaction category", Toast.LENGTH_LONG)
                    toast.show()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if(response.code() == 200){
                        categoryResponses.remove(category)
                        this@TransactionCategoryAdapter.notifyDataSetChanged()
                        val toast = Toast.makeText(parentFragment.context, "Transaction category deleted", Toast.LENGTH_LONG)
                        toast.show()
                    }
                    else{
                        val toast = Toast.makeText(parentFragment.context, "Error while deleting transaction category", Toast.LENGTH_LONG)
                        toast.show()
                    }
                }

            })
    }

    class TransactionCategoryViewHolder(@NonNull val view : View) : RecyclerView.ViewHolder(view)
}