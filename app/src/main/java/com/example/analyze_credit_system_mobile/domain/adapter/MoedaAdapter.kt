package com.example.analyze_credit_system_mobile.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.analyze_credit_system_mobile.databinding.LayoutMoedaRcvBinding
import com.example.analyze_credit_system_mobile.domain.model.Moeda


class MoedaAdapter  : RecyclerView.Adapter<MoedaAdapter.MoedaViewHolder>() {
    private var listMoeda = listOf<Moeda>()

    fun getMoedas( list : List<Moeda>){
        listMoeda = (list)
        notifyDataSetChanged()
    }

    inner class  MoedaViewHolder(itemMoedaLayout : LayoutMoedaRcvBinding) : RecyclerView.ViewHolder(itemMoedaLayout.root){
        private val binding : LayoutMoedaRcvBinding

        init {
            binding = itemMoedaLayout
        }
        fun bind(itemMoeda: Moeda){
            binding.chip4.text = "${itemMoeda.name} :R$ ${itemMoeda.valor}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoedaViewHolder {
        val  view = LayoutMoedaRcvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MoedaViewHolder(view)
    }

    override fun getItemCount(): Int = listMoeda.size

    override fun onBindViewHolder(holder: MoedaViewHolder, position: Int) {
        val itemMoeda = listMoeda[position]
        holder.bind(itemMoeda)
    }

}