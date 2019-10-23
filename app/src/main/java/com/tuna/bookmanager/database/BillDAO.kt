package com.tuna.bookmanager.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.tuna.bookmanager.model.Bill
import com.tuna.bookmanager.model.Book
import java.text.SimpleDateFormat
import java.util.*

class BillDAO(context: Context): Constant() {
    private var dbManager:DatabaseManager?=null
    private var sdf:SimpleDateFormat?=null
    init {
        dbManager = DatabaseManager(context)
        sdf = SimpleDateFormat("dd-MM-yyyy")
    }

    val getAllBill:ArrayList<Bill>
        get() {
            val listBill = ArrayList<Bill>()
            val selectQuery = "SELECT * FROM $TABLE_BILL"
            val db: SQLiteDatabase = dbManager!!.writableDatabase
            val cursor: Cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val bill = Bill()
                    bill.id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                    bill.date = sdf!!.parse(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)))

                    listBill.add(bill)
                } while (cursor.moveToNext())
            }
            return listBill
        }

    fun insertBill(bill: Bill){
        val db:SQLiteDatabase = dbManager!!.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID,bill.id)
        values.put(COLUMN_DATE, sdf?.format(bill.date))
        db.insert(TABLE_BILL,null,values)
        db.close()
    }

    fun updateBill(id:String,date:Date) {
        val db:SQLiteDatabase = dbManager!!.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_DATE, sdf!!.format(date))
        db.update(TABLE_BILL,values,"$COLUMN_ID=?", arrayOf(id))
    }

    fun deleteBill(id:String){
        val db:SQLiteDatabase = dbManager!!.writableDatabase
        db.delete(TABLE_BILL,"$COLUMN_ID=?", arrayOf(id))
    }

}