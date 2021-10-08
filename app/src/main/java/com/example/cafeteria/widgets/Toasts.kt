package com.evapharma.cafeteriaapp
import android.content.Context
import android.widget.Toast

fun shortToast(context: Context, msg:String){
    Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
}

fun longToast(context: Context,msg:String){
    Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
}