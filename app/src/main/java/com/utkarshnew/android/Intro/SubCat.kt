package com.utkarshnew.android.Intro

import androidx.room.ColumnInfo

data class SubCat(var id:String="",var name:String,var parenid:String,var mastertype:String,var is_selct:Boolean =false,var is_subcatselct:Boolean =false,var is_maincatselct:Boolean =false)

