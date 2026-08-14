package com.example.supercartapp.util

import android.view.View

fun View.hide(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hideRest(vararg views: View){
    this.visibility = View.VISIBLE
    views.forEach {
        it.visibility = View.GONE
    }
}