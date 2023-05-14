package com.example.analyze_credit_system_mobile.data.repository


import com.example.analyze_credit_system_mobile.data.dto.toCustomer
import com.example.analyze_credit_system_mobile.data.remote.CustumerService
import com.example.analyze_credit_system_mobile.domain.model.Customer
import com.example.analyze_credit_system_mobile.domain.model.toDTO
import com.example.analyze_credit_system_mobile.domain.repository.ICustomerRepository
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val serviceCustomer: CustumerService
) :ICustomerRepository{
    override fun createCustumer(customer: Customer): String {
         try {

             val credit =  serviceCustomer.createUser(customer.toDTO())
             if (credit.isSuccessful){
                   val result = credit.body()
                 if (result !=null){
                     return result
                 }else return " null credito"
             }else{
                 return credit.message()
             }

         }catch (e:Exception){
              e.printStackTrace()
              return throw Exception("falha ao salvar Customer")
         }
    }

    override fun getAllCustomer(): List<Customer> {
        try{
            val result = serviceCustomer.gelAllCustomers()
            if (result.isSuccessful){
                val listCustomerDTO= result.body()
                val customerList  = listCustomerDTO?.map {customerDTO ->
                    customerDTO.toCustomer()
                }
                if (customerList != null) return customerList
                     else  return listOf()
              }else{
                  return listOf()
              }
        }catch (e:Exception){
            e.printStackTrace()
            return throw java.lang.RuntimeException("falha ao buscar dados")
        }
    }

    override fun findCustumerById(idCustomer: Long): Customer? {
        TODO("Not yet implemented")
    }

    override fun deleteCustumer(customer: Customer): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateCustumer(idCustomer: Long): Boolean {
        TODO("Not yet implemented")
    }
}