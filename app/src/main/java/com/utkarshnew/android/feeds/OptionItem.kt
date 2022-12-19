package com.utkarshnew.android.feeds

import com.utkarshnew.android.feeds.dataclass.Option

interface OptionItem {
    fun itemSelect(option: Option, index:Int, feedlistpos:Int)
}