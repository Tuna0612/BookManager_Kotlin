package com.tuna.bookmanager.database

open class Constant {
    companion object {
        internal const val TABLE_PROFILE = "Profile"
        internal const val COLUMN_USERNAME = "Username"
        internal const val COLUMN_NAME = "Name"
        internal const val COLUMN_PASSWORD = "Password"
        internal const val COLUMN_PHONE = "Phone"
        internal const val SQL_PROFILE =
            ("CREATE TABLE $TABLE_PROFILE($COLUMN_USERNAME NVARCHAR(50) PRIMARY KEY,$COLUMN_NAME NVARCHAR(50),$COLUMN_PASSWORD NVARCHAR(50),$COLUMN_PHONE VARCHAR);")

        internal const val TABLE_TYPEBOOK = "TypeBook"
        internal const val COLUMN_ID = "ID"
        internal const val COLUMN_POSITION = "Position"
        internal const val COLUMN_DESCRIPTION = "Description"
        internal const val SQL_TYPEBOOK =
            ("CREATE TABLE $TABLE_TYPEBOOK($COLUMN_ID NVARCHAR(50) PRIMARY KEY,$COLUMN_NAME NVARCHAR(50),$COLUMN_POSITION NVARCHAR(50),$COLUMN_DESCRIPTION NVARCHAR(50));")


        internal const val TABLE_BOOK = "Book"
        internal const val COLUMN_TYPEBOOK = "maTheLoai"
        internal const val COLUMN_BOOK = "Book"
        internal const val COLUMN_AUTHOR = "Author"
        internal const val COLUMN_NXB = "Nxb"
        internal const val COLUMN_PRICE = "Price"
        internal const val COLUMN_TOTAL = "Total"
        internal const val SQL_BOOK =
            ("CREATE TABLE $TABLE_BOOK($COLUMN_ID NVARCHAR(50) primary key, $COLUMN_TYPEBOOK NVARCHAR(50), $COLUMN_BOOK NVARCHAR(50), $COLUMN_AUTHOR NVARCHAR(50), $COLUMN_NXB NVARCHAR(50), $COLUMN_PRICE TEXT, $COLUMN_TOTAL INT);")

        internal const val TABLE_BILL = "Bill"
        internal const val COLUMN_DATE =  "date"
        internal const val SQL_BILL = "CREATE TABLE $TABLE_BILL($COLUMN_ID NVARCHAR(50) PRIMARY KEY, $COLUMN_DATE DATE);"

    }
}