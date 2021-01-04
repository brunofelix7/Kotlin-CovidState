package com.brunofelixdev.kotlincovidstate.extension

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.brunofelixdev.kotlincovidstate.R

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.log(message: String) {
    Log.i(this.resources.getString(R.string.log_tag), message)
}