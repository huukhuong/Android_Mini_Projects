package com.example.foodorderapp.model

class District(var name: String, var wards: ArrayList<Ward>) {

    override fun toString(): String {
        return name
    }

}