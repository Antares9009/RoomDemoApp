package com.example.roomdemoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemoapp.databinding.ActivityMainBinding
import com.example.roomdemoapp.db.Subscriber
import com.example.roomdemoapp.db.SubscriberDAO
import com.example.roomdemoapp.db.SubscriberDatabase
import com.example.roomdemoapp.db.SubscriberRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var adapter : MyRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao:SubscriberDAO = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this,factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let{
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initRecyclerView(){
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyRecyclerViewAdapter( {selectedItem:Subscriber->listItemClicked(selectedItem)})
        binding.subscriberRecyclerView.adapter = adapter
        displaySubscriberList()
    }

    private fun displaySubscriberList(){
        subscriberViewModel.subscribers.observe(this, Observer {
          Log.i("MY_TAG",it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber : Subscriber){
        //Toast.makeText(this,"Selected is ${subscriber.name}",Toast.LENGTH_LONG).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }
}