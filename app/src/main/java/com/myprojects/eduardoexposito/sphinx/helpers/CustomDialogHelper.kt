package com.myprojects.eduardoexposito.sphinx.helpers

import android.content.Context
import android.support.v7.app.AlertDialog
import com.myprojects.eduardoexposito.sphinx.R

//======================================================================================
// Cancelable dialog
//======================================================================================
fun createCancelableDialog(context: Context, titleId: Int?, messageId: Int) =
    AlertDialog.Builder(context, R.style.AlertDialogTheme).apply {
        titleId?.let { setTitle(getContext().getString(it)) }
        setMessage(getContext().getString(messageId))
        setCancelable(true)
    }.create()!!

//======================================================================================
// Info dialog
//======================================================================================
fun createInfoDialog(context: Context, titleId: Int?, messageId: Int, positiveAction: () -> Unit = {}) =
    createInfoDialog(context, titleId, context.getString(messageId), positiveAction)

fun createInfoDialog(context: Context, titleId: Int?, message: String, positiveAction: () -> Unit = {}) =
    AlertDialog.Builder(context, R.style.AlertDialogTheme).apply {
        titleId?.let { setTitle(getContext().getString(it)) }
        setMessage(message)
        setCancelable(false)
        setPositiveButton(getContext().getString(R.string.dialog_positive_button)) { dialog, _ ->
            positiveAction.invoke()
            dialog.cancel()
        }
    }.create()!!

//======================================================================================
// Action/Cancel dialog
//======================================================================================
fun createDialog(
    context: Context,
    titleId: Int?,
    messageId: Int,
    positiveActionTextId: Int = R.string.dialog_positive_button,
    positiveAction: () -> Unit = {},
    negativeActionTextId: Int = R.string.dialog_negative_button,
    negativeAction: () -> Unit = {}
) =
    AlertDialog.Builder(context, R.style.AlertDialogTheme).apply {
        titleId?.let { setTitle(getContext().getString(it)) }
        setMessage(getContext().getString(messageId))
        setCancelable(false)
        setPositiveButton(getContext().getString(positiveActionTextId)) { dialog, _ ->
            positiveAction.invoke()
            dialog.cancel()
        }
        setNegativeButton(getContext().getString(negativeActionTextId)) { dialog, _ ->
            negativeAction.invoke()
            dialog.cancel()
        }
    }.create()!!
