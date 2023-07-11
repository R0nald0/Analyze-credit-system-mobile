package com.example.analyze_credit_system_mobile.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.analyze_credit_system_mobile.domain.model.Address

import com.example.analyze_credit_system_mobile.domain.usecase.Impl.AutenticationUseCaseImpl
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.CustomerUseCase
import com.example.analyze_credit_system_mobile.view.viewmodel.util.CoroutineRule
import com.example.analyze_credit_system_mobile.view.viewmodel.util.getOrAwaitValue
import com.google.common.base.Verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import com.google.common.truth.Truth.assertThat
import io.mockk.verify
import org.mockito.Mockito.times

@RunWith(MockitoJUnitRunner::class)
class CadastroViewModelTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutineRule = CoroutineRule()
    @Mock
    lateinit var useCaseMock : CustomerUseCase
    @Mock
    lateinit var  authUseCaseMock :AutenticationUseCaseImpl
    lateinit var cadastroVieModelMock : CadastroViewModel

    val address = Address("40226540","rua 123")
    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        cadastroVieModelMock = CadastroViewModel(useCaseMock,authUseCaseMock)
    }
    @Test
    fun `findAddress_most return Address by zip code`() = runTest {
        Mockito.`when`(authUseCaseMock.getAddress("40226540")).thenReturn(
            Result.success(address)
        )
        cadastroVieModelMock.findAddress(address.zipCode)
        val result = cadastroVieModelMock.addressLiveData.getOrAwaitValue()
        val add = result.getOrThrow()
        assertThat(result.isSuccess).isTrue()
        assertThat(add.zipCode).isEqualTo("40226540")

        Mockito.verify(authUseCaseMock, times(1)).getAddress(address.zipCode)

    }


}