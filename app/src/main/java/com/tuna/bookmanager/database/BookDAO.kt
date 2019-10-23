package com.tuna.bookmanager.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.tuna.bookmanager.model.Book

class BookDAO(context: Context?) : Constant() {
    private var dbManager: DatabaseManager? = null

    init {
        dbManager = DatabaseManager(context)
    }

    val getAllBook: ArrayList<Book>
        @SuppressLint("Recycle")
        get() {
            val listBook = ArrayList<Book>()
            val selectQuery = "SELECT * FROM $TABLE_BOOK"
            val db: SQLiteDatabase = dbManager!!.writableDatabase
            val cursor: Cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val book = Book()
                    book.id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                    book.tenSach = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK))
                    book.maTheLoai = cursor.getString(cursor.getColumnIndex(COLUMN_TYPEBOOK))
                    book.author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR))
                    book.nxb = cursor.getString(cursor.getColumnIndex(COLUMN_NXB))
                    book.price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                    book.total = cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL))
                    listBook.add(book)
                } while (cursor.moveToNext())
            }
            return listBook
        }

    fun insertBook(book: Book) {
        val db: SQLiteDatabase = dbManager!!.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, book.id)
        values.put(COLUMN_BOOK, book.tenSach)
        values.put(COLUMN_TYPEBOOK, book.maTheLoai)
        values.put(COLUMN_AUTHOR, book.author)
        values.put(COLUMN_NXB, book.nxb)
        values.put(COLUMN_PRICE, book.price)
        values.put(COLUMN_TOTAL, book.total)
        db.insert(TABLE_BOOK, null, values)
        db.close()
    }

    fun updateBook(
        id: String,
        name: String,
        maTheLoai: String,
        author: String,
        nxb: String,
        price: Double,
        total: Int
    ) {
        val db: SQLiteDatabase = dbManager!!.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_BOOK, name)
        values.put(COLUMN_TYPEBOOK, maTheLoai)
        values.put(COLUMN_AUTHOR, author)
        values.put(COLUMN_NXB, nxb)
        values.put(COLUMN_PRICE, price)
        values.put(COLUMN_TOTAL, total)
        db.update(TABLE_BOOK, values, "$COLUMN_ID=?", arrayOf(id))
        db.close()
    }

    fun deleteBook(id: String) {
        val db: SQLiteDatabase = dbManager!!.writableDatabase
        db.delete(TABLE_BOOK, "$COLUMN_ID=?", arrayOf(id))
        db.close()
    }

}