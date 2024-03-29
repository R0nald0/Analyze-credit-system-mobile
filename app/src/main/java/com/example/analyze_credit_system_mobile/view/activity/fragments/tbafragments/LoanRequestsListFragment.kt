package com.example.analyze_credit_system_mobile.view.activity.fragments.tbafragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.analyze_credit_system_mobile.databinding.FragmentLoanRequestsListBinding
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.domain.states.StateCredit
import com.example.analyze_credit_system_mobile.view.adapter.CreditListAdapter
import com.example.analyze_credit_system_mobile.view.viewmodel.CreateCreditViewModel
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoanRequestsListFragment : Fragment() {
    private val binding by lazy {
        FragmentLoanRequestsListBinding.inflate(layoutInflater)
    }
    private val creditViewModel by viewModels<CreateCreditViewModel>()
    private val loginViewModel by activityViewModels<LoginViewModel>()
    private lateinit var creditListAdapter: CreditListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        loginViewModel.customerLogged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initAdapter()
        initBinding()
    }

    fun initBinding(){
        binding.apply {
            rcvCrediList.adapter = creditListAdapter
            rcvCrediList.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }
    fun initAdapter(){
        creditListAdapter = CreditListAdapter()
    }

    fun initObserver(){
        creditViewModel.listCredit.observe(viewLifecycleOwner){creditList->
            if(creditList.isEmpty()) {
                creditListAdapter.getAllOderCreditCustomer(listOf())
                binding.apply {
                   linearLayoutAllmovimentsEmpty.visibility =  View.VISIBLE
                   rcvCrediList.visibility =View.GONE
                }
            }
            else {
                creditListAdapter.getAllOderCreditCustomer(creditList)
                binding.apply {
                    linearLayoutAllmovimentsEmpty.visibility =  View.GONE
                    rcvCrediList.visibility =View.VISIBLE
                }
            }
        }

        creditViewModel.stateCreditLiveData.observe(viewLifecycleOwner){ state->
            when(state){
                is StateCredit.Error->{
                    Toast.makeText(context, state.menssage, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

        loginViewModel.authenticationState.observe(viewLifecycleOwner){stateCustomer->
            when(stateCustomer){
                is AuthenticationState.Logged ->{
                    creditViewModel.getAllOrderCreditFromCustomer(stateCustomer.customerView.id)
                }

                else -> {}
            }
        }

    }
    
}
