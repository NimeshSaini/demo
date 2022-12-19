package com.utkarshnew.android.Intro

interface ItemSelected {
    fun Selecteditem(item: Int,mastercat: Mastercat)
    fun Selectedsubcat(pos: Int,sucat: SubCat,type: String)
}