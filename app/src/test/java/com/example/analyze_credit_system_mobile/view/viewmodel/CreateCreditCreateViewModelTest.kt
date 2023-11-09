package com.example.analyze_credit_system_mobile.view.viewmodel

import com.example.analyze_credit_system_mobile.domain.usecase.ICreditUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.CustomerUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.ValidateCredit
import com.example.analyze_credit_system_mobile.view.shared.widgets.extension.convertDateLongToString
import com.example.analyze_credit_system_mobile.view.shared.widgets.extension.convertDateStringToLong
import com.google.common.truth.Truth.assertThat

import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class CreateCreditCreateViewModelTest {

    @Mock
    lateinit var creditUseCaseMock : ICreditUseCase
    @Mock
    lateinit var validateCreditMock:ValidateCredit
    @Mock
    lateinit var customerUseCaseMock: CustomerUseCase

    lateinit var crediViewModel : CreateCreditViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        crediViewModel = CreateCreditViewModel(creditUseCaseMock,validateCreditMock,customerUseCaseMock)
    }

    @Test
    fun `formataDataDeStringParaLong_must receive date in string and return a Long`() {
        val date = "23/02/2021"
       val  dateReturn = Date().convertDateStringToLong(date)
      assertThat(dateReturn).isNotNull()
        assertThat(dateReturn).isEqualTo(1614049200000)
    }

    @Test
    fun `formataDataDeLongParaString must receive date in Long and return a String`() {
        val dataLong = 1692327600000
        val  dateReturn = Date().convertDateLongToString(dataLong)
        assertThat(dateReturn).isNotNull()
        assertThat(dateReturn).isEqualTo("18/08/2023")
    }
}

