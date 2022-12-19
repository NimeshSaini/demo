package com.utkarshnew.android.address.model

data class AddressModel(
    val cd_time: Long,
    val `data`: List<Data>,
    val interval: Int,
    val limit: Int,
    val message: String,
    val status: Boolean,
    val time: Int
)