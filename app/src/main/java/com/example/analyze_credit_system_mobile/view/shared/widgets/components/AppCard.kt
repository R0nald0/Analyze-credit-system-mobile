package com.example.analyze_credit_system_mobile.view.shared.widgets.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppCard(
    modifier: Modifier,
    shapee :RoundedCornerShape = RoundedCornerShape(8.dp),
    color: Color = Color(0xff1b3e2c),
    content: @Composable (() -> Unit)
){
    Surface(
        modifier = modifier,
        shape = shapee,
        elevation = 10.dp,
        color = color,
        content = content,
    )
}

@Composable
@Preview
fun AppCardPreview (){
     Surface() {
          AppCard(Modifier){}
     }
}