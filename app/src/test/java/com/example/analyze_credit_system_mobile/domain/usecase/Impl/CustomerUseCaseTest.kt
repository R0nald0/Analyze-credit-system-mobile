package com.example.analyze_credit_system_mobile.domain.usecase.Impl

import com.example.analyze_credit_system_mobile.domain.model.Address
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository

import com.google.common.truth.Truth.assertThat

import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito

import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CustomerUseCaseTest {


    @Mock
    lateinit var customerRepositoryMock : ICustomerRepository
    lateinit var useCaseMock: CustomerUseCase

    val customer = Customer(1,"miau","silva","16225601082","miau@gmail.com","231232", Address("40226540","433"),133.0.toBigDecimal(),
        mutableListOf()
    )


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        useCaseMock = CustomerUseCase(customerRepositoryMock)
    }

    @Test
    fun `createCustumer_must create customer e and result customer`()= runTest {
       Mockito.`when`(customerRepositoryMock.createCustumer(customer)).thenReturn(
           customer
       )
       val resultCustomer =useCaseMock.createCustumer(customer)
       val customerMokc =resultCustomer.getOrThrow()
       assertThat(resultCustomer.isSuccess).isTrue()
       assertThat(customerMokc).isSameInstanceAs(customer)
       assertThat(customerMokc).isInstanceOf(Customer::class.java)
       assertThat(customerMokc.email).isEqualTo("miau@gmail.com")

    }

    @Test
    fun `createCustomer_must return erro mensagem when costumer is null`() = runTest{
        Mockito.`when`(customerRepositoryMock.createCustumer(customer)).thenReturn(
            null
        )
        val result = useCaseMock.createCustumer(customer)
        assertThat(result.isFailure)
    }

    @Test
    fun `delete_should delete a customer by id and return true`() = runTest {
          Mockito.`when`(customerRepositoryMock.deleteCustumer(customer)).thenReturn(
              true
          )
           val result = useCaseMock.deleteCustumer(customer)
            val isDelete= result.getOrThrow()
           assertThat(result.isSuccess).isEqualTo(true)
           assertThat(isDelete).isNotEqualTo(false)

    }

    @Test
    fun `findCustumerByEmail_most retunr a customer by email`() = runTest {
        val email = customer.email
        Mockito.`when`(customerRepositoryMock.findCustumerByEmail(email)).thenReturn(customer)

        val resulCustomer = useCaseMock.findCustumerByEmail(email)
        val customer = resulCustomer.getOrNull()
        assertThat(resulCustomer.isSuccess).isTrue()
        assertThat(customer).isSameInstanceAs(customer)
        assertThat(customer).isNotNull()
        assertThat(customer?.email).contains("@")
        assertThat(customer?.cpf).isEqualTo("16225601082")
    }

    @Test
    fun `findCustomerByEmail_must return exeprion when not found email`() = runTest {
        Mockito.`when`(customerRepositoryMock.findCustumerByEmail("fakeEmail@gamil.com")).thenThrow(
            Exception()
        )
    }

    @After
    fun tearDown() {
    }
}