package com.example.analyze_credit_system_mobile.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.data.remote.service.CurrencyService
import com.example.analyze_credit_system_mobile.domain.model.Moeda
import com.example.analyze_credit_system_mobile.domain.model.Notice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor(
    private val serviceCurrecy : CurrencyService,

) : ViewModel() {
    private val _listNotice = MutableLiveData<List<Notice>>()
    val listNotice : LiveData<List<Notice>>
         get() = _listNotice

    private val _listBanner =MutableLiveData<List<Notice>>()
    val listBanner : LiveData<List<Notice>>
        get() = _listBanner


    private val _listMoeda =MutableLiveData<List<Moeda>>()
    val listMoeda : LiveData<List<Moeda>>
        get() = _listMoeda

  init {
      getlistNotice()
      getListBanner()
  }

    fun getlistNotice(){
        viewModelScope.launch {
             _listNotice.value = lista
        }
    }

    fun  getListBanner(){
        viewModelScope.launch {
            _listBanner.value = listaBannerStatic
        }
    }

    fun getCurrencyMoedas(){
        viewModelScope.launch() {
               val  result = serviceCurrecy.getCurrecys()
                if (result.isSuccess){
                    val moedaDto = result.getOrNull()
                    if(moedaDto != null){

                        val listMoeda = listOf(
                            Moeda(moedaDto.EURBRL.code, String.format("%.4s",moedaDto.EURBRL.ask)),
                            Moeda(moedaDto.USDBRL.code, String.format("%.4s",moedaDto.USDBRL.ask)),
                            Moeda(moedaDto.BTCBRL.code,moedaDto.BTCBRL.ask.replaceRange(3,4,
                                '.'.toString()
                            )),
                        )
                        _listMoeda.value =listMoeda
                    }else{
                        result.getOrElse { exception ->
                            Log.i("INFO_", "getCurrecys:bad request list null ${exception.message}")
                        }
                    }
                }
        }
    }

}

val lista = mutableListOf(
    Notice(
        R.drawable.credito_consig,
        "Empréstimo Consignado",
         "De acordo com o Estatuto do Idoso (Lei nº 10.741/03), todo aquele que possui 60 anos ou mais é considerado idoso e goza dos direitos ali dispostos. No último censo o IBGE apurou que atualmente a população brasileira é composta por 204.450.649 habitantes, dos quais cerca de 26,1 milhões possuem mais de 60 anos, um número que quase dobrou nos últimos quinze anos.\n" +
                 "\n" +
                 "Esses números são fundamentais para que possamos entender o porquê desta parcela da população ter se tornado tão atrativa para aqueles que trabalham com o mercado de consumo. Atualmente há desde pacotes de viagens até academias especiais para a “melhor” idade.\n" +
                 "\n" +
                 "E com o mercado financeiro não é diferente: a concessão de crédito para aposentados e pensionistas é um filão generoso.\n" +
                 "\n" +
                 "O empréstimo consignado é uma modalidade de empréstimo de dinheiro através da qual os consumidores recebem determinada monta das instituições financeiras fornecedoras e, em contraprestação, autorizam estas a descontarem diretamente de seus vencimentos/proventos um certo percentual mensalmente, a título de amortização parcial da dívida. Os descontos ocorrerão até o reembolso total do valor emprestado, acrescido de juros, correção e demais encargos.\n" +
                 "\n" +
                 "Essa forma de contratação de crédito existe no Brasil há mais de cinquenta anos, mas até 2003 apenas servidores públicos usufruíam dela. Desde a lei nº 10.820, no entanto, os aposentados e pensionistas do RGPS foram incluídos no rol de consumidores desse tipo de linha de crédito."
        ),
    Notice(
        R.drawable.fgts,
        "Antecipação do FGTS",
        "O empréstimo com antecipação do FGTS é uma modalidade de crédito para quem tem dinheiro no Fundo de Garantia e aderiu ao Saque-Aniversário.\n" +
                "\n" +
                "Com ele, você pode antecipar 7 anos do saldo do FGTS liberado pelo governo no mês do seu aniversário, e usá-lo como quiser.\n" +
                "\n" +
                "Tudo isso com juros baixos, saldo a partir de R\$200 e sem parcelas mensais, porque os valores serão descontados diretamente da sua conta FGTS, anualmente."),
    Notice(
        R.drawable.credito_imob,
        "crédito imobiliário pré-aprovado facilita a vida na hora de comprar um imóvel",
        "Ter em mãos algumas propostas de crédito pré-aprovado pode ser decisivo para se organizar financeiramente, equilibrar expectativas e dar agilidade às negociações na hora de comprar a casa própria.\n" +
                "\n" +
                "Antes de ir em busca do imóvel perfeito para você, pré-aprovar o crédito imobiliário pode ser uma boa solução para se precaver de investimentos que não podem ser feitos e frustrações que ninguém quer ter com o sonho da casa própria.\n" +
                "\n" +
                "O crédito pré-aprovado seria uma etapa anterior à liberação do crédito imobiliário concedido pelos bancos, é gratuito e pode ser solicitado a diversas instituições financeiras para entender se as parcelas, taxas e juros oferecidos cabem no seu orçamento. Trata-se de uma análise da situação financeira do cliente junto a órgãos de proteção ao crédito, e que na prática, quando aprovado, garante o dinheiro da compra da casa própria nas condições determinadas pelo banco. fonte :https://caiomacedo.com.br/o-credito-imobiliario-pre-aprovado-facilita-a-vida-na-hora-de-comprar-um-imovel/"),
    Notice(
        R.drawable.maxresdefault,
        "Como começar a investir",
        "Quem já refletiu sobre o seu futuro financeiro certamente pensou sobre como começar a investir. O verbo talvez soe como uma possibilidade distante para muitas pessoas. Mas a verdade é que existem muitas formas de investir – das mais acessíveis às mais sofisticadas.\n" +
                "\n" +
                "Conhecê-las, escolher a sua própria e dar os primeiros passos devem estar entre as prioridades de quem sonha em ter uma vida confortável para si e sua família.\n" +
                "\n" +
                "Se lhe falta segurança sobre como começar a investir, esse guia explica os conceitos básicos do mercado financeiro e dos investimentos, de um jeito direto e descomplicado" +
                "fonte: https://www.infomoney.com.br/guias/como-comecar-a-investir/ "
    ),
    Notice(
        R.drawable.emprestimo_neg,
        "EMPRÉSTIMO PARA NEGATIVADO",
        "O empréstimo para negativados online é uma alternativa rápida e prática para quem precisa pegar crédito no mercado, mas está com o CPF restrito — o famoso nome sujo.\n" +
                "\n" +
                "A dificuldade de conseguir aprovação se dá pelo fato de que as instituições consideram um risco muito alto emprestar para quem já não consegue honrar seus compromissos e, por isso, têm seus nomes negativados. fonte:https://www.idinheiro.com.br/emprestimos/negativados/emprestimo-com-restricao/"
    )
)

val listaBannerStatic = listOf(
    Notice(R.drawable.ir,
    "Para Você | Antecipação do 13º Salário",""),
    Notice(R.drawable.images,
    "Antecipação de 100% do imposto de renda",""),
    Notice(R.drawable.banner_jurs,
    "Empréstimo com juros baixos:",""),
)


