package com.example.analyze_credit_system_mobile.view.shared.widgets.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun cardSaldo(
    modifier :Modifier,
    colorContent : Color = Color.White,
    textContent :String = "",
    fontSize : TextUnit = 45.sp,
    lowText:String = "",
){
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            ,
    ) {

            Column(verticalArrangement = Arrangement.Center) {
                Text(text = textContent ,fontSize = fontSize,color = colorContent, fontWeight = FontWeight.W700, overflow = TextOverflow.Ellipsis)
                Text(text = lowText,fontSize = 14.sp ,color = colorContent,overflow = TextOverflow.Ellipsis)
            }
    }
}

@Composable
@Preview
fun cardSaldoPreview(){
    cardSaldo(Modifier)
}