package com.tuna.bookmanager.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.tuna.bookmanager.database.Constant.Companion.SQL_BILL
import com.tuna.bookmanager.database.Constant.Companion.SQL_BOOK
import com.tuna.bookmanager.database.Constant.Companion.SQL_PROFILE
import com.tuna.bookmanager.database.Constant.Companion.SQL_TYPEBOOK
import com.tuna.bookmanager.database.Constant.Companion.TABLE_BILL
import com.tuna.bookmanager.database.Constant.Companion.TABLE_BOOK
import com.tuna.bookmanager.database.Constant.Companion.TABLE_PROFILE
import com.tuna.bookmanager.database.Constant.Companion.TABLE_TYPEBOOK

class DatabaseManager(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        const val DATABASE_NAME = "Bookmanager.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_PROFILE)
        db.execSQL(SQL_TYPEBOOK)
        db.execSQL(SQL_BOOK)
        db.execSQL(SQL_BILL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_PROFILE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TYPEBOOK")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BOOK")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BILL")
        onCreate(db)
    }

}