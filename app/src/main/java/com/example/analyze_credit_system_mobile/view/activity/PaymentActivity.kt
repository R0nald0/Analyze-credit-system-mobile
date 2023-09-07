package com.example.analyze_credit_system_mobile.view.activity

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.navArgs
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.domain.enums.TitulosMovimentacao
import com.example.analyze_credit_system_mobile.shared.extensions.formatCurrency
import com.example.analyze_credit_system_mobile.shared.extensions.toastAlert
import com.example.analyze_credit_system_mobile.view.activity.ui.theme.AnalyzecreditsystemmobileTheme
import com.example.analyze_credit_system_mobile.view.activity.ui.theme.GreeLight
import com.example.analyze_credit_system_mobile.view.activity.ui.theme.GreenAPP
import com.example.analyze_credit_system_mobile.view.shared.widgets.components.AppCard
import com.example.analyze_credit_system_mobile.view.shared.widgets.components.cardSaldo
import com.example.analyze_credit_system_mobile.view.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
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
                        TitulosMovimentacao.PIX.name->PixInterFace(args)
                        TitulosMovimentacao.TED.name-> TedInterFace(args = args)
                        TitulosMovimentacao.PAGAMENTO_BOLETO.name ->BoletoInterface(args)
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PixInterFace(args :PaymentActivityArgs , modifier: Modifier = Modifier) {


    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val context = LocalContext.current
        var amountSend by remember {
            mutableStateOf("")
        }

        var accountNumber by remember {
            mutableStateOf("")
        }

        var isValid by remember {
            mutableStateOf(false)
        }

        var errorText by remember {
            mutableStateOf("")
        }

        val homeViewModel =  viewModel(modelClass = HomeViewModel::class.java)
        val resultLiveRecipientData by homeViewModel.recipientData.observeAsState(initial = homeViewModel.recipientData.value)
        /*val state by  homeViewModel.statePayment.observeAsState(initial = homeViewModel.statePayment.value)*/

        AreaCustomerData(args = args , modifier = modifier)

        Spacer(modifier = modifier.height(20.dp))

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedTextField(
                value = accountNumber,
                maxLines = 1,
                shape = RoundedCornerShape(30.dp),
                isError = isValid,
                label = { Text(text = "Chave pix")},

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                supportingText = {if (isValid) Text(text = errorText, fontWeight = FontWeight.Bold) else null},
                trailingIcon ={
                             if (isValid) Icon(
                                 imageVector = Icons.Filled.ErrorOutline,
                                 contentDescription = "",
                                 tint = Color.Red
                             )

                             else null
                } ,
                onValueChange ={
                    if (it.length > 6){ accountNumber = it.substring(0,6)  }
                    else accountNumber = it
                } )

             IconButton(onClick = {
                    errorText =""
                   val erros  =homeViewModel.validateAccountNumber(accountNumber)
                   if (erros.isNotEmpty()) {
                       isValid = true
                       erros.forEach {erro->
                           errorText += "${erro}\n"
                       }
                   } else isValid=false

                 if (resultLiveRecipientData?.isFailure == true) {
                    isValid = true
                    context.toastAlert("Dados do Destinátario inválido,tente novamente")
                  }
             }
            ) {
               Icon(imageVector = Icons.Filled.PersonSearch, contentDescription = "Search", tint = GreenAPP)
            }
        }

        Spacer(modifier = modifier.height(20.dp))

        if (resultLiveRecipientData != null) {
            if (resultLiveRecipientData!!.isSuccess){
                val recipientData = resultLiveRecipientData!!.getOrThrow()
                Column (
                    modifier= modifier.verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally){
                    AppCard(modifier = modifier,
                        color = Color.LightGray
                    ) {
                        Column (
                            modifier = modifier.padding(8.dp),
                        ){
                            val cpf = recipientData.cpf
                                .replaceRange(0,3,"***")
                                .replaceRange(8,14,"***-**")

                            Text(
                                text = "Instituição: CreditAppSolucoes&Pagamentos",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                overflow = TextOverflow.Ellipsis ,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "Nome :${recipientData.firstName} ${recipientData.lastName} ",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = "cpf: $cpf",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )
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
            }
        }

    }
    Spacer(modifier = modifier.height(40.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TedInterFace(args :PaymentActivityArgs , modifier: Modifier = Modifier){
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
        AreaCustomerData(args = args, modifier = modifier )

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
fun BoletoInterface(args : PaymentActivityArgs,modifier: Modifier = Modifier){
    var amountSend by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {AreaCustomerData(args = args, modifier = modifier )},
    ) {

        Column(
            modifier
                .fillMaxWidth()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

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

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreaCustomerData(args:PaymentActivityArgs, modifier: Modifier){
    val activity = LocalContext.current as? Activity
    var isVisible by remember {
        mutableStateOf(false)
    }
    val angulo by animateFloatAsState(targetValue = if(isVisible) 180F else 0F)
    var saldo by remember { mutableStateOf("") }
 Column(
       modifier= modifier
           .background(
               color = GreeLight,
               shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
           )
           .fillMaxWidth(),
       horizontalAlignment = Alignment.CenterHorizontally

   ) {

     CenterAlignedTopAppBar(
         title = {   Text(
             text = args.typePaymentTitle,
             style = MaterialTheme.typography.titleLarge,
             color = Color.Black,
             fontWeight = FontWeight.SemiBold
         )},
         colors = TopAppBarDefaults.mediumTopAppBarColors(
             containerColor = GreeLight
         ),
         navigationIcon = {
             IconButton(onClick = {
                 if (activity != null) {
                     activity.finish()
                 }
             }) {
                 Icon(
                     imageVector = Icons.Filled.ArrowBack,
                     contentDescription = "Back button"
                 )
             }
         },
     )

       //Spacer(modifier = modifier.height(20.dp))
       if (isVisible) saldo = args.customerView.accountFreeBalance.formatCurrency()
       else  saldo ="Saldo"
       Row(
           modifier = modifier.padding(horizontal = 16.dp),
           horizontalArrangement = Arrangement.SpaceAround,
       ) {
           cardSaldo(
               modifier = modifier.fillMaxWidth(0.4F),
               textContent = saldo ,
               fontSize = 26.sp,
               colorContent = Color.Black
           )

           IconButton(onClick = {
               isVisible = !isVisible
           }) {
               Icon(
                   modifier= modifier
                       .rotate(angulo)
                       .size(30.dp),
                   painter = painterResource(id = R.drawable.ic_arrow_down_24), contentDescription = "", tint = Color.Black)
           }
       }

       Spacer(modifier = modifier.height(20.dp))
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

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnalyzecreditsystemmobileTheme {
        PixInterFace("Pix")
    }
}
*/

