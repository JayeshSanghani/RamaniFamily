package com.ramanifamily.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.VisualTransformation
import com.ramanifamily.R


@Composable
fun AppOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
) {

    OutlinedTextField(
        singleLine = true,
        maxLines = 1,
        value = value,
        onValueChange = onValueChange,

        label = { Text(text = label) },

        placeholder = {
            if (placeholder != null) {
                Text(text = placeholder)
            }
        },

        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(id = R.dimen.view_bottom)),

        enabled = enabled,
        readOnly = readOnly,
        isError = isError,

        visualTransformation = visualTransformation,

        keyboardOptions = keyboardOptions,

        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = colorResource(id = R.color.green_700)
                )
            }
        },

        trailingIcon = trailingIcon?.let { icon ->
            {
                IconButton(
                    onClick = {
                        onTrailingIconClick?.invoke()
                    }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = colorResource(id = R.color.green_700)
                    )
                }
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
            unfocusedContainerColor = Color.Transparent,
            cursorColor = colorResource(R.color.green_700),
            errorIndicatorColor = Color.Red,
            errorLabelColor = Color.Red,
            errorCursorColor = Color.Red,

            disabledTextColor = colorResource(R.color.disable),
            disabledIndicatorColor = colorResource(R.color.disable),
            disabledLabelColor = colorResource(R.color.disable),

            disabledContainerColor = Color.Transparent,
        )
    )
}

