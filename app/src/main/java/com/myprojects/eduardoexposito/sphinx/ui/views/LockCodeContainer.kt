package com.myprojects.eduardoexposito.sphinx.ui.views

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.widget.TextView
import com.myprojects.eduardoexposito.sphinx.helpers.fadeOut
import kotlinx.android.synthetic.main.view_lock_code_container.view.*

class LockCodeContainer : ConstraintLayout {

    companion object {
        private const val GENDARMENMARKT_CODE = "0135"
        private const val CHARLOTTENBURG_CODE = "0248"
        private const val ALEXANDERPLATZ_CODE = "067928.462064"
    }

    private lateinit var lockCodeBoxes: List<TextView>

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()
        lockCodeBoxes = mutableListOf(codeDigit1, codeDigit2, codeDigit3, codeDigit4)
    }

    //========================================================================
    //========================================================================
    fun setUp(textResId: Int) {
        lockCodeText.setText(textResId)
    }

    fun displayGendarmenMarktCode(onClick: () -> Unit) {
        displayCode(GENDARMENMARKT_CODE, lockCodeBoxes, onClick)
    }

    fun displayCharlottenBurgCode(onClick: () -> Unit) {
        displayCode(CHARLOTTENBURG_CODE, lockCodeBoxes, onClick)
    }

    fun displayAlexanderPlatzCode(onClick: () -> Unit) {
        displayCode(ALEXANDERPLATZ_CODE, lockCodeBoxes, onClick)
    }

    //========================================================================
    //========================================================================
    private fun displayCode(code: String, textViews: List<TextView>, onClick: () -> Unit) {
        textViews.forEachIndexed { index, textView ->
            textView.text = code.getOrNull(index)?.toString() ?: ""
        }
        lockCodeContinueButton.setOnClickListener { onClick() }
    }
}
