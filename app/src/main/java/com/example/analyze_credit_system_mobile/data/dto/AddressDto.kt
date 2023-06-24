package com.example.analyze_credit_system_mobile.data.dto

import com.example.analyze_credit_system_mobile.domain.model.Address

data class AddressDto(
    val bairro: String,
    val cep: String,
    val complemento: String,
    val ddd: String,
    val gia: String,
    val ibge: String,
    val localidade: String,
    val logradouro: String,
    val siafi: String,
    val uf: String
)

fun AddressDto.toAddress() = Address(
    zipCode = this.cep,
    street = "$logradouro $bairro $localidade-$uf,$complemento"
)