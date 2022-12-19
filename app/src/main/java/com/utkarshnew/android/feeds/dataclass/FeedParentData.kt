package com.utkarshnew.android.feeds.dataclass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feedData")
class FeedParentData {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var status: Boolean = false
    var message: String = ""
    var time: Int = 0
    var interval: Int = 0
    var limit: Int = 0
    var cd_time: Long = 0
}