package com.tuna.bookmanager.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.tuna.bookmanager.model.Profile

class ProfileDAO(context: Context?) : Constant() {


    private var dbManager: DatabaseManager? = null

    init {
        dbManager = DatabaseManager(context)
    }


    val getAllProfile: ArrayList<Profile>
        @SuppressLint("Recycle")
        get() {
            val listStudent = ArrayList<Profile>()
            val selectQuery = "SELECT * FROM $TABLE_PROFILE"
            val db: SQLiteDatabase = dbManager!!.writableDatabase
            val cursor: Cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val profile = Profile()
                    profile.username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME))
                    profile.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                    profile.password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
                    profile.phone = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE))
                    listStudent.add(profile)
                } while (cursor.moveToNext())
            }
            return listStudent
        }

    fun addProfile(profile: Profile) {
        val db: SQLiteDatabase = dbManager!!.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USERNAME, profile.username)
        values.put(COLUMN_NAME, profile.name)
        values.put(COLUMN_PASSWORD, profile.password)
        values.put(COLUMN_PHONE, profile.phone)
        db.insert(TABLE_PROFILE, null, values)
        db.close()
    }

    fun updateProfile(username: String, name: String, phone: String) {
        val db: SQLiteDatabase = dbManager!!.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_NAME, name)
        values.put(COLUMN_PHONE, phone)

        db.update(TABLE_PROFILE, values, "$COLUMN_USERNAME=?", arrayOf(username))
        db.close()
    }

    fun deleteProfile(username: String) {
        val db: SQLiteDatabase = dbManager!!.writableDatabase
        db.delete(TABLE_PROFILE, "$COLUMN_USERNAME=?", arrayOf(username))
        db.close()
    }
}