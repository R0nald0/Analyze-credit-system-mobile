package com.example.analyze_credit_system_mobile.view.shared.widgets.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.analyze_credit_system_mobile.domain.enums.Status
import com.example.analyze_credit_system_mobile.shared.extensions.formatCurrency
import java.math.BigDecimal

@Composable
fun cardCredit(
    modifier: Modifier,
    identifierCredit:String ="&%$%&*()¨()424324234112weqed1312",
    valueCredit:BigDecimal = "100.00".toBigDecimal(),
    numberInstallment : Int = 0,
    status : Status =Status.IN_PROGRESS,
    dayFirstInstallment : String = "2/10/1992"
){
   Column(
       modifier = modifier
           .fillMaxWidth()
           .padding(8.dp)
   ) {
        val currency = valueCredit.formatCurrency()
        val title =12.sp
         val paddingHorizontal = 10.dp
         val textContentSize = 14.sp
       Row(
           modifier
               .fillMaxWidth(),
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           Text(text = "Identificador", fontSize = title, color = MaterialTheme.colors.secondary)
           Text(text = "Status", fontSize = title,color = MaterialTheme.colors.onPrimary)
       }
       Row(
           modifier.fillMaxWidth().padding(horizontal = paddingHorizontal),
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           Text(
               modifier=modifier.fillMaxWidth(0.5f),
               text = identifierCredit,
               fontSize = textContentSize,
               color = MaterialTheme.colors.onPrimary,
               maxLines = 1,
               overflow = TextOverflow.Ellipsis
           )
           Text(text = status.state,fontSize = textContentSize,color =MaterialTheme.colors.onPrimary)
       }

       Spacer(modifier = modifier.height(2.dp))
       Text(text = "Valor", fontSize = title,
           color = MaterialTheme.colors.onPrimary,
       )
       Text(
           modifier = modifier.padding(horizontal = paddingHorizontal),
           text = currency,
           fontSize = 20.sp,
           color = MaterialTheme.colors.onPrimary
       )

       Spacer(modifier = modifier.height(4.dp))

       Row(
           modifier = modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           Text(text = "Dia da Primeira Parcela",fontSize = title,color = MaterialTheme.colors.onPrimary)
           Text(text = "Numero de Parcelas", fontSize = title ,color = MaterialTheme.colors.onPrimary)
       }

       Row(
           modifier = modifier.fillMaxWidth().padding(horizontal = paddingHorizontal),
           horizontalArrangement = Arrangement.SpaceBetween
       ) {
           Text(text = dayFirstInstallment,fontSize = textContentSize,color = MaterialTheme.colors.onPrimary)
           Text(text = "$numberInstallment", fontSize = textContentSize,color = MaterialTheme.colors.onPrimary)
       }

   }
}

@Composable
@Preview
fun cardCreditPreview(){
    cardCredit(Modifier)
}