package com.example.foodorderapp.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderapp.R
import com.example.foodorderapp.adapter.AdapterItemCart
import com.example.foodorderapp.model.ItemCart

class HistoryOrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_order)

        addControls()
        addEvents()
    }

    private fun addControls() {
        var rcv: RecyclerView = findViewById(R.id.rcv)
        var list = ArrayList<ItemCart>()
        for (i in 1..10) {
            list.add(
                ItemCart(
                    i,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGlT4-2sZGD2ogTEacK-C2iNMK59xNBYL6NYcSmfe2rI35XIiJPygT0DdIP1dyo8FkeW0&usqp=CAU",
                    "Hamburger",
                    "Delicious",
                    3
                )
            )
        }
        var adapter = AdapterItemCart(list)
        rcv.adapter = adapter
    }

    private fun addEvents() {

    }

}