package com.myprojects.eduardoexposito.sphinx.helpers

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.view.animation.AccelerateInterpolator
import com.myprojects.eduardoexposito.sphinx.R

//=======================================================================================
// SPHINX AVATAR
//=======================================================================================
enum class SphinxAvatarAnimations {
    ICON_TO_PIC_TOP,
    ICON_TO_PIC_CENTER,
    PIC_TO_ICON,
    PIC_CENTER_TO_TOP,
    PIC_TOP_TO_CENTER

}

fun ConstraintLayout.translateSphinxAvatar(translation: SphinxAvatarAnimations, duration: Long) {
    TransitionManager.beginDelayedTransition(
        this,
        ChangeBounds().setDuration(duration).setInterpolator(AccelerateInterpolator())
    )
    ConstraintSet()
        .apply {
            clone(this@translateSphinxAvatar)
            when (translation) {
                SphinxAvatarAnimations.ICON_TO_PIC_TOP -> sphinxAvatarIconToPicTop(context.resources.getDimension(R.dimen.avatar_top_pic_size).toInt())
                SphinxAvatarAnimations.ICON_TO_PIC_CENTER -> sphinxAvatarIconToPicCenter(context.resources.getDimension(R.dimen.avatar_pic_size).toInt())
                SphinxAvatarAnimations.PIC_TO_ICON -> sphinxAvatarPicToIcon(context.resources.getDimension(R.dimen.avatar_icon_size).toInt())
                SphinxAvatarAnimations.PIC_CENTER_TO_TOP -> sphinxAvatarPicCenterToTop(context.resources.getDimension(R.dimen.avatar_top_pic_size).toInt())
                SphinxAvatarAnimations.PIC_TOP_TO_CENTER -> sphinxAvatarPicTopToCenter(context.resources.getDimension(R.dimen.avatar_pic_size).toInt())
            }
        }
        .applyTo(this)
}

fun ConstraintSet.sphinxAvatarPicCenterToTop(iconDimen: Int) {
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.TOP,
        R.id.guideLineHPercent05,
        ConstraintSet.TOP
    )
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.BOTTOM,
        R.id.guideLineHPercent5,
        ConstraintSet.TOP
    )
    constrainWidth(R.id.sphinxAvatarPic, iconDimen)
    constrainHeight(R.id.sphinxAvatarPic, iconDimen)
}

fun ConstraintSet.sphinxAvatarPicTopToCenter(iconDimen: Int) {
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.TOP,
        R.id.guideLineHPercent2,
        ConstraintSet.TOP
    )
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.BOTTOM,
        R.id.guideLineHPercent8,
        ConstraintSet.TOP
    )
    constrainWidth(R.id.sphinxAvatarPic, iconDimen)
    constrainHeight(R.id.sphinxAvatarPic, iconDimen)
}

fun ConstraintSet.sphinxAvatarPicToIcon(iconDimen: Int) {
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.START,
        R.id.dialogView,
        ConstraintSet.START
    )
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.TOP,
        R.id.dialogView,
        ConstraintSet.TOP
    )
    clear(R.id.sphinxAvatarPic, ConstraintSet.END)
    clear(R.id.sphinxAvatarPic, ConstraintSet.BOTTOM)
    constrainWidth(R.id.sphinxAvatarPic, iconDimen)
    constrainHeight(R.id.sphinxAvatarPic, iconDimen)
}

fun ConstraintSet.sphinxAvatarIconToPicCenter(picDimen: Int) {
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.START,
        R.id.guideLineVPercent05,
        ConstraintSet.START
    )
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.TOP,
        R.id.guideLineHPercent2,
        ConstraintSet.TOP
    )
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.END,
        R.id.guideLineVPercent95,
        ConstraintSet.END
    )
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.BOTTOM,
        R.id.guideLineHPercent8,
        ConstraintSet.BOTTOM
    )
    constrainWidth(R.id.sphinxAvatarPic, picDimen)
    constrainHeight(R.id.sphinxAvatarPic, picDimen)
}

fun ConstraintSet.sphinxAvatarIconToPicTop(picDimen: Int) {
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.START,
        R.id.guideLineVPercent05,
        ConstraintSet.START
    )
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.TOP,
        R.id.guideLineHPercent05,
        ConstraintSet.TOP
    )
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.END,
        R.id.guideLineVPercent95,
        ConstraintSet.END
    )
    connect(
        R.id.sphinxAvatarPic,
        ConstraintSet.BOTTOM,
        R.id.guideLineHPercent5,
        ConstraintSet.BOTTOM
    )
    constrainWidth(R.id.sphinxAvatarPic, picDimen)
    constrainHeight(R.id.sphinxAvatarPic, picDimen)
}
