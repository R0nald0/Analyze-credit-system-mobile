package com.example.analyze_credit_system_mobile.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.navArgs
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.domain.enums.TitulosMovimentacao
import com.example.analyze_credit_system_mobile.view.activity.ui.theme.AnalyzecreditsystemmobileTheme
import com.example.analyze_credit_system_mobile.view.shared.widgets.components.AppCard
import com.example.analyze_credit_system_mobile.view.shared.widgets.components.cardSaldo

class PaymentActivity : ComponentActivity() {

    val args : PaymentActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnalyzecreditsystemmobileTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when(args.typePaymentTitle){
                        TitulosMovimentacao.PIX.name->PixInterFace(args.typePaymentTitle)
                        TitulosMovimentacao.TED.name-> TedInterFace(typeName = args.typePaymentTitle)
                        TitulosMovimentacao.PAGAMENTO_BOLETO.name ->BoletoInterface(args.typePaymentTitle)
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PixInterFace(typeName :String , modifier: Modifier = Modifier) {

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var amountSend by remember {
            mutableStateOf("")
        }
        AreaCustomerData(typePayment = typeName , modifier = modifier)

        Spacer(modifier = modifier.height(20.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedTextField(
                value = "",
                maxLines = 1,
                shape = RoundedCornerShape(30.dp),
                label = { Text(text = "Chave pix")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                placeholder = { Text(text = "Chave Pix") },
                onValueChange ={} )
            IconButton(onClick = { /*TODO*/ }) {
               Icon(painter = painterResource(id = R.drawable.person_search_svgrepo_com), contentDescription ="" )
            }
        }

        Spacer(modifier = modifier.height(20.dp))

        AppCard(modifier = modifier,
              color = Color.LightGray
            ) {
             Column (
               modifier = modifier.padding(8.dp),
             ){
                  val cpf = "876.987.987-21"
                      .replaceRange(0,3,"***")
                      .replaceRange(8,14,"***-**")

                 Text(text = "Instituição: CreditAppSolucoes&Pagamentos",fontWeight = FontWeight.Bold,color = Color.Black, overflow = TextOverflow.Ellipsis ,style = MaterialTheme.typography.titleMedium)
                 Text(text = "Nome :Miau Silva",color = Color.Black,fontWeight = FontWeight.Bold,style = MaterialTheme.typography.titleMedium)
                 Text(text = "cpf: $cpf",color = Color.Black, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
             }
        }

        Spacer(modifier = modifier.height(40.dp))
         OutlinedTextField(
             keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
             value = amountSend,
             label = { Text(text = "Digite o valor")},
             shape = RoundedCornerShape(30.dp),
             onValueChange ={
                 amountSend = it
             }
         )

        Spacer(modifier = modifier.height(40.dp))
        AreaButtuns()

    }
    Spacer(modifier = modifier.height(40.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TedInterFace(typeName :String , modifier: Modifier = Modifier){
    var accountNumber by remember {
        mutableStateOf("")
    }
    var cpf by remember {
        mutableStateOf("")
    }
    var amountSend by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AreaCustomerData(typePayment = typeName, modifier = modifier )

        Spacer(modifier = modifier.height(40.dp))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = accountNumber,
            label = { Text(text = "O número da Conta")},
            shape = RoundedCornerShape(30.dp),
            onValueChange ={
                accountNumber = it
            }
        )
        Spacer(modifier = modifier.height(40.dp))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = cpf,
            label = { Text(text = "e o Cpf")},
            shape = RoundedCornerShape(30.dp),
            onValueChange ={
                cpf = it
            }
        )

        Spacer(modifier = modifier.height(40.dp))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = amountSend,
            label = { Text(text = "Qual valor ?")},
            shape = RoundedCornerShape(30.dp),
            onValueChange ={
                amountSend = it
            }
        )
        Spacer(modifier = modifier.height(30.dp))
        AreaButtuns()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BoletoInterface(paymenType : String,modifier: Modifier = Modifier){
    var amountSend by remember {
        mutableStateOf("")
    }

    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AreaCustomerData(typePayment = paymenType, modifier = modifier )
        Spacer(modifier = modifier.height(40.dp))
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = amountSend,
            label = { Text(text = "Código de barras do boleto")},
            shape = RoundedCornerShape(30.dp),
            onValueChange ={
                amountSend = it
            }
        )

        Spacer(modifier = modifier.height(30.dp))
        AreaButtuns()
    }
}

@Composable
fun AreaCustomerData(typePayment:String, modifier: Modifier){
    var isVisible by remember {
        mutableStateOf(false)
    }
    val angulo by animateFloatAsState(targetValue = if(isVisible) 180F else 0F)
    var saldo by remember {
        mutableStateOf("")
    }

   Column(
       horizontalAlignment = Alignment.CenterHorizontally
   ) {
       Text(text = typePayment, style = MaterialTheme.typography.titleLarge)
       Divider(color = Color.DarkGray, thickness = 5.dp)
       Spacer(modifier = modifier.height(20.dp))
       AppCard(modifier = modifier) {

           if (isVisible)  saldo ="R$ 1.432,00"
           else  saldo ="Saldo"
           Row(
               modifier = modifier.padding(horizontal = 16.dp),
               horizontalArrangement = Arrangement.Center,
               verticalAlignment = Alignment.Top
           ) {
                   cardSaldo(
                       modifier = modifier.fillMaxWidth(0.4F),
                       textContent = saldo ,
                       fontSize = 24.sp
                   )

               IconButton(onClick = {
                   isVisible = !isVisible
               }) {
                   Icon(
                       modifier= modifier
                           .rotate(angulo)
                           .size(35.dp),
                       painter = painterResource(id = R.drawable.ic_arrow_down_24), contentDescription = "", tint = Color.White)
               }
           }
       }
   }
}
@Composable
fun AreaButtuns(modifier: Modifier = Modifier){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ElevatedButton(onClick = { /*TODO*/ }) {
            Text(text = "Transferir", color = Color.DarkGray)
        }
        OutlinedButton(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Cancelar")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnalyzecreditsystemmobileTheme {
        PixInterFace("Pix")
    }
}

