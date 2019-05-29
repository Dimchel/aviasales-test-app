package com.dimchel.aviasalestestapp.utils.simpleclasses

import android.animation.Animator

open class SimpleAnimationListener : Animator.AnimatorListener {
    override fun onAnimationRepeat(animation: Animator?) = Unit
    override fun onAnimationEnd(animation: Animator?) = Unit
    override fun onAnimationCancel(animation: Animator?) = Unit
    override fun onAnimationStart(animation: Animator?) = Unit
}