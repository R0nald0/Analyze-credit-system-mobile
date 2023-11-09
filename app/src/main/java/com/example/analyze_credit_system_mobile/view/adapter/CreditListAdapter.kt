package com.example.analyze_credit_system_mobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import com.example.analyze_credit_system_mobile.databinding.RcvListCreditBinding
import com.example.analyze_credit_system_mobile.view.enums.Status
import com.example.analyze_credit_system_mobile.view.shared.widgets.extension.convertDateLongToString
import com.example.analyze_credit_system_mobile.view.model.CreditExhibitionView
import com.example.analyze_credit_system_mobile.view.shared.widgets.components.AppCard
import com.example.analyze_credit_system_mobile.view.shared.widgets.components.cardCredit
import java.util.Date
import java.util.UUID

class CreditListAdapter :RecyclerView.Adapter<CreditListAdapter.CreditListAdapterViewHolder>(){
    private  var listCreditExhibition  = listOf<CreditExhibitionView>()

    fun getAllOderCreditCustomer(list : List<CreditExhibitionView>){
         listCreditExhibition = list

        notifyDataSetChanged()
    }

    inner class CreditListAdapterViewHolder(creditListLayout :RcvListCreditBinding) :RecyclerView.ViewHolder(creditListLayout.root){
         var binding :RcvListCreditBinding

        init {
            binding = creditListLayout
        }
        fun bind(creditExhibitionView : CreditExhibitionView?){
             binding.composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
             binding.composeView.setContent {
                 allCreditList(creditExhibitionView)
             }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditListAdapterViewHolder {
         val view = RcvListCreditBinding.inflate( LayoutInflater.from(parent.context),parent,false)
         return CreditListAdapterViewHolder(view)
    }

    override fun getItemCount() = listCreditExhibition.size

    override fun onBindViewHolder(holder: CreditListAdapterViewHolder, position: Int) {
        val credit =listCreditExhibition[position]
        holder.bind(credit)
    }

}

@Composable
fun allCreditList(creditByCustomer : CreditExhibitionView?){
    if (creditByCustomer != null){
        AppCard(
            modifier = Modifier.padding(8.dp)
        ) {
            cardCredit(
                modifier = Modifier,
                identifierCredit = creditByCustomer.creditCode.toString(),
                dayFirstInstallment = Date().convertDateLongToString(creditByCustomer.dayFistInstallment)!!,
                valueCredit = creditByCustomer.creditValue,
                numberInstallment = creditByCustomer.numberOfInstallments,
                status = creditByCustomer.status
            )
        }
    }
    else{
        Column(modifier = Modifier
            .fillMaxWidth(0.4F)
            .fillMaxHeight(0.4F),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CircularProgressIndicator(
                color = Color.Green
            )
            Text(text = "Carregando...",color = MaterialTheme.colorScheme.onPrimary)
        }
    }


}

@Composable
@Preview
fun allCreditListPreview(){

    allCreditList(CreditExhibitionView(
        UUID.randomUUID(),"1234".toBigDecimal(),
        Date().time,1,
        Status.IN_PROGRESS,null ,1))
}
