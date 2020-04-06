package com.lukmotech.bankbuddy.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

const val DAYS_AFTER_CURRENT_DATE = 3

private val currencyFormat = DecimalFormat("####,###,###.00")

private val dateFormat = SimpleDateFormat("HH:mm:ss MMM-dd, yyyy", Locale.UK)

fun Calendar.addDays(days: Int): Calendar {
    this.add(Calendar.DATE, days)
    return this
}

fun Double.toCurrency():String = currencyFormat.format(this)