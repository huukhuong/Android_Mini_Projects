package com.example.foodorderapp.model

class Ward(var name: String, var prefix: String) {

    override fun toString(): String {
        return "$prefix $name"
    }

}