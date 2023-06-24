package com.example.analyze_credit_system_mobile.domain.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.analyze_credit_system_mobile.R
import com.example.analyze_credit_system_mobile.databinding.NoticeLayoutBinding
import com.example.analyze_credit_system_mobile.domain.model.Notice
import kotlin.random.Random

class NoticeAdapter :RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {
     private val listNotice = mutableListOf<Notice>()

    fun getNotices( list : MutableList<Notice>){
         listNotice.addAll(list)
        notifyDataSetChanged()
    }
     inner class  NoticeViewHolder(itemNoticeLayout :NoticeLayoutBinding) : RecyclerView.ViewHolder(itemNoticeLayout.root){
        private val binding : NoticeLayoutBinding

        init {
          binding = itemNoticeLayout
      }
         fun bind(itemNotice: Notice){
             binding.txvTituloNotice.text = itemNotice.title
             val numero = Random.nextInt(10)
           //  binding.txvDescriNotice.setLines(numero)
             binding.txvDescriNotice.text = itemNotice.description
             Glide.with(itemView.context).load(itemNotice.image)
                 .into(binding.imvNotice)

         }
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val  view = NoticeLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
           parent,
            false
        )
        return NoticeViewHolder(view)
    }

    override fun getItemCount(): Int {
       return  listNotice.size
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val itemNotice = listNotice[position]
        holder.bind(itemNotice)
    }
}