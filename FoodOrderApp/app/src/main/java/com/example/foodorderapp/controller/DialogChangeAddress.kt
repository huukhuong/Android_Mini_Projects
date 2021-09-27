package com.example.foodorderapp.controller

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.foodorderapp.R
import com.example.foodorderapp.model.District
import com.example.foodorderapp.model.Province
import com.example.foodorderapp.model.Ward

class DialogChangeAddress(var provinces: ArrayList<Province>?, var txvAddress: TextView) :
    AppCompatDialogFragment() {

    private lateinit var spinnerProvince: Spinner
    private lateinit var spinnerDistrict: Spinner
    private lateinit var spinnerWard: Spinner
    private lateinit var txtAddress: EditText
    private lateinit var btnCancel: Button
    private lateinit var btnSubmit: Button
    private lateinit var adapterProvince: ArrayAdapter<Province>
    private lateinit var adapterDistrict: ArrayAdapter<District>
    private lateinit var adapterWard: ArrayAdapter<Ward>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_change_address, null)
        builder.setView(view)

        addControls(view)
        addEvents()

        return builder.create()
    }

    private fun addControls(view: View) {
        spinnerProvince = view.findViewById(R.id.spinnerProvince)
        spinnerDistrict = view.findViewById(R.id.spinnerDistrict)
        spinnerWard = view.findViewById(R.id.spinnerWard)
        txtAddress = view.findViewById(R.id.txtAddress)
        btnCancel = view.findViewById(R.id.btnCancel)
        btnSubmit = view.findViewById(R.id.btnSubmit)

        adapterProvince = ArrayAdapter(requireContext(), R.layout.spinner_item)
        adapterDistrict = ArrayAdapter(requireContext(), R.layout.spinner_item)
        adapterWard = ArrayAdapter(requireContext(), R.layout.spinner_item)

        adapterProvince.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
        adapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapterWard.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerProvince.adapter = adapterProvince
        spinnerDistrict.adapter = adapterDistrict
        spinnerWard.adapter = adapterWard

        provinces!!.forEach { adapterProvince.add(it) }
        val address: String = txvAddress.text.toString()
        txtAddress.setText(address)
    }


    private fun addEvents() {
        spinnerProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                onSelectProvince(i)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        spinnerDistrict.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                onSelectDistrict(i)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        spinnerWard.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                onSelectWard(i)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        btnCancel.setOnClickListener { closeDialog() }
        btnSubmit.setOnClickListener { updateAddress() }
    }

    private fun updateAddress() {
        txvAddress.text =
            "${txtAddress.text.toString()}, ${spinnerWard.selectedItem.toString()}, ${spinnerDistrict.selectedItem.toString()}, ${spinnerProvince.selectedItem.toString()}"
        closeDialog()
    }

    private fun closeDialog() {
        this.dismiss()
    }

    private fun onSelectProvince(i: Int) {
        val province: Province? = adapterProvince.getItem(i)
        val districts: ArrayList<District> = province!!.districts
        adapterDistrict.clear()
        districts.forEach { adapterDistrict.add(it) }
        adapterDistrict.notifyDataSetChanged()
    }

    private fun onSelectDistrict(i: Int) {
        val district: District? = adapterDistrict.getItem(i)
        val wards: ArrayList<Ward> = district!!.wards
        adapterWard.clear()
        wards.forEach { adapterWard.add(it) }
        adapterWard.notifyDataSetChanged()
    }

    private fun onSelectWard(i: Int) {
    }

}