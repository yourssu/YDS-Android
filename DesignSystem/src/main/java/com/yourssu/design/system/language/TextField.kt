package com.yourssu.design.system.language

import android.content.Context
import android.view.ViewGroup
import com.yourssu.design.system.atom.PasswordTextField
import com.yourssu.design.system.atom.SearchTextField
import com.yourssu.design.system.atom.SimpleTextField
import com.yourssu.design.system.atom.SuffixTextField

fun Context.passwordTextField(block: PasswordTextField.() -> Unit) = PasswordTextField(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.passwordTextField(block: PasswordTextField.() -> Unit) = PasswordTextField(this.context).run {
    block.invoke(this)
    this@passwordTextField.addView(this)
    this
}

fun ComponentGroup.passwordTextField(block: PasswordTextField.() -> Unit) = PasswordTextField(this.componentContext).run {
    block.invoke(this)
    this@passwordTextField.addComponent(this)
    this
}

fun Context.searchTextField(block: SearchTextField.() -> Unit) = SearchTextField(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.searchTextField(block: SearchTextField.() -> Unit) = SearchTextField(this.context).run {
    block.invoke(this)
    this@searchTextField.addView(this)
    this
}

fun ComponentGroup.searchTextField(block: SearchTextField.() -> Unit) = SearchTextField(this.componentContext).run {
    block.invoke(this)
    this@searchTextField.addComponent(this)
    this
}

fun Context.simpleTextField(block: SimpleTextField.() -> Unit) = SimpleTextField(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.simpleTextField(block: SimpleTextField.() -> Unit) = SimpleTextField(this.context).run {
    block.invoke(this)
    this@simpleTextField.addView(this)
    this
}

fun ComponentGroup.simpleTextField(block: SimpleTextField.() -> Unit) = SimpleTextField(this.componentContext).run {
    block.invoke(this)
    this@simpleTextField.addComponent(this)
    this
}

fun Context.suffixTextField(block: SuffixTextField.() -> Unit) = SuffixTextField(this).run {
    block.invoke(this)
    this
}

fun ViewGroup.suffixTextField(block: SuffixTextField.() -> Unit) = SuffixTextField(this.context).run {
    block.invoke(this)
    this@suffixTextField.addView(this)
    this
}

fun ComponentGroup.suffixTextField(block: SuffixTextField.() -> Unit) = SuffixTextField(this.componentContext).run {
    block.invoke(this)
    this@suffixTextField.addComponent(this)
    this
}