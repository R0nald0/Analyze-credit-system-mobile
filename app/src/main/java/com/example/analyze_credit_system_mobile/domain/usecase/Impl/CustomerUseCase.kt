package com.example.analyze_credit_system_mobile.domain.usecase.Impl

import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import com.example.analyze_credit_system_mobile.domain.usecase.ICustomerUseCase
import javax.inject.Inject

class CustomerUseCase @Inject constructor(
    private val custumerRepository: ICustomerRepository
): ICustomerUseCase {
    override fun createCustumer(customer: Customer): Result<Boolean> {
         try {
             val customer = custumerRepository.createCustumer(customer)
                return Result.success(customer)
         }catch (e:Exception){
             e.printStackTrace()
             return Result.failure(Throwable("erro ao cadastrar customer",e))
         }
    }

    override fun getAllCustomer(): Result<List<Customer>> {
        TODO("Not yet implemented")
    }

    override fun findCustumerById(idCustomer: Long): Result<Customer> {
        try {
            val customer =custumerRepository.findCustumerById(idCustomer)
             if (customer != null){
                 return Result.success(customer)
             }else{
                 return Result.failure(Throwable("Identificador innválido"))
             }
        }catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro ao buscar custumer",e ))
        }
    }

    override fun deleteCustumer(customer: Customer): Result<Boolean> {
        try{
            val result = custumerRepository.deleteCustumer(customer)
               return  Result.success(result)
        }catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro deletar Customer",e))
        }
    }

    override fun updateCustumer(idCustomer: Long): Result<Boolean> {
        try{
            val result = custumerRepository.updateCustumer(idCustomer)
            return  Result.success(result)
        }catch (e:Exception){
            e.printStackTrace()
            return Result.failure(Throwable("erro atualizar Customer",e))
        }
    }


}