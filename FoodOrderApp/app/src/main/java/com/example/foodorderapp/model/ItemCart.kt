package com.example.foodorderapp.model

class ItemCart {

    var pId: Int = 0
    lateinit var img: String
    lateinit var name: String
    lateinit var details: String
    var quantity: Int = 0

    constructor() {}

    constructor(pId: Int, img: String, name: String, details: String, quantity: Int) : this() {
        this.pId = pId
        this.img = img
        this.name = name
        this.details = details
        this.quantity = quantity
    }

}