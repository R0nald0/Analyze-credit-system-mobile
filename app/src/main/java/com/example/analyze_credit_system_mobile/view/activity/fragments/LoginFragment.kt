package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.Manifest
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentLoginBinding
import com.example.analyze_credit_system_mobile.domain.constant.Consts
import com.example.analyze_credit_system_mobile.domain.states.AuthenticationState
import com.example.analyze_credit_system_mobile.shared.extensions.clearFieldsError
import com.example.analyze_credit_system_mobile.shared.extensions.toastAlert
import com.example.analyze_credit_system_mobile.view.shared.createNavigationPending
import com.example.analyze_credit_system_mobile.view.shared.createNotification
import com.example.analyze_credit_system_mobile.view.shared.utils.MyNotification
import com.example.analyze_credit_system_mobile.view.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private  val binding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private val loginViewModel by activityViewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()
        initBidings()

//        binding.btnProgresssLogin.setOnClickListener {
//
//            this.context?.let {context->
//               val pendingIntent = context.createNavigationPending( R.id.home_fragment,
//                    R.navigation.navigation_user_accers,
//                    null)
//
//              val notification = context.createNotification(
//                    "Vericamos o Pedido de Emprestimo",
//                    R.drawable.attach_money_24,
//                    "Seu pedido de emprestimo para o credid foi Aprovado",
//                    Consts.CHANNEL_ID,
//                    pendingIntent
//                ).build()
//
//
//                with(NotificationManagerCompat.from(context)){
//                    if (ActivityCompat.checkSelfPermission(
//                            context,
//                            Manifest.permission.POST_NOTIFICATIONS
//                        ) != PackageManager.PERMISSION_GRANTED
//                    ) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//
//                    }
//
//                    notify(Consts.NOTIFICATION_ID,notification)
//                }
//
//            }
//
//           /* val notification = MyNotification(requireContext()).createNotification(
//                "Vericamos o Pedido de Emprestimo",
//                R.drawable.attach_money_24,
//                "Seu pedido de emprestimo para o credid foi Aprovado",
//                Consts.CHANNEL_ID,
//                pendingIntent!!
//            ).build()*/
//
//
//        }
    }

    private fun initViewModels() {
     loginViewModel.authenticationState.observe(viewLifecycleOwner){ authenticationState ->
            when(authenticationState){
                is AuthenticationState.Loading ->{
                    binding.btnProgresssLogin.setLoading()
                }
                is AuthenticationState.Loaded->{
                    binding.btnProgresssLogin.setNormal()
                }
                is AuthenticationState.InvalidAuthentication ->{
                    authenticationState.fields.forEach {campo->
                        bindInputWithLoginViewModels()[campo]?.error = getString(campo.second)
                    }
                }
                is AuthenticationState.Logged ->{
                    findNavController().navigateUp()
                }
                is AuthenticationState.errorState ->{
                    context?.toastAlert("${authenticationState.mensageError}")
                }
                else -> {}
            }
        }
    }
     private fun bindInputWithLoginViewModels() = mapOf(
         LoginViewModel.INPUT_EMAIL to binding.txtInputEmailLogin,
         LoginViewModel.INPUT_PASSWORD to binding.txtInputPassword
     )

   private fun initBidings(){
       binding.txtInputEmailLogin.clearFieldsError()
       binding.txtInputPassword.clearFieldsError()

      binding.btnProgresssLogin.setOnClickListener {
              val email = binding.edtInputEmailLogin.text.toString()
               val password = binding.edtInputPassword.text.toString()
              loginViewModel.authentication(email, password)

       }
       binding.btnCadastresse.setOnClickListener {
           findNavController().navigate(R.id.action_loginFragment_to_cadastroFragment)
       }
   }

}