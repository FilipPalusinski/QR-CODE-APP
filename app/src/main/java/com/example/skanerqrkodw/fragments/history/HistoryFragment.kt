package com.example.skanerqrkodw.fragments.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skanerqrkodw.R
import com.example.skanerqrkodw.databinding.FragmentHistoryBinding
import com.example.skanerqrkodw.fragments.history.database.HistoryDatabase
import com.example.skanerqrkodw.fragments.history.recyclerview.HistoryAdapter


class HistoryFragment : Fragment() {


    private lateinit var viewModel: HistoryViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHistoryBinding>(
            inflater, R.layout.fragment_history, container, false
        )
        val application = requireNotNull(this.activity).application

        val dataSource = HistoryDatabase.getInstance(application).historyDatabaseDao
        val viewModelFactory = HistoryViewModelFactory(dataSource, application)

        val historyViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(HistoryViewModel::class.java)

        binding.setLifecycleOwner(this)

        binding.historyViewModel = historyViewModel

        val adapter = HistoryAdapter(dataSource, application)
        binding.historyList.adapter = adapter

        historyViewModel.stories.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
        return binding.root
    }

}