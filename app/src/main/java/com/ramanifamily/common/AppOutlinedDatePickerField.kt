package com.ramanifamily.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.ramanifamily.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppOutlinedDatePickerField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    disableFutureDates: Boolean = false,
    onDateSelected: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        selectableDates = (if (disableFutureDates) {
            object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis <= todayInUtc()
                }
            }
        } else {
            null
        })!!
    )

    if (showDialog) {
        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(
                surface = colorResource(R.color.white),
                surfaceContainerHigh = colorResource(R.color.white),
                onSurface = colorResource(R.color.green_700),
                primary = colorResource(R.color.green_700)
            )
        ) {
        DatePickerDialog(
            colors = DatePickerDefaults.colors(
                // Backgrounds
                containerColor = colorResource(R.color.white),
                titleContentColor = colorResource(R.color.green_700),
                headlineContentColor = colorResource(R.color.green_700),
                weekdayContentColor = colorResource(R.color.green_700),

                // Days
                dayContentColor = colorResource(R.color.green_700),
                selectedDayContentColor = Color.White,
                selectedDayContainerColor = colorResource(R.color.green_700),

                // Today indicator
                todayContentColor = colorResource(R.color.green_700),
                todayDateBorderColor = colorResource(R.color.green_700),

                // Disabled dates
                disabledDayContentColor = colorResource(R.color.green_700).copy(alpha = 0.3f)
            ),
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(formatDate(it))
                        }
                        showDialog = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("CANCEL")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { showDialog = true }
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            enabled = false,
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = colorResource(R.color.green_700)
                )
            },

            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.green_700),
                unfocusedTextColor = colorResource(R.color.green_500),
                focusedIndicatorColor = colorResource(R.color.green_700),
                unfocusedIndicatorColor = colorResource(R.color.green_500),
                focusedLabelColor = colorResource(R.color.green_700),
                unfocusedLabelColor = colorResource(R.color.green_500),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                cursorColor = colorResource(R.color.green_700),
                errorIndicatorColor = Color.Red,
                errorLabelColor = Color.Red,
                errorCursorColor = Color.Red,

                disabledTextColor = colorResource(R.color.green_700),
                disabledIndicatorColor = colorResource(R.color.green_700),
                disabledLabelColor = colorResource(R.color.green_700),

                disabledContainerColor = Color.Transparent,
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
fun formatDate(millis: Long): String {
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return sdf.format(Date(millis))
}

@OptIn(ExperimentalMaterial3Api::class)
fun todayInUtc(): Long {
    val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.timeInMillis
}






