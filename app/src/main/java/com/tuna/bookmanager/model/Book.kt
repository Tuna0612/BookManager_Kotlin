package com.tuna.bookmanager.model

class Book {
    var id: String? = null
    var maTheLoai: String? = null
    var tenSach: String? = null
    var author: String? = null
    var nxb: String? = null
    var price: Double? = null
    var total: Int? = null

    constructor()

    constructor(
        maTheLoai: String?,
        tenSach: String?,
        author: String?,
        nxb: String?,
        price: Double?,
        total: Int?
    ) {
        this.maTheLoai = maTheLoai
        this.tenSach = tenSach
        this.author = author
        this.nxb = nxb
        this.price = price
        this.total = total
    }

    constructor(
        id: String?,
        maTheLoai: String?,
        tenSach: String?,
        author: String?,
        nxb: String?,
        price: Double?,
        total: Int?
    ) {
        this.id = id
        this.maTheLoai = maTheLoai
        this.tenSach = tenSach
        this.author = author
        this.nxb = nxb
        this.price = price
        this.total = total
    }

}