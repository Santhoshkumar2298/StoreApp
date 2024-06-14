package com.santhoshkumar.storeapp.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.santhoshkumar.storeapp.R

// Common Utility Class contains methods which are repeatedly used in multiple classes
class CommonUtils {
    companion object{
        private lateinit var dialog: AlertDialog

        fun showLoadingDialog(context: Context) {
            val dialogBuilder = MaterialAlertDialogBuilder(context)
            dialogBuilder.setView(
                LayoutInflater.from(context).inflate(R.layout.loader_layout, null)
            )
                .setCancelable(false)
            dialog = dialogBuilder.create()
            dialog.show()
        }

        fun dismissLoadingDialog() {
            dialog.dismiss()
        }

        fun makeToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}