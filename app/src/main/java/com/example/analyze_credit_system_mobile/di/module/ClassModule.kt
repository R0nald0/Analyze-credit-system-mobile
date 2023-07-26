package com.example.analyze_credit_system_mobile.di.module

import com.example.analyze_credit_system_mobile.data.remote.AccountMovimentsApi
import com.example.analyze_credit_system_mobile.data.remote.CotacaoApi
import com.example.analyze_credit_system_mobile.data.remote.CreditApi
import com.example.analyze_credit_system_mobile.data.remote.CustumerApi
import com.example.analyze_credit_system_mobile.data.remote.RetrofitApiClient
import com.example.analyze_credit_system_mobile.data.remote.ViaCepApi
import com.example.analyze_credit_system_mobile.data.remote.firabase.MyFirebaseAuthentication
import com.example.analyze_credit_system_mobile.data.remote.service.AddressService
import com.example.analyze_credit_system_mobile.data.remote.service.CurrencyService
import com.example.analyze_credit_system_mobile.data.remote.service.CustomerService
import com.example.analyze_credit_system_mobile.data.repository.AccountMovimentRepository
import com.example.analyze_credit_system_mobile.data.repository.AddressRespository
import com.example.analyze_credit_system_mobile.data.repository.CustomerRepository
import com.example.analyze_credit_system_mobile.domain.constant.Consts
import com.example.analyze_credit_system_mobile.domain.usecase.AccountMovimentUseCase
import com.example.analyze_credit_system_mobile.domain.usecase.Impl.ValidateCredit
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ClassModule {
    @Provides
    fun provideAccountMovimentUsecase(accountMovimentRepository: AccountMovimentRepository):AccountMovimentUseCase{
        return AccountMovimentUseCase(accountMovimentRepository)
    }
    @Provides
    fun provideAccountRepository(accountMovimentsApi: AccountMovimentsApi):AccountMovimentRepository{
        return AccountMovimentRepository(accountMovimentsApi)
    }
    @Provides
    fun provideValidCredit():ValidateCredit{
        return ValidateCredit()
    }
    @Provides
     fun provideAddresRepository(addressService: AddressService):AddressRespository{
         return  AddressRespository(addressService)
     }
    @Provides
    fun providesCostumerRepository(customerService:CustomerService):CustomerRepository{
         return CustomerRepository(customerService)
    }

    @Provides
    fun providesCostumerService(custumerApi: CustumerApi,myFirebaseAuthentication: MyFirebaseAuthentication): CustomerService {
            return CustomerService(myFirebaseAuthentication,custumerApi)
    }
  @Provides
    fun provideMyFirebaseAuthentication(firebaseAuth: FirebaseAuth):MyFirebaseAuthentication{
       return MyFirebaseAuthentication(firebaseAuth)
    }
    @Provides
    fun provideFiewbaseAuthentication():FirebaseAuth{
         return FirebaseAuth.getInstance()
    }
   @Provides
    fun provideCurrencyService( cotacaoApi : CotacaoApi): CurrencyService {
        return CurrencyService(cotacaoApi)
    }
    @Provides
    fun provideCustumerService() : CustumerApi{
        return RetrofitApiClient.createApi(CustumerApi::class.java,Consts.BASE_URL_CREDIT_API)
    }
    @Provides
    fun provideCurrencyApi():CotacaoApi{
        return RetrofitApiClient.createApi(CotacaoApi::class.java,Consts.BASE_URL_COTACOES_API)
    }
    @Provides
    fun provideCreditService() : CreditApi {
        return RetrofitApiClient.createApi(CreditApi::class.java,Consts.BASE_URL_CREDIT_API)
    }

    @Provides
    fun provideApiViaCep():ViaCepApi{
        return RetrofitApiClient.createApi(ViaCepApi::class.java,Consts.BASE_URL_VIA_CEP_API)
    }
    @Provides
    fun provideApiAccountMovimentation(): AccountMovimentsApi {
        return RetrofitApiClient.createApi(AccountMovimentsApi::class.java,Consts.BASE_URL_CREDIT_API)
    }
}