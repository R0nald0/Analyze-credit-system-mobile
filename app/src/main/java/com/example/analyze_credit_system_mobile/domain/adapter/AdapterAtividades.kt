package com.example.analyze_credit_system_mobile.domain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.analyze_credit_system_mobile.databinding.AtividadesLayoutBinding
import com.example.analyze_credit_system_mobile.domain.model.Atividade

class AdapterAtividades : RecyclerView.Adapter<AdapterAtividades.AtividadesViewHolder>() {
     private var listAtividade = listOf<Atividade>(
         Atividade("Pix","R$ 123,00","12/02/2001"),
         Atividade("Pix","R$ 123,00","12/02/2001"),
         Atividade("Pix","R$ 123,00","12/02/2001"),
         Atividade("Pix","R$ 123,00","12/02/2001"),
     )


    fun getListAtividade(list : List<Atividade>){
         listAtividade = list
        notifyDataSetChanged()
    }
    inner class AtividadesViewHolder(itemAtividade : AtividadesLayoutBinding) :RecyclerView.ViewHolder(itemAtividade.root){
      lateinit var binding : AtividadesLayoutBinding
     init {
         binding =itemAtividade
     }
        fun bind(item :Atividade){
             binding.txvNomeAtividade.text = item.nome
             binding.txvValorAtividade.text = item.valor
             binding.txvData.text = item.data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtividadesViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val view =AtividadesLayoutBinding.inflate(layout,parent,false)
        return AtividadesViewHolder(view)
    }

    override fun getItemCount() = listAtividade.size

    override fun onBindViewHolder(holder: AtividadesViewHolder, position: Int) {
        val item = listAtividade[position]
        holder.bind(item)
    }
}