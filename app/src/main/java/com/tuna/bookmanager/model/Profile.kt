package com.tuna.bookmanager.model

class Profile {
    var username: String? = null
    var name: String? = null
    var password: String? = null
    var phone: String? = null

    constructor()

    constructor(username: String?, name: String?, password: String?, phone: String?) {
        this.username = username
        this.name = name
        this.password = password
        this.phone = phone
    }
}