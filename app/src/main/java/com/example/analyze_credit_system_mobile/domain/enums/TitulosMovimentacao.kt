package com.example.analyze_credit_system_mobile.domain.enums

enum class TitulosMovimentacao(val type :String) {
    PEDIDO_EMPRESTIMO("Emprestimo")
    ,PIX("Pix")
    ,PAGAMENTO_BOLETO("Boleto")
    ,TED("Ted")
}