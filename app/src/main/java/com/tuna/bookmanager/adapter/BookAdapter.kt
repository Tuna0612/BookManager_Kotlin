package com.tuna.bookmanager.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.tuna.bookmanager.R
import com.tuna.bookmanager.database.BookDAO
import com.tuna.bookmanager.database.TypeBookDAO
import com.tuna.bookmanager.model.Book
import com.tuna.bookmanager.model.TypeBook
import kotlinx.android.synthetic.main.dialog_edit_book.view.*
import kotlinx.android.synthetic.main.item_book.view.*
import java.util.ArrayList

class BookAdapter(
    var listBook: List<Book>,
    val context: Context?,
    adapterListener: AdapterListener
) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    var bookDAO = BookDAO(context)
    var typeBookDAO = TypeBookDAO(context)
    var listTypeBook: ArrayList<TypeBook>? = null
    var maTheLoai: String? = null
    private lateinit var spinner:Spinner

    interface AdapterListener {
        fun OnClick(position: Int)
    }

    fun changeDataset(listBook: List<Book>) {
        this.listBook = listBook
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_book,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listBook.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvID.text = listBook[position].id
        holder.tvName.text = listBook[position].tenSach
        holder.tvTypeBook.text = "Thể loại: " + listBook[position].maTheLoai
        holder.tvPrice.text = "Giá: " + listBook[position].price.toString() + "VNĐ"
        holder.btnEdit.setOnClickListener {
            val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_edit_book, null)
            val builder = AlertDialog.Builder(context)
            builder.setView(view)
            builder.setIcon(R.drawable.ic_edit)
            builder.setTitle("Edit Book")
            val dialog = builder.create()
            dialog.show()

            view.edName.setText(listBook[position].tenSach)
            maTheLoai = listBook[position].maTheLoai
            spinner = view.spinner

            view.edAuthor.setText(listBook[position].author)
            view.edNXB.setText(listBook[position].nxb)
            view.edTotal.setText(listBook[position].total.toString())
            view.edPrice.setText(listBook[position].price.toString())
            view.cardsave.setOnClickListener {
                bookDAO.updateBook(listBook[position].id.toString(),view.edName.text.toString(),
                    maTheLoai.toString(),view.edAuthor.text.toString(),view.edNXB.text.toString(),view.edPrice.text.toString().toDouble(),view.edTotal.text.toString().toInt())
                listBook = bookDAO.getAllBook
                notifyDataSetChanged()
                dialog.dismiss()
            }

            view.cardcancel.setOnClickListener {
                Toast.makeText(context,"Cancel",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    v: View?,
                    position: Int,
                    id: Long
                ) {
                    maTheLoai = listTypeBook?.get(spinner.selectedItemPosition)?.name
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
            getAllTypeBook()
        }

        holder.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setIcon(R.drawable.ic_delete)
            builder.setTitle("Delete Book")
            builder.setMessage("Do you want to delete this book: " + listBook[position].tenSach)
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                bookDAO.deleteBook(listBook[position].id.toString())
                listBook = bookDAO.getAllBook
                notifyDataSetChanged()
            })

            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
            })

            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun checkPosition(idType: String?): Int {
        if (listTypeBook != null) {
            for (i in 0..listTypeBook!!.size) {
                if (idType.equals(listTypeBook!![i].name)) {
                    return i
                }
            }
        }
        return 0
    }

    private fun getAllTypeBook(){
        listTypeBook = typeBookDAO.getAllTypeBook
        typeBookDAO = TypeBookDAO(context)
        listTypeBook = typeBookDAO.getAllTypeBook
        val dataAdapter =
            context?.let {
                ArrayAdapter<TypeBook>(
                    it,
                    android.R.layout.simple_spinner_item,
                    listTypeBook!!
                )
            }
        dataAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapter
        spinner.setSelection(checkPosition(maTheLoai))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.tvName
        val tvID: TextView = itemView.tvID
        val tvTypeBook: TextView = itemView.tvTypeBook
        val tvPrice: TextView = itemView.tvPrice
        val btnEdit: ImageView = itemView.btnEdit
        val btnDelete: ImageView = itemView.btnDelete
    }

}