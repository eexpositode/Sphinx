package com.myprojects.eduardoexposito.sphinx.helpers

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.graphics.drawable.TransitionDrawable
import android.support.annotation.IdRes
import android.support.constraint.Barrier
import android.support.constraint.Guideline
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import com.myprojects.eduardoexposito.sphinx.R


/**
 * There is an issue when observing [LiveData] in the [android.support.v4.app.Fragment.onViewCreated]
 * method. Since fragments can be detached and re-attached from activity without being actually
 * destroyed, every time we enter the fragment a new observer is attached to the LiveData,
 * driving eventually to memory leaks.
 * <p>
 * https://medium.com/@BladeCoder/architecture-components-pitfalls-part-1-9300dd969808
 * <p>
 * In order to avoid this, observers have to be attached to a LiveData using this method that
 * first tries to remove the observer before actually attaching a new one.
 */
fun <T> LiveData<T>.observeWithUnsubscription(owner: LifecycleOwner, observerFunction: (T) -> (Unit)) {
    removeObservers(owner)
    observe(owner, Observer { it?.let(observerFunction) })
}

fun <T : View> View.bind(@IdRes res: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(res) }
}

//=======================================================================================
//
//=======================================================================================
fun View.fadeIn(duration: Long = 0L, startDelay: Long = 0L, interpolator: Interpolator = LinearInterpolator()) =
    run {
        isEnabled = true
        if (alpha == 0.0F) {
            (this as? ViewGroup)?.showChildren()
            animate()
                .alpha(1.0F)
                .setStartDelay(startDelay)
                .setDuration(duration)
                .setInterpolator(interpolator)
        }
    }

fun View.fadeOut(
    duration: Long = 0L,
    startDelay: Long = 0L,
    interpolator: Interpolator = LinearInterpolator()
) =
    run {
        isEnabled = false
        if (alpha == 1.0F) {
            (this as? ViewGroup)?.hideChildren()
            animate()
                .alpha(0.0F)
                .setStartDelay(startDelay)
                .setDuration(duration)
                .setInterpolator(interpolator)
        }
    }

fun ViewGroup.showChildren() {
    for (i in 0 until childCount) {
        getChildAt(i).let { view ->
            if (view is ViewGroup) {
                view.showChildren()
                view.alpha = 1.0F
            } else if (view !is Guideline && view !is Barrier) {
                view.alpha = 1.0F
                view.isEnabled = true
                bringToFront()
            }
        }
    }
}

fun ViewGroup.hideChildren() {
    for (i in 0 until childCount) {
        getChildAt(i).let { view ->
            if (view is ViewGroup) {
                view.hideChildren()
                view.alpha = 0.0F
            } else if (view !is Guideline && view !is Barrier) {
                view.alpha = 0.0F
                view.isEnabled = false
            }
        }
    }
}

//=======================================================================================
//
//=======================================================================================
enum class BgAnimationType(val bgDrawableResId: Int) {
    TO_FAILURE(R.drawable.bg_main_container_failure_gradient),
    TO_SUCCESS(R.drawable.bg_main_container_success_gradient),
    BACK(R.drawable.bg_main_container_gradient)
}

fun View.animateBackgroundTo(type: BgAnimationType, duration: Int) {
    val newBG = ContextCompat.getDrawable(context, type.bgDrawableResId)
    if (background == null) {
        background = newBG
    } else {
        val transitionDrawable = TransitionDrawable(arrayOf(background, newBG))
        transitionDrawable.isCrossFadeEnabled = true
        background = transitionDrawable
        transitionDrawable.startTransition(duration)
    }
}

