package com.brunofelixdev.kotlincovidstate.extension

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.brunofelixdev.kotlincovidstate.util.APP_TAG

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.log(message: String) {
    Log.i(APP_TAG, message)
}