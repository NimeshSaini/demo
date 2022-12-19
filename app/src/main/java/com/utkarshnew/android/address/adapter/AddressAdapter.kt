package com.utkarshnew.android.address.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.utkarshnew.android.R
import com.utkarshnew.android.address.adapter.AddressAdapter.AddressAdapterholder
import com.utkarshnew.android.address.interfaces.onItemSelected
import com.utkarshnew.android.address.model.Data
import com.utkarshnew.android.courses.Activity.CourseActivity
import com.utkarshnew.android.databinding.AddressAdapterBinding

class AddressAdapter(var context: Context, var addresslist: List<Data>, var itemSelected: onItemSelected) : RecyclerView.Adapter<AddressAdapterholder>() {
    var oldpos:Int=-1;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressAdapterholder {

        val view = AddressAdapterBinding.inflate(LayoutInflater.from(context), parent, false)
        return AddressAdapterholder(view)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: AddressAdapterholder, position: Int) {
        holder.bind(addresslist[position])

        if (position ==oldpos) {
            holder.addressAdapterBinding.addressItem.setBackgroundDrawable(context.resources.getDrawable(R.drawable.round_gray_address_yello, null))
        } else {
            addresslist[position].selected =false

            holder.addressAdapterBinding.addressItem.setBackgroundDrawable(context.resources.getDrawable(R.drawable.round_gray_address, null))
        }
        holder.addressAdapterBinding.addressItem.setOnClickListener {
            if (context is CourseActivity)
            {
                val copyOfLastCheckedPosition: Int = oldpos
                oldpos = position
                notifyItemChanged(copyOfLastCheckedPosition)
                notifyItemChanged(oldpos)
                itemSelected?.apply {
                    itemSelect(position, addresslist[position])
                }
            }

        }
        holder.addressAdapterBinding.edit.setOnClickListener {
            itemSelected?.apply {
                itemClick(position, addresslist[position])
            }
        }
        holder.addressAdapterBinding.delete.setOnClickListener {
            itemSelected?.apply {
                AlertDialog.Builder(context)
                    .setTitle("Delete Address")
                    .setMessage("Are you sure you want to delete this address?")
                    .setPositiveButton(android.R.string.yes) {
                            dialog, which ->
                        delete_address(position, addresslist[position])

                    }
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
            }
        }

    }

    override fun getItemCount(): Int {
        return addresslist.size
    }

    inner class AddressAdapterholder(val addressAdapterBinding: AddressAdapterBinding) : RecyclerView.ViewHolder(addressAdapterBinding.root) {
        fun bind(coursesCoupon: Data) {
            addressAdapterBinding.addressadapter = coursesCoupon
        }
    }

    fun updateItem(addresslist: List<Data>) {
        this.addresslist = addresslist
        notifyDataSetChanged()
    }
}