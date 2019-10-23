package com.tuna.bookmanager.database

import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.tuna.bookmanager.model.TypeBook

class TypeBookDAO(context: Context?) : Constant() {
    private var dbManager: DatabaseManager? = null

    init {
        dbManager = DatabaseManager(context)
    }

    val getAllTypeBook: ArrayList<TypeBook>
        get() {
            val listTypeBook = ArrayList<TypeBook>()
            val selectQuery = "SELECT * FROM $TABLE_TYPEBOOK"
            val db: SQLiteDatabase = dbManager!!.writableDatabase
            val cursor: Cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val typeBook = TypeBook()
                    typeBook.id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                    typeBook.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                    typeBook.position = cursor.getString(cursor.getColumnIndex(COLUMN_POSITION))
                    typeBook.description =
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                    listTypeBook.add(typeBook)
                } while (cursor.moveToNext())
            }
            return listTypeBook
        }

    fun insertTypeBook(typeBook: TypeBook) {
        val db: SQLiteDatabase = dbManager!!.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, typeBook.id)
        values.put(COLUMN_NAME, typeBook.name)
        values.put(COLUMN_POSITION, typeBook.position)
        values.put(COLUMN_DESCRIPTION, typeBook.description)

        db.insert(TABLE_TYPEBOOK, null, values)
        db.close()
    }

    fun updateTypeBook(id: String, name: String, position: String, description: String) {
        val db: SQLiteDatabase = dbManager!!.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_POSITION, position)
        values.put(COLUMN_DESCRIPTION, description)
        db.update(TABLE_TYPEBOOK, values, "$COLUMN_ID=?", arrayOf(id))
        db.close()
    }

    fun deleteTypeBook(typeBook: String) {
        val db: SQLiteDatabase = dbManager!!.writableDatabase
        db.delete(TABLE_TYPEBOOK, "$COLUMN_ID=?", arrayOf(typeBook))
        db.close()
    }
}