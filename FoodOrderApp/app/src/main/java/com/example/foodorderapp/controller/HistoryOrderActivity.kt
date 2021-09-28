package com.example.foodorderapp.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
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
                    "https://cdn.huongnghiepaau.com/wp-content/uploads/2019/01/hamburger-nhan-thit-heo.jpg",
                    "Hamburger",
                    "Delicious",
                    3
                )
            )
        }
        var adapter = AdapterItemCart(list)
        rcv.layoutManager = LinearLayoutManager(this)
        rcv.adapter = adapter
    }

    private fun addEvents() {

    }

}