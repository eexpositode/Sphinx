package com.myprojects.eduardoexposito.sphinx.ui.views

import android.content.Context
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.AppCompatButton
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.text.method.ScrollingMovementMethod
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.view_dialog_container.view.*

class DialogContainerView : ConstraintLayout {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        currentDialog.movementMethod = ScrollingMovementMethod()
    }

    //============================================================================
    //============================================================================
    fun setUp(
        dialogTextResId: Int,
        positiveButtonTextResId: Int? = null,
        positiveButtonAction: () -> Unit = {},
        negativeButtonTextResId: Int? = null,
        negativeButtonAction: () -> Unit = {}
    ) = setUp(
        resources.getString(dialogTextResId),
        positiveButtonTextResId,
        positiveButtonAction,
        negativeButtonTextResId,
        negativeButtonAction
    )

    fun setUp(
        dialogText: String,
        positiveButtonTextResId: Int? = null,
        positiveButtonAction: () -> Unit = {},
        negativeButtonTextResId: Int? = null,
        negativeButtonAction: () -> Unit = {}
    ) {
        currentDialog.apply {
            text = dialogText
            scrollTo(0, 0)
        }
        positiveButton.setUpButton(positiveButtonTextResId, positiveButtonAction)
        negativeButton.setUpButton(negativeButtonTextResId, negativeButtonAction)
    }

    private fun AppCompatButton.setUpButton(textResId: Int?, onClickAction: () -> Unit) {
        if (textResId == null) {
            visibility = View.INVISIBLE
        } else {
            visibility = View.VISIBLE
            setText(textResId)
            setOnClickListener { onClickAction() }
        }
    }
}
