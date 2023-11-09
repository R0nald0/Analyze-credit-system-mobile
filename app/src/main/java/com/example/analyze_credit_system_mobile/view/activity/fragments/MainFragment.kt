package com.example.analyze_credit_system_mobile.view.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.FragmentMainBinding
import com.example.analyze_credit_system_mobile.domain.model.Notice
import com.example.analyze_credit_system_mobile.view.adapter.MoedaAdapter
import com.example.analyze_credit_system_mobile.view.adapter.NoticeAdapter
import com.example.analyze_credit_system_mobile.view.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@AndroidEntryPoint
class MainFragment : Fragment() {
    private val binding by lazy{
        FragmentMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var noticeAdapter: NoticeAdapter
    private lateinit var moedaAdapter: MoedaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initConfigs()
        initObservers()

    }

    override fun onStart() {
        super.onStart()
        mainViewModel.getCurrencyMoedas()
    }

    fun initConfigs(){
        noticeAdapter = NoticeAdapter()
        moedaAdapter = MoedaAdapter()


        binding.imgCarrousel.registerLifecycle(lifecycle)
        binding.rcvMoedas.apply {
            adapter = moedaAdapter
            layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
            val anime =android.view.animation.AnimationUtils.loadAnimation(context.applicationContext,R.anim.anim_current_enter)
            startAnimation(anime)

        }
        binding.rcvNotice.apply {
            adapter =noticeAdapter
            layoutManager = StaggeredGridLayoutManager(
             2,StaggeredGridLayoutManager.VERTICAL
            )

        }
    }

    fun initObservers(){
        mainViewModel.listMoeda.observe(viewLifecycleOwner){currencyList->
            if (currencyList.isNotEmpty()){
                moedaAdapter.getMoedas(currencyList)
            }else{
                Toast.makeText(this.context, "no currency avalible", Toast.LENGTH_LONG).show()
            }

        }
        mainViewModel.listNotice.observe(viewLifecycleOwner){listNotice->
              if (listNotice.isNotEmpty()){
                  noticeAdapter.getNotices(listNotice as MutableList<Notice>)
              }else Toast.makeText(this.context, "Lista vazia", Toast.LENGTH_LONG).show()

        }
        mainViewModel.listBanner.observe(viewLifecycleOwner){listaBanner->
            if (listaBanner.isNotEmpty()){
                val carouselItems  = listaBanner.map {
                    CarouselItem(it.image,it.title)
                }
                binding.imgCarrousel.setData(carouselItems)
            }else Toast.makeText(this.context, "Lista vazia", Toast.LENGTH_LONG).show()
        }
    }

}