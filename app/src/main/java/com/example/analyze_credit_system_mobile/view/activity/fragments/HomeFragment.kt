package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentHomeBinding
import com.example.analyze_credit_system_mobile.domain.enums.TitulosMovimentacao
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.shared.extensions.formatCurrency
import com.example.analyze_credit_system_mobile.view.adapter.PageViewListAdapter
import com.example.analyze_credit_system_mobile.view.model.CustomerView
import com.example.analyze_credit_system_mobile.view.shared.widgets.components.AppCard
import com.example.analyze_credit_system_mobile.view.shared.widgets.components.cardSaldo
import com.example.analyze_credit_system_mobile.view.viewmodel.HomeViewModel
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val homeViewModel by viewModels<HomeViewModel>()
    private val  loginViewModel by activityViewModels<LoginViewModel>()
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    private lateinit var customerView : CustomerView

    private lateinit var viewPagerAdapter: PageViewListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initBindings()
    }

    private fun initBindings() {
        binding.imgNotification.setOnClickListener {
            loginViewModel.delsogar()
        }

        binding.btnPix.setOnClickListener {
            val args = HomeFragmentDirections.actionHomeFragmentToPaymentActivity(customerView,TitulosMovimentacao.PIX.name)
            findNavController().navigate(args)
        }
        binding.btnBoleto.setOnClickListener {
            val args = HomeFragmentDirections.actionHomeFragmentToPaymentActivity(customerView,TitulosMovimentacao.PAGAMENTO_BOLETO.name)
            findNavController().navigate(args)
        }
        binding.button.setOnClickListener {
            val args = HomeFragmentDirections.actionHomeFragmentToPaymentActivity(customerView,TitulosMovimentacao.TED.name)
            findNavController().navigate(args)
        }
    }

    fun initAdapter( ){
        viewPagerAdapter = PageViewListAdapter(this.childFragmentManager,this.lifecycle)
        binding.viewPagerList.adapter = viewPagerAdapter
    }

    override fun onStart() {
        super.onStart()
        loginViewModel.customerLogged()
    }
    private fun initObservers() {
        loginViewModel.authenticationState.observe(this.viewLifecycleOwner){authenticateState ->
               when(authenticateState){
                   is  AuthenticationState.Logged ->{
                       customerView = authenticateState.customerView

                       configTabLayout()
                       val name = "${customerView.firstName} ${customerView.lastName}"
                       binding.txvNameUser.setText(name)
                       homeViewModel.getAllMovimentsCustomer(customerView.id)
                       binding.txvContaUserNumber.setText(customerView.numberAccount.toString())
                       getComposeView(customerView)
                   }
                   is AuthenticationState.Unlogged -> {
                       findNavController().navigate(R.id.loginFragment)
                   }
                   else -> {}
               }
        }
    }


  fun getComposeView(customer: CustomerView){
        binding.idComposeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
           setContent {
               mainApp(customer)
           }
        }
    }

    @Composable
    fun mainApp(customer: CustomerView){

            AppCard(
                modifier = Modifier.background(Color.Transparent),
                content = {
                    Column {
                        var isVisible by remember { mutableStateOf(false) }
                        val angulo by animateFloatAsState(targetValue = if(isVisible) 180F else 0F)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                                ,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Saldo", fontSize = 18.sp , color = Color.White)
                            IconButton(
                                onClick = {
                                isVisible = !isVisible
                            }) {
                                Icon(
                                    modifier = Modifier.rotate(angulo).size(30.dp),
                                    painter = painterResource(id = R.drawable.ic_arrow_down_24),
                                    contentDescription = "arrow_down",
                                    tint = Color.White
                                )
                            }
                        }

                       AnimatedVisibility(visible = isVisible) {
                           cardSaldo(
                               modifier = Modifier.fillMaxWidth(),
                               textContent = getString(R.string.home_mensage_free_value,customer.accountFreeBalance
                                   .formatCurrency()),
                               lowText =  getString(R.string.home_mensage_block_value,customer.accountBalanceBlocked
                                   .formatCurrency()),
                               colorContent = Color.White
                           )
                       }
                        Spacer(Modifier.height(3.dp).background(Color.Red))
                    }
                }
            )

    }

    fun configTabLayout(){
       TabLayoutMediator(binding.tabLayout,binding.viewPagerList){ tab,position ->
            when(position){
                0 ->tab.text ="Ultimas Movimentações"
                1 ->tab.text ="Pedidos Empréstimos"
                else -> tab.text ="Ultimas Movimentações"
            }
        }.attach()
    }


}