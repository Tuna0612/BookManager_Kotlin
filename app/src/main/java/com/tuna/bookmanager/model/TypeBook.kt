package com.tuna.bookmanager.model

class TypeBook {
    var id: String? = null
    var name: String? = null
    var position: String? = null
    var description: String? = null

    constructor()

    constructor(name: String?, position: String?, description: String?) {
        this.name = name
        this.position = position
        this.description = description
    }

    constructor(id: String, name: String?, position: String, description: String?) {
        this.id = id
        this.name = name
        this.position = position
        this.description = description
    }

    override fun toString(): String {
        return id.toString() + " | " + name
    }


}