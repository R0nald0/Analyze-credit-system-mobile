package com.example.analyze_credit_system_mobile.domain.usecase.Impl

import com.example.analyze_credit_system_mobile.data.repository.AddressRespository
import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
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
    lateinit var autenticationUseCaseImplMock: IAutenticationUseCase

    @Mock
    lateinit var addressRespository: AddressRespository

    val customer = Customer(1,"miau","silva","16225601082","miau@gmail.com","231232", Address("40226540","433"),133.0.toBigDecimal(),
        mutableListOf()
    )

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        autenticationUseCaseImplMock = AutenticationUseCaseImpl(addressRespository)
    }
    @Test
    fun `validForm_should return a mutable list empty when fields valid`() = runTest {
      autenticationUseCaseImplMock.validForm(
          "miau",
          "silva",
          "16225601082",
          133.0.toBigDecimal(),
          "miau@gmail.com",
          "40226540",
          "231232"
        )

        val resultInvalids = autenticationUseCaseImplMock.checkInvalidFormList()
        assertThat(resultInvalids.listInvalidField.size).isEqualTo(0)
    }
    @Test
    fun `validForm_should return a erro mensage when name isEmpty`()= runTest {

        autenticationUseCaseImplMock.validForm(
            "",
            "silva",
            "16225601082",
            133.0.toBigDecimal(),
            "miau@gmail.com",
            "40226540",
            "231232"
        )
        val resultList = autenticationUseCaseImplMock.checkInvalidFormList()

        assertThat(resultList.listInvalidField.isEmpty()).isFalse()
        assertThat(resultList.listInvalidField).hasSize(1)
        assertThat(resultList.listInvalidField.first()).isEqualTo("NAME_CUSTOMER_KEY" to 2131886163)
    }
    @Test
    fun `validForm_should return a erro mensage when LastName isEmpty`()= runTest {

        autenticationUseCaseImplMock.validForm(
            "Miua",
            "",
            "16225601082",
            133.0.toBigDecimal(),
            "miau@gmail.com",
            "40226540",
            "231232"
        )
        val resultList = autenticationUseCaseImplMock.checkInvalidFormList()

        assertThat(resultList.listInvalidField.isEmpty()).isFalse()
        assertThat(resultList.listInvalidField).hasSize(1)
        assertThat(resultList.listInvalidField.first()).isEqualTo("LASTNAME_CUSTOMER" to 2131886165)

    }

    @Test
    fun `validForm_should return a erro mensage when cpf not contain eleven digits isEmpty`()= runTest {

        autenticationUseCaseImplMock.validForm(
            "Miua",
            "Silva",
            "1622560108",
            133.0.toBigDecimal(),
            "miau@gmail.com",
            "40226540",
            "231232"
        )
        val resultList = autenticationUseCaseImplMock.checkInvalidFormList()

        assertThat(resultList.listInvalidField.isEmpty()).isFalse()
        assertThat(resultList.listInvalidField).hasSize(1)
        assertThat(resultList.listInvalidField.first()).isEqualTo("CPF_CUSTOMER_KEY" to 2131886161)
    }

    @Test
    fun `validForm_should return a erro mensage when income is less than equal to zero`()= runTest {

        autenticationUseCaseImplMock.validForm(
            "Miua",
            "Silva",
            "16225601083",
            0.toBigDecimal(),
            "miau@gmail.com",
            "40226540",
            "231232"
        )
        val resultList = autenticationUseCaseImplMock.checkInvalidFormList()

        assertThat(resultList.listInvalidField.isEmpty()).isFalse()
        assertThat(resultList.listInvalidField).hasSize(1)

        assertThat(resultList.listInvalidField.first()).isEqualTo("INCOME_CUSTOMER_KEY" to 2131886164)
    }

    @Test
    fun `validForm_should return a erro mensage when email not contain @`()= runTest {

        autenticationUseCaseImplMock.validForm(
            "Miua",
            "Silva",
            "16225601082",
            133.0.toBigDecimal(),
            "miaugmail.com",
            "40226540",
            "231232"
        )
        val resultList = autenticationUseCaseImplMock.checkInvalidFormList()

        assertThat(resultList.listInvalidField.isEmpty()).isFalse()
        assertThat(resultList.listInvalidField).hasSize(1)

        assertThat(resultList.listInvalidField.first()).isEqualTo("EMAIL_CUSTOMER_KEY" to 2131886162)

    }

    @Test
    fun `validForm_should return a erro mensage when email not contain dot com`()= runTest {

        autenticationUseCaseImplMock.validForm(
            "Miua",
            "Silva",
            "16225601082",
            133.0.toBigDecimal(),
            "miau@gmail",
            "40226540",
            "231232"
        )
        val resultList = autenticationUseCaseImplMock.checkInvalidFormList()

        assertThat(resultList.listInvalidField.isEmpty()).isFalse()
        assertThat(resultList.listInvalidField).hasSize(1)
        assertThat(resultList.listInvalidField.first()).isEqualTo("EMAIL_CUSTOMER_KEY" to 2131886162)

    }

    @Test
    fun `validForm_should return a erro mensage when zip code not contain eight digits`()= runTest {

        autenticationUseCaseImplMock.validForm(
            "Miua",
            "Silva",
            "16225601082",
            133.0.toBigDecimal(),
            "miau@gmail.com",
            "",
            "231232"
        )
        val resultList = autenticationUseCaseImplMock.checkInvalidFormList()

        assertThat(resultList.listInvalidField.isEmpty()).isFalse()
        assertThat(resultList.listInvalidField).hasSize(1)
        assertThat(resultList.listInvalidField.first()).isEqualTo("ZIPCODE_CUSTOMER_KEY" to 2131886160)

    }

    @Test
    fun `validForm_should return a erro mensage when less than six digits`()= runTest {

        autenticationUseCaseImplMock.validForm(
            "Miua",
            "Silva",
            "16225601082",
            133.0.toBigDecimal(),
            "miau@gmail.com",
            "12345678",
            "23123"
        )
        val resultList = autenticationUseCaseImplMock.checkInvalidFormList()

        assertThat(resultList.listInvalidField.isEmpty()).isFalse()
        assertThat(resultList.listInvalidField).hasSize(1)

        assertThat(resultList.listInvalidField.first()).isEqualTo("PASSWORD_CUSTOMER_KEY" to 2131886168)

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
        Mockito.`when`(addressRespository.getAddress("402")).thenReturn(
           Result.failure( Exception("cep inexistente"))
        )

        val resulFailure = addressRespository.getAddress("400")

    }
}