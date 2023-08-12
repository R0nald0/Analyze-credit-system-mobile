package com.example.analyze_credit_system_mobile.data.dto.response

import com.example.analyze_credit_system_mobile.domain.model.Address

data class Endereco(
    val address: String,
    val city: String,
    val code: String,
    val district: String,
    val state: String,
    val status: Int
)

fun Endereco.toAddress() = Address(
    zipCode = this.code,
    street = "$city-$state $address,$district "
)