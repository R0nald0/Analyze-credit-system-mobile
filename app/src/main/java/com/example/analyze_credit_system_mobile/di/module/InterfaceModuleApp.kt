package com.example.analyze_credit_system_mobile.di.module

import com.example.analyze_credit_system_mobile.domain.repository.ICreditRepositoty
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import com.example.analyze_credit_system_mobile.domain.usecase.ICustomerUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.CreditUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.CustomerUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class InterfaceModuleApp {

    @Binds
    abstract fun bindCustmerRepository():ICustomerRepository

    @Binds
    abstract fun bindCreditRepository():ICreditRepositoty

    @Binds
    abstract fun bindCustomerUseCase(customerUseCase: CustomerUseCase):ICustomerUseCase

    @Binds
    abstract fun bindCreditUseCase(creditUseCase: CreditUseCase):ICustomerUseCase

}