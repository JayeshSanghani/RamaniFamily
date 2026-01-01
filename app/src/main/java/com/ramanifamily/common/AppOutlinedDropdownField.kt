package com.ramanifamily.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.ramanifamily.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppOutlinedDropdownField(
    value: String,
    label: String,
    options: List<String>,
    modifier: Modifier = Modifier,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // REQUIRED container
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {

        // menuAnchor() works ONLY here
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            modifier = Modifier
                .menuAnchor()      // ✅ NOW IT WORKS
                .padding(bottom = dimensionResource(id = R.dimen.view_bottom))
                .fillMaxWidth(),

            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        painter = it,
                        contentDescription = null,
                        tint = colorResource(R.color.green_700)
                    )
                }
            },

            trailingIcon = trailingIcon?.let {
                {
                    Icon(
                        painter = it,
                        contentDescription = null,
                        tint = colorResource(R.color.green_700)
                    )
                }
            },

            colors = TextFieldDefaults.colors(
                focusedTextColor = colorResource(R.color.green_700),
                unfocusedTextColor = colorResource(R.color.green_500),
                focusedIndicatorColor = colorResource(R.color.green_700),
                unfocusedIndicatorColor = colorResource(R.color.green_500),
                focusedLabelColor = colorResource(R.color.green_700),
                unfocusedLabelColor = colorResource(R.color.green_500),
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            )
        )

        //  Correct menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = colorResource(R.color.bg_dropdown)
        ) {
            options.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

