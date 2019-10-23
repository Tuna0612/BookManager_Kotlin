package com.tuna.bookmanager.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.tuna.bookmanager.R
import com.tuna.bookmanager.adapter.TypeBookAdapter
import com.tuna.bookmanager.database.TypeBookDAO
import com.tuna.bookmanager.model.TypeBook
import kotlinx.android.synthetic.main.dialog_add_typebook.view.*
import kotlinx.android.synthetic.main.fragment_type_book.view.*

class TypeBookFragment : Fragment() {
    var listTypeBook: ArrayList<TypeBook> = ArrayList()

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.fragment_type_book, container, false)
        val typeBookDAO = TypeBookDAO(view.context)
        listTypeBook = typeBookDAO.getAllTypeBook
        view.rcViewTypeBook.layoutManager = LinearLayoutManager(context)
        val adapter =
            TypeBookAdapter(listTypeBook, context, object : TypeBookAdapter.AdapterListener {
                override fun OnClick(position: Int) {

                }
            })
        view.rcViewTypeBook.adapter = adapter

        view.fab.setOnClickListener {
            val view: View =
                LayoutInflater.from(activity).inflate(R.layout.dialog_add_typebook, null)
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Add Type Book")
            builder.setIcon(R.drawable.ic_note_add)
            builder.setView(view)
            val dialog = builder.create()
            dialog.show()
            val id = view.edidtheloai.text
            val name = view.edtentheloai.text
            val position = view.edvitritheloai.text
            val description = view.edmotatheloai.text

            view.cardsavetl.setOnClickListener {
                val typeBook = TypeBook(
                    id.toString(),
                    name.toString(),
                    position.toString(),
                    description.toString()
                )
                typeBookDAO.insertTypeBook(typeBook)
                listTypeBook.clear()
                listTypeBook = typeBookDAO.getAllTypeBook
                adapter.changeDataset(listTypeBook)
                dialog.dismiss()
            }
        }
        return view
    }
}
