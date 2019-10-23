package com.tuna.bookmanager.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager

import com.tuna.bookmanager.R
import com.tuna.bookmanager.adapter.BookAdapter
import com.tuna.bookmanager.database.BookDAO
import com.tuna.bookmanager.database.TypeBookDAO
import com.tuna.bookmanager.model.Book
import com.tuna.bookmanager.model.TypeBook
import kotlinx.android.synthetic.main.dialog_add_book.view.*
import kotlinx.android.synthetic.main.fragment_book.view.*

class BookFragment : Fragment() {
    var listTypeBook: ArrayList<TypeBook> = ArrayList()
    var listBook: ArrayList<Book> = ArrayList()
    private lateinit var typeBookDAO: TypeBookDAO
    private lateinit var bookDAO: BookDAO
    private lateinit var adapter: BookAdapter
    private lateinit var spinner: Spinner
    var maTheLoai: String? = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.fragment_book, container, false)
        typeBookDAO = TypeBookDAO(view.context)
        bookDAO = BookDAO(view.context)
        listBook = bookDAO.getAllBook
        view.rcViewBook.layoutManager = LinearLayoutManager(context)
        adapter = BookAdapter(listBook, context, object : BookAdapter.AdapterListener {
            override fun OnClick(position: Int) {

            }
        })

        view.rcViewBook.adapter = adapter

        view.fab.setOnClickListener {
            val view: View = LayoutInflater.from(context).inflate(R.layout.dialog_add_book, null)
            val     builder = AlertDialog.Builder(activity)
            builder.setView(view)
            builder.setIcon(R.drawable.ic_note_add)
            builder.setTitle("Add Book")
            val dialog = builder.create()
            dialog.show()
            spinner = view.spnTheLoai

            view.cardsavebook.setOnClickListener {
                val book = Book(
                    view.edMaSach.text.toString(),
                    maTheLoai,
                    view.edTenSach.text.toString(),
                    view.edTacGia.text.toString(),
                    view.edNXB.text.toString(),
                    view.edGiaBia.text.toString().toDouble(),
                    view.edSoLuong.text.toString().toInt()
                )
                bookDAO.insertBook(book)
                listBook.clear()
                listBook = bookDAO.getAllBook
                adapter.changeDataset(listBook)
                dialog.dismiss()
            }

            view.cardcancelbook.setOnClickListener {
                dialog.dismiss()
            }


            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    v: View?,
                    position: Int,
                    id: Long
                ) {
                    maTheLoai = listTypeBook[spinner.selectedItemPosition].name
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
            getTheLoai()

        }
        return view
    }

    private fun getTheLoai() {
        typeBookDAO = TypeBookDAO(context)
        listTypeBook = typeBookDAO.getAllTypeBook
        val dataAdapter =
            context?.let {
                ArrayAdapter<TypeBook>(
                    it,
                    android.R.layout.simple_spinner_item,
                    listTypeBook
                )
            }
        dataAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dataAdapter
    }
}
