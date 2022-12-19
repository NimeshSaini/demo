package com.utkarshnew.android.feeds.dataclass

data class FeedModel(
    val cd_time: Long,
    val `data`: List<Data>,
    val interval: Int,
    val limit: Int,
    val message: String,
    val status: Boolean,
    val time: Int
)