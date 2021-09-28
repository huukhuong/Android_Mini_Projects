package com.example.foodorderapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodorderapp.R
import com.example.foodorderapp.model.ItemCart
import com.squareup.picasso.Picasso

class AdapterItemCart(private var list: ArrayList<ItemCart>) : RecyclerView.Adapter<AdapterItemCart.ItemCartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCartViewHolder {
        return ItemCartViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cart_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemCartViewHolder, position: Int) {
        val item = list[position]
        Picasso.get().load(item.img).into(holder.imgProduct)
        holder.txvProductName.text = item.name
        holder.txvProductDetail.text = item.details
        holder.txvQuantity.text = item.quantity.toString()
        holder.txvPrice.text = item.pId.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ItemCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgProduct: ImageView = itemView.findViewById(R.id.imgProduct)
        var txvProductName: TextView = itemView.findViewById(R.id.txvProductName)
        var txvProductDetail: TextView = itemView.findViewById(R.id.txvProductDetail)
        var txvPrice: TextView = itemView.findViewById(R.id.txvPrice)
        var btnRemove: ImageButton = itemView.findViewById(R.id.btnRemove)
        var txvQuantity: TextView = itemView.findViewById(R.id.txvQuantity)
        var btnAdd: ImageButton = itemView.findViewById(R.id.btnAdd)

        init {
            btnAdd.setOnClickListener { addQuantity() }
            btnRemove.setOnClickListener { minusQuantity() }
        }

        private fun minusQuantity() {
            var qty: Int = txvQuantity.text.toString().toInt()
            qty -= 1
            txvQuantity.setText(qty)
        }

        private fun addQuantity() {
            var qty: Int = txvQuantity.text.toString().toInt()
            qty += 1
            txvQuantity.setText(qty)
        }

    }

}