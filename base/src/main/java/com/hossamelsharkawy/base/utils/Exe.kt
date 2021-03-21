package com.hossamelsharkawy.base.utils

import android.R
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.annotation.StringRes

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Created by hossam on 31/10/16.
 */

object Exe {
    fun dialogInfo(mContext: Context, Message: String) {
        AlertDialog.Builder(mContext)
                .setMessage(Message)
                .setCancelable(false)
                .setPositiveButton(
                        R.string.ok
                ) { _, _ ->
                }.show()
    }

    fun dialogInfo(mContext: Context,title:String, Message: String, resIcon: Int, Positive:DialogInterface.OnClickListener) {
        AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(Message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, Positive)
                .setIcon(resIcon).show()
    }

    fun massage(mContext: Context, massage: String) {
        Toast.makeText(mContext, massage, Toast.LENGTH_LONG).show()
    }

    fun massage(mContext: Context, @StringRes massageId: Int) {
        Toast.makeText(mContext, mContext.getString(massageId), Toast.LENGTH_LONG).show()
    }

    fun alertDialogInfo(mContext: Context, Message: String) {

        AlertDialog.Builder(mContext)
                .setMessage(Message)
                .setPositiveButton(android.R.string.yes) { dialog, which -> dialog.cancel() }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
    }

    fun alertDialogInfo(mContext: Context, Message: String, Positive: () -> Unit) {

        AlertDialog.Builder(mContext)
                .setMessage(Message)
                .setCancelable(false)
                .setPositiveButton(
                        android.R.string.yes
                ) { _, _ ->
                    Positive()
                }.setIcon(android.R.drawable.ic_dialog_alert).show()
    }

    fun alertDialog(
            mContext: Context, title: String, Message: String,
            Positive: DialogInterface.OnClickListener

    ): AlertDialog.Builder {

        return AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(Message)
                .setPositiveButton(R.string.yes, Positive)
                .setNegativeButton(R.string.no) { dialog, which -> dialog.cancel() }
                .setIcon(R.drawable.ic_dialog_alert)
    }


    fun alertDialog(
            mContext: Context, title: String, Message: String,
            Positive: DialogInterface.OnClickListener,
            Cancel: DialogInterface.OnCancelListener
    ): AlertDialog.Builder {
        return AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(Message)
                .setPositiveButton(R.string.yes, Positive)
                .setNegativeButton(R.string.no) { dialog, which ->
                    Cancel.onCancel(dialog)
                    dialog.cancel()
                }
                .setIcon(R.drawable.ic_dialog_alert)
    }

    fun DecimalFormatTwo(number: Double): Double {
        val decimalFormat = DecimalFormat("##.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH))

        return java.lang.Double.parseDouble(decimalFormat.format(number))
    }

    fun getProgressDialog(context: Context): ProgressDialog {

        val progressDialog = ProgressDialog(context)

        progressDialog.setMessage("Loading...")

        progressDialog.setCancelable(false)
        return progressDialog
    }

    fun getProgressDialog(context: Context, title: String): ProgressDialog {

        val progressDialog = ProgressDialog(context)

        progressDialog.setMessage(title)

        progressDialog.setCancelable(false)
        return progressDialog
    }


}