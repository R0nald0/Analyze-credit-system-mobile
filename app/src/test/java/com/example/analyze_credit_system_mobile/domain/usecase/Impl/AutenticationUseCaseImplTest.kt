package com.example.analyze_credit_system_mobile.domain.usecase.Impl

import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.data.repository.AddressRespository
import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.states.AutenticationValidFormState
import com.example.analyze_credit_system_mobile.domain.usecase.IAutenticationUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AutenticationUseCaseImplTest{
    lateinit var autenticationUseCaseImpl: IAutenticationUseCase

    @Mock
    lateinit var addressRespository: AddressRespository

    val customer = Customer("miau","silva","16225601082","miau@gmail.com","231232", Address("40226540","433"),133.0.toBigDecimal(),
        mutableListOf()
    )

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        autenticationUseCaseImpl = AutenticationUseCaseImpl(addressRespository)
    }
    @Test
    fun `validForm_should return a mutable list empty when fields valid`() {
        val resultList = autenticationUseCaseImpl.validForm(
            customer.firstName,
            customer.lastName,
            customer.cpf,
            customer.income,
            customer.email,
            customer.address.zipCode,
            customer.address.street,
            customer.password
        ).listInvalidField

        assertThat(resultList.isEmpty()).isTrue()
        assertThat(resultList).hasSize(0)
    }

    @Test
    fun `validForm_should return a mutablelist wtih  Pair of items invalids`() {

        val resultList = autenticationUseCaseImpl.validForm(
            "",
            "",
            "",
            0.0.toBigDecimal(),
            "miaugmail",
            "40226",
            "",
            "43"
        ).listInvalidField
        assertThat(resultList.isEmpty()).isFalse()
        assertThat(resultList).hasSize(8)
        assertThat(resultList[0]).isEqualTo("NAME_CUSTOMER_KEY" to 2131886160)
        assertThat(resultList[1]).isEqualTo("LASTNAME_CUSTOMER" to 2131886162)
        assertThat(resultList[2]).isEqualTo("CPF_CUSTOMER_KEY" to 2131886158)
        assertThat(resultList[3]).isEqualTo("INCOME_CUSTOMER_KEY" to 2131886161)
        assertThat(resultList[4]).isEqualTo("EMAIL_CUSTOMER_KEY" to 2131886159)
        assertThat(resultList[5]).isEqualTo("ZIPCODE_CUSTOMER_KEY" to 2131886157)
        assertThat(resultList[6]).isEqualTo("STREET_CUSTOMER_KEY" to 2131886166)
        assertThat(resultList[7]).isEqualTo("PASSWORD_CUSTOMER_KEY" to 2131886165)
    }

    @Test
    fun `getAddress_most return address by zipCode`()  = runTest{
         Mockito.`when`(addressRespository.getAddress("40000000")).thenReturn(
              Result.success(Address("40000000","rua teste numero 49 sul"))
         )

       val resultAddress =   addressRespository.getAddress("40000000")
         assertThat(resultAddress.isSuccess).isTrue()
         assertThat(resultAddress.getOrThrow()).isInstanceOf(Address::class.java)
    }

    @Test
    fun `getAddress_most return NullPointerException when zip code invalid`() = runTest {
        Mockito.`when`(addressRespository.getAddress("40000")).thenReturn(
           Result.failure( NullPointerException("cep inexistente"))
        )

        val resulFailure = addressRespository.getAddress("400")
        assertThat(resulFailure).isInstanceOf(Result::class.java)
    }
}