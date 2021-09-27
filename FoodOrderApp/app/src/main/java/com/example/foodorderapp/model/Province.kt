package com.example.foodorderapp.model

class Province(var name: String, var districts: ArrayList<District>) {

    override fun toString(): String {
        return name
    }

}