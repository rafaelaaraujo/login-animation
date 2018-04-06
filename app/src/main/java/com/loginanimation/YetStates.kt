package com.loginanimation

/**
 * Created by rafaela.araujo on 05/04/18.
 */
object YetStates {

    val STATE_INITIAL = intArrayOf(
            R.attr.initial, -R.attr.looking, -R.attr.hidding_eyes, -R.attr.email_focus, -R.attr.laughing
    )

    val STATE_LOOKING = intArrayOf(
            -R.attr.initial, R.attr.looking, -R.attr.hidding_eyes, -R.attr.email_focus, -R.attr.laughing
    )

    val STATE_FOCUS = intArrayOf(
            -R.attr.initial, -R.attr.looking, R.attr.hidding_eyes, -R.attr.email_focus, -R.attr.laughing
    )

    val STATE_SMILE = intArrayOf(
            -R.attr.initial, -R.attr.looking, -R.attr.hidding_eyes, -R.attr.email_focus, R.attr.laughing
    )

    val STATE_NEUTRAL = intArrayOf(
            -R.attr.initial, R.attr.looking, -R.attr.hidding_eyes, -R.attr.email_focus, -R.attr.laughing
    )

}