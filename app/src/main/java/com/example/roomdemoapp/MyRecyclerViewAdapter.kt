package com.example.roomdemoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemoapp.databinding.ListItemBinding
import com.example.roomdemoapp.db.Subscriber

class MyRecyclerViewAdapter(private val clickListener:(Subscriber)->Unit) : RecyclerView.Adapter<MyViewHolder>() {

    private val subscribersList = ArrayList<Subscriber>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemBinding =
                DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscribersList[position],clickListener)
    }

    fun setList(subscribers : List<Subscriber>){
        subscribersList.clear()
        subscribersList.addAll(subscribers)
    }

    override fun getItemCount(): Int {
        return subscribersList.size
    }


}

class MyViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit){
        binding.nameTextView.text = subscriber.name
        binding.emailText.text = subscriber.email
        binding.listItemLayout.setOnClickListener{
            clickListener(subscriber)
        }
    }
}