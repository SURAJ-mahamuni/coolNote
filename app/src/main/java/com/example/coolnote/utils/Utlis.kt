package com.example.coolnote.utils

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.backPressedHandle(handle: () -> Unit) {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            handle()
        }
    }
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
}

fun Fragment.toastMsg(msg : String){
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

@SuppressLint("ClickableViewAccessibility")
fun Fragment.setupUI(view: View, handle: () -> Unit) {

    // Set up touch listener for non-text box views to hide keyboard.
    if (view !is EditText) {
        view.setOnTouchListener { v, event ->
            hideSoftKeyboard()
            handle()
            false
        }
    }

    //If a layout container, iterate over children and seed recursion.
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val innerView = view.getChildAt(i)
            setupUI(innerView, handle)
        }
    }
}

fun Fragment.hideSoftKeyboard() {
    requireActivity().currentFocus?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}