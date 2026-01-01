package com.ramanifamily.common

import androidx.annotation.ColorRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramanifamily.R

@Composable
fun CustomButton(
    text: String,
    onClick:  () -> Unit,
    @ColorRes backgroundColor: Int = R.color.green_700,
    @ColorRes textColor: Int = R.color.white,
    cornerRadius: Dp = 50.dp,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    fontWeight: FontWeight = FontWeight.Normal,
    ) {

    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(cornerRadius),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 0.dp, top = 8.dp, end = 0.dp, bottom = 8.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = backgroundColor),
            contentColor = colorResource(id = textColor),
            disabledContainerColor = colorResource(id = backgroundColor).copy(alpha = 0.5f),
            disabledContentColor = colorResource(id = textColor).copy(alpha = 0.7f)
        )
    ) {

        Text(
            text = text,
            fontSize = dimensionResource(id = R.dimen.font_size).value.sp
        )
    }
}


