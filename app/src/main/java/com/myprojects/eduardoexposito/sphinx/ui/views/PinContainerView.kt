package com.myprojects.eduardoexposito.sphinx.ui.views

import android.content.Context
import android.graphics.PorterDuff
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import com.myprojects.eduardoexposito.sphinx.R
import kotlinx.android.synthetic.main.view_pin_container.view.*

class PinContainerView : ConstraintLayout {

    companion object {
        private const val VALID_CODE = "8427"
    }

    interface OnEnterCodeListener {
        fun onEnterRightCode()
        fun onEnterWrongCode()
    }

    private var onEnterCodeListener: OnEnterCodeListener? = null
    private var pinCodes = mutableListOf<View>()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    fun setUp(
        onEnterCodeListener: OnEnterCodeListener,
        textResId: Int
    ) {
        setUp(onEnterCodeListener, resources.getString(textResId))
    }

    fun setUp(
        onEnterCodeListener: OnEnterCodeListener,
        text: String
    ) {
        this.onEnterCodeListener = onEnterCodeListener
        initPinCodes()
        pinKey1.onClick(R.drawable.ic_pin_key_1_black_24dp, "1")
        pinKey2.onClick(R.drawable.ic_pin_key_2_black_24dp, "2")
        pinKey3.onClick(R.drawable.ic_pin_key_3_black_24dp, "3")
        pinKey4.onClick(R.drawable.ic_pin_key_4_black_24dp, "4")
        pinKey5.onClick(R.drawable.ic_pin_key_5_black_24dp, "5")
        pinKey6.onClick(R.drawable.ic_pin_key_6_black_24dp, "6")
        pinKey7.onClick(R.drawable.ic_pin_key_7_black_24dp, "7")
        pinKey8.onClick(R.drawable.ic_pin_key_8_black_24dp, "8")

        actionCommandDisplay.text = text
    }

    //============================================================================
    //============================================================================
    private fun initPinCodes() {
        pinCodes.apply {
            clear()
            addAll(listOf(pinCode1, pinCode2, pinCode3, pinCode4))
        }.forEach { view ->
            (view as? AppCompatImageView)?.let {
                it.tag = null
                it.setImageDrawable(null)
            }
        }
    }

    private fun enterKey(drawableResId: Int, code: String) {
        (pinCodes.firstOrNull { it.tag == null } as? AppCompatImageView)?.setView(code, drawableResId)
        if (pinCodes.indexOfFirst { it.tag == null } < 0) {
            if (pinCodes.map { it.tag as String }.reduce { key, anotherKey -> key + anotherKey } == VALID_CODE) {
                onEnterCodeListener?.onEnterRightCode()
            } else {
                onEnterCodeListener?.onEnterWrongCode()
            }
        }
    }

    private fun AppCompatImageView.setView(tag: String, drawableResId: Int) = this.apply {
        this.tag = tag
        setImageDrawable(
            ContextCompat.getDrawable(context, drawableResId)?.apply {
                mutate()
                setColorFilter(ContextCompat.getColor(context, R.color.pin_code_entered_image_color), PorterDuff.Mode.SRC_IN)
            }
        )
    }

    private fun AppCompatImageView.onClick(drawableResId: Int, code: String) = this.apply {
        setOnClickListener { enterKey(drawableResId, code) }
    }
}
