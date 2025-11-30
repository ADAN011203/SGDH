package com.example.sgdh.util

object Validators {

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun isNotEmpty(text: String): Boolean {
        return text.trim().isNotEmpty()
    }

    fun isValidJustification(justification: String): Boolean {
        return justification.trim().length >= 10
    }

    fun isValidQuantity(quantity: Int): Boolean {
        return quantity > 0
    }
}