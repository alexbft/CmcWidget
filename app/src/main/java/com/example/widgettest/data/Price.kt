package com.example.widgettest.data

import java.math.BigDecimal
import java.math.MathContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@JvmInline
value class Price(private val units: Double) {
    fun formatAsCurrency(): String {
        if (units >= 10.0) {
            val dec = DecimalFormat("#,###.00", DecimalFormatSymbols(Locale.US))
            return dec.format(units)
        }
        return BigDecimal(units).round(MathContext(4)).toPlainString()
    }
}
