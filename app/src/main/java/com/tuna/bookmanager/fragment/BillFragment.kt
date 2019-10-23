package com.tuna.bookmanager.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.tuna.bookmanager.R
import com.tuna.bookmanager.adapter.BillAdapter
import com.tuna.bookmanager.database.BillDAO
import com.tuna.bookmanager.model.Bill
import kotlinx.android.synthetic.main.dialog_bill.view.*
import kotlinx.android.synthetic.main.fragment_bill.view.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

class BillFragment : Fragment() {

    private lateinit var edDatePicker: EditText
    var listBill:ArrayList<Bill> = ArrayList()
    lateinit var billDAO:BillDAO
    lateinit var adapter: BillAdapter
    @SuppressLint("SimpleDateFormat")
    val sdf = SimpleDateFormat("dd-MM-yyyy")


    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View = LayoutInflater.from(context).inflate(R.layout.fragment_bill, container, false)
        billDAO = BillDAO(view.context)
        listBill = billDAO.getAllBill
        view.rcViewBill.layoutManager = LinearLayoutManager(context)

        adapter = BillAdapter(listBill,context,object: BillAdapter.AdapterListener{
            override fun OnClick(position: Int) {

            }
        })

        view.fab.setOnClickListener {
            val view:View = LayoutInflater.from(context).inflate(R.layout.dialog_bill,null)
            val builder = AlertDialog.Builder(activity)
            builder.setIcon(R.drawable.ic_note_add)
            builder.setTitle("Add Bill")
            builder.setView(view)
            edDatePicker = view.edDateHoaDon


            val id = view.edIDHoaDon.text
            val date = view.edDateHoaDon.text

            view.imgDatePicker.setOnClickListener {
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)

                val dpd = context?.let { it1 ->
                    DatePickerDialog(it1,
                        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                            edDatePicker.setText("$dayOfMonth - ${monthOfYear + 1} - $year")

                        },year,month,day)
                }
                dpd!!.show()
            }

            view.cardsave.setOnClickListener {
//                val bill = Bill(id.toString(),date.toString())
//                billDAO.insertBill(bill)
//                listBill.clear()
//                listBill = billDAO.getAllBill
//                adapter.changeDataset(listBill)

                //Log.d("DATAAAA", sdf.parse(date.toString()).toString())
            }



            val dialog = builder.create()
            dialog.show()
        }
        return view
    }
}
