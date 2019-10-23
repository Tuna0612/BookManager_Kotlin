package com.tuna.bookmanager.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.tuna.bookmanager.R
import com.tuna.bookmanager.database.TypeBookDAO
import com.tuna.bookmanager.model.TypeBook
import kotlinx.android.synthetic.main.dialog_edit_typebook.view.*
import kotlinx.android.synthetic.main.item_typebook.view.*

class TypeBookAdapter(
    var listTypeBook: List<TypeBook>,
    val context: Context?,
    val adapterListener: AdapterListener
) :
    RecyclerView.Adapter<TypeBookAdapter.ViewHolder>() {

    val typeBookDAO = TypeBookDAO(context)

    interface AdapterListener {
        fun OnClick(position: Int)
    }

    fun changeDataset(listTypeBook: List<TypeBook>) {
        this.listTypeBook = listTypeBook
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_typebook,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listTypeBook.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvID.text = listTypeBook[position].id
        holder.tvName.text = "Thể loại: " + listTypeBook[position].name
        holder.tvPosition.text = "Vị trí: " + listTypeBook[position].position
        holder.tvDescription.text = "Mô tả: " + listTypeBook[position].description
        holder.cardView.setOnClickListener {
            adapterListener.OnClick(position)
        }

        holder.btnEdit.setOnClickListener {
            val view: View =
                LayoutInflater.from(context).inflate(R.layout.dialog_edit_typebook, null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            builder.setIcon(R.drawable.ic_edit)
            builder.setTitle("Edit Type Book")
            val dialog = builder.create()
            dialog.show()

            view.edName.setText(listTypeBook[position].name)
            view.edPosition.setText(listTypeBook[position].position)
            view.edDescription.setText(listTypeBook[position].description)

            view.cardsave.setOnClickListener {
                typeBookDAO.updateTypeBook(
                    listTypeBook[position].id.toString(),
                    view.edName.text.toString(),
                    view.edPosition.text.toString(),
                    view.edDescription.text.toString()
                )
                listTypeBook = typeBookDAO.getAllTypeBook
                notifyDataSetChanged()
                dialog.dismiss()
            }

            view.cardcancel.setOnClickListener {
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        holder.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setIcon(R.drawable.ic_delete)
            builder.setTitle("Delete Type Book")
            builder.setMessage("Do you want to delete this type book: " + listTypeBook[position].name)
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                typeBookDAO.deleteTypeBook(listTypeBook[position].id.toString())
                listTypeBook = typeBookDAO.getAllTypeBook
                notifyDataSetChanged()
            })

            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
            })

            val dialog = builder.create()
            dialog.show()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvID: TextView = itemView.tvID
        val tvName: TextView = itemView.tvName
        val tvPosition: TextView = itemView.tvPosition
        val tvDescription: TextView = itemView.tvDescription
        val cardView: CardView = itemView.cardView
        val btnEdit: ImageView = itemView.btnEdit
        val btnDelete: ImageView = itemView.btnDelete
    }

}