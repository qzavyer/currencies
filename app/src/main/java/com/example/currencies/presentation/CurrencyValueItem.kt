package com.example.currencies.presentation

class CurrencyValueItem(private val name: String, val course: Float, val source: String) {
    var isSet: Boolean = false
    val ticket: String
        get() {
            val ticket = name.replace(source, "")
            return if (ticket == "")
                source
            else
                ticket
        }
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