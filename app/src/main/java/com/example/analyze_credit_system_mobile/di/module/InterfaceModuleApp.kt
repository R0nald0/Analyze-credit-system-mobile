package com.example.analyze_credit_system_mobile.di.module

import com.example.analyze_credit_system_mobile.data.repository.CreditRepository
import com.example.analyze_credit_system_mobile.data.repository.CustomerRepository
import com.example.analyze_credit_system_mobile.domain.repository.ICreditRepositoty
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import com.example.analyze_credit_system_mobile.domain.usecase.IAutenticationUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.ICreditUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.ICustomerUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.AutenticationUseCaseImpl
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.CreditUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.CustomerUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface  InterfaceModuleApp {


    @Binds
     fun bindCreditUsCase(creditUseCase: CreditUseCase):ICreditUseCase
    @Binds
     fun bindAutenticationUseCase(autenticationUseCaseImpl: AutenticationUseCaseImpl):IAutenticationUseCase
    @Binds
     fun bindsUseCaseCustomer(customerUseCase: CustomerUseCase):ICustomerUseCase
    @Binds
     fun bindCustmerRepository(customerRepository: CustomerRepository):ICustomerRepository
    @Binds
     fun bindCreditRepository(creditRepository: CreditRepository):ICreditRepositoty


}