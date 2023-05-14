package com.example.analyze_credit_system_mobile.di.module

import com.example.analyze_credit_system_mobile.data.remote.CreditService
import com.example.analyze_credit_system_mobile.data.remote.CreditSystemApi
import com.example.analyze_credit_system_mobile.data.remote.CustumerService
import com.example.analyze_credit_system_mobile.di.ApplicationCredit
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.CustomerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ClassModule {

    @Provides
    fun provideApiCreditSystem(custumerService: CustumerService) : CustumerService{
        return CreditSystemApi.createApi(custumerService::class.java)
    }

    @Provides
    fun provideApiCreditSystem(custumerService: CreditService) : CreditService{
        return CreditSystemApi.createApi(CreditService::class.java)
    }



}