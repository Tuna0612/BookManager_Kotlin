package com.tuna.bookmanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tuna.bookmanager.R
import com.tuna.bookmanager.database.BillDAO
import com.tuna.bookmanager.model.Bill
import kotlinx.android.synthetic.main.item_bill.view.*
import java.text.SimpleDateFormat

class BillAdapter(var listBill:List<Bill>, var context: Context?, adapterListener: AdapterListener):
    RecyclerView.Adapter<BillAdapter.ViewHolder>() {

    //private var listBill:ArrayList<Bill>?=null
    val sdf = SimpleDateFormat("dd-MM-yyy")

    interface AdapterListener{
        fun OnClick(position:Int)
    }

    fun changeDataset(listBill: List<Bill>){
        this.listBill = listBill
        notifyDataSetChanged()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bill,parent,false))
    }

    override fun getItemCount(): Int {
        return listBill!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvID.text = listBill?.get(position)?.id
        holder.tvDate.text = sdf.format(listBill?.get(position)?.date)

        holder.btnEdit.setOnClickListener {

        }

        holder.btnDelete.setOnClickListener {

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvID:TextView = itemView.tvID
        var tvDate:TextView = itemView.tvDate
        var btnEdit:ImageView = itemView.btnEdit
        var btnDelete:ImageView = itemView.btnDelete
    }

}