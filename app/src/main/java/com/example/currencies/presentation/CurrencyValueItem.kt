package com.example.currencies.presentation

class CurrencyValueItem(name: String, val course: Float, source: String) {
    var isSet: Boolean = false
    val ticket = name.replace(source, "")
    var value: Float? = null
    fun calc(value: Float?) {
        if (value == null)
            this.value = null
        else
            this.value = course * value
    }

    fun get(value: Float?): Float {
        if (value == null)
            return 0f
        return value / course
    }
}