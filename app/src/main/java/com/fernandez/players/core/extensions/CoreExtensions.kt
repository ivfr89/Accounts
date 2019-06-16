package com.fernandez.players.core.extensions

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.fernandez.players.R
import com.fernandez.players.core.ModelEntity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T: ModelEntity> String.fromJson(): T = Gson().fromJson<T>(this, object: TypeToken<T>(){}.type)

inline fun <reified T: ModelEntity> T.toJson(): String? {
    return Gson().toJson(this,object: TypeToken<T>(){}.type)
}


val Fragment.appContext : Context get() = activity?.applicationContext!!


fun Fragment.notifyWithAction(container: View, @StringRes message: Int, @StringRes actionText: Int, action: () -> Any) {
    val snackBar = Snackbar.make(container, message, Snackbar.LENGTH_INDEFINITE)
    snackBar.setAction(actionText) { _ -> action.invoke() }
    snackBar.setActionTextColor(
        ContextCompat.getColor(appContext,
            R.color.colorPrimary))
    snackBar.show()
}