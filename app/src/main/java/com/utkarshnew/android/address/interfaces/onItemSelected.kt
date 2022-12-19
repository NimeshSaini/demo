package com.utkarshnew.android.address.interfaces

import com.utkarshnew.android.address.model.Data

interface onItemSelected {
    fun itemClick(pos:Int,data: Data)
    fun itemSelect(pos:Int,data: Data)
    fun delete_address(pos:Int,data: Data)
}