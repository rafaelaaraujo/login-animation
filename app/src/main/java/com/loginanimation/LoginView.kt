package com.loginanimation

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.login_view.view.*
import com.devs.vectorchildfinder.VectorChildFinder

/**
 * Created by rafaela.araujo on 02/04/18.
 */
class LoginView(context: Context, attr: AttributeSet? = null) : FrameLayout(context, attr), TextWatcher {

    private lateinit var vector: VectorChildFinder

    private val STATE_INITIAL = intArrayOf(
            R.attr.initial, -R.attr.looking, -R.attr.hidding_eyes, -R.attr.email_focus, -R.attr.laughing
    )

    private val STATE_LOOKING = intArrayOf(
            -R.attr.initial, R.attr.looking, -R.attr.hidding_eyes, -R.attr.email_focus, -R.attr.laughing
    )

    private val STATE_FOCUS = intArrayOf(
            -R.attr.initial, -R.attr.looking, R.attr.hidding_eyes, -R.attr.email_focus, -R.attr.laughing
    )

    private val STATE_LAUGHING = intArrayOf(
            -R.attr.initial, -R.attr.looking, -R.attr.hidding_eyes, -R.attr.email_focus, R.attr.laughing
    )

    private val STATE_NEUTRAL = intArrayOf(
            -R.attr.initial, R.attr.looking, -R.attr.hidding_eyes, -R.attr.email_focus, -R.attr.laughing
    )

    init {
        LayoutInflater.from(context).inflate(R.layout.login_view, this)
        initViewActions()

    }

    private fun initViewActions() {
        changeImageState(STATE_INITIAL)

        rootView.setOnClickListener {
            image.setImageDrawable(context.getDrawable(R.drawable.asl_yet))
            changeImageState(STATE_INITIAL)
            edit_text_password.clearFocus()
            edit_text_email.clearFocus()
            hideSoftKeyboard()
        }

        edit_text_password.setOnFocusChangeListener { _, b ->
            if (b) {
                image.setImageDrawable(context.getDrawable(R.drawable.asl_yet))
                changeImageState(STATE_LOOKING)
            } else
                changeImageState(STATE_INITIAL)

        }

        edit_text_email.setOnFocusChangeListener { _, b ->
            if (b) {
                changeImageState(STATE_FOCUS)
            } else {
                image.setImageDrawable(context.getDrawable(R.drawable.asl_yet))
                changeImageState(STATE_INITIAL)
            }
        }

        edit_text_email.addTextChangedListener(this)
    }

    private fun updateFaceView(charSequenceSize: Int, before: Int) {
        if (charSequenceSize == 1 && before == 0) {
            changeImageState(STATE_LAUGHING)

            Handler().postDelayed({
                changeVectorParameters(charSequenceSize)
            }, 500)
        } else if (charSequenceSize <= 30) {
            changeVectorParameters(charSequenceSize)

        } else if (charSequenceSize == 0 && before == 1) {
            image.setImageDrawable(context.getDrawable(R.drawable.asl_yet))
            changeImageState(STATE_NEUTRAL)
        }
    }

    private fun changeVectorParameters(charSequenceSize: Int) {
        vector = if (charSequenceSize > 0) {
            VectorChildFinder(context, R.drawable.vd_yet_email_laughing, image)

        } else {
            VectorChildFinder(context, R.drawable.vd_yet_email, image)
        }

        val face = vector.findGroupByName("face")
        val group7 = vector.findGroupByName("group_7")
        val group6 = vector.findGroupByName("group_6")
        val group3 = vector.findGroupByName("group_3")
        val group4 = vector.findGroupByName("group_4")
        val group1 = vector.findGroupByName("group_1")

        group7.rotation = group7.rotation.minus((charSequenceSize * .2)).toFloat()
        group7.translateY = group7.translateY.plus((charSequenceSize * .5)).toFloat()
        group6.rotation = group6.rotation.minus((charSequenceSize * .1)).toFloat()
        group3.rotation = group3.rotation.minus((charSequenceSize * .1)).toFloat()

        group6.translateY = group6.translateY.plus((charSequenceSize * .2)).toFloat()
        group3.translateY = group3.translateY.plus((charSequenceSize * .2)).toFloat()

        group4.translateX = group4.translateX.minus((charSequenceSize * .2)).toFloat()
        group1.translateX = group1.translateX.minus((charSequenceSize * .2)).toFloat()
        face.translateX = face.translateX.plus(charSequenceSize * .5).toFloat()
        face.translateY = face.translateY.plus(charSequenceSize * .2).toFloat()
    }

    private fun changeImageState(state: IntArray) {
        image.setImageState(state, true)
    }

    private fun hideSoftKeyboard() {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, after: Int) {
        if (p0 != null && p0.isNotEmpty()) {
            updateFaceView(p0.length, before)
        } else {
            updateFaceView(0, 0)
        }
    }

}