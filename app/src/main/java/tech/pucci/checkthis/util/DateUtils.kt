package tech.pucci.checkthis.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun formatDateToBrStandardFrom(millis: Long): String {
        return try {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = millis
            val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            formatter.format(calendar.time)
        } catch (e: Exception) {
            ""
        }

    }
}