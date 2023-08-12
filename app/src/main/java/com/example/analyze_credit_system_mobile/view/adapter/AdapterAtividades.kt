package com.example.analyze_credit_system_mobile.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.analyze_credit_system_mobile.data.dto.response.AccountMovimentView
import com.example.analyze_credit_system_mobile.databinding.AtividadesLayoutBinding

class AdapterAtividades : RecyclerView.Adapter<AdapterAtividades.AtividadesViewHolder>() {
     private var listAtividade = listOf<AccountMovimentView>()


    fun getListAtividade(list : List<AccountMovimentView>){
       val listOrder  = list.sortedByDescending {
              it.dateMoviment
          }

         listAtividade = listOrder
        notifyDataSetChanged()
    }
    inner class AtividadesViewHolder(itemAtividade : AtividadesLayoutBinding) :RecyclerView.ViewHolder(itemAtividade.root){
      lateinit var binding : AtividadesLayoutBinding
     init {
         binding =itemAtividade
     }
        fun bind(item :AccountMovimentView){
             binding.txvNomeAtividade.text = item.type.name
             binding.txvValorAtividade.text ="R$ ${item.movimentValue}"
             binding.txvData.text = item.dateMoviment
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