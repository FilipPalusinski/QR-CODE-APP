package com.example.skanerqrkodw.fragments.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.skanerqrkodw.R
import com.example.skanerqrkodw.databinding.FragmentResultBinding
import com.example.skanerqrkodw.fragments.ResultFragmentArgs
import com.example.skanerqrkodw.fragments.ResultFragmentDirections
import com.example.skanerqrkodw.fragments.history.database.HistoryDatabase


class ResultFragment : Fragment() {

    private lateinit var viewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentResultBinding>(
            inflater, R.layout.fragment_result, container, false
        )
        val application = requireNotNull(this.activity).application
        val dataSource = HistoryDatabase.getInstance(application).historyDatabaseDao
        val viewModelFactory = ResultViewModelFactory(dataSource)

        val resultViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(ResultViewModel::class.java)

        binding.resultViewModel = resultViewModel

        viewModel = ViewModelProvider(this).get(ResultViewModel::class.java)

        binding.resultViewModel = viewModel

        val args = ResultFragmentArgs.fromBundle(requireArguments())
        viewModel.scannedQrCode = args.scanningResult

        viewModel.checkString()

        viewModel.makeVisible.observe(viewLifecycleOwner, Observer {
            if (viewModel.makeVisible.value == true) {
                binding.open.visibility = View.VISIBLE
            }

        })
        binding.open.setOnClickListener{
            openBrowser(viewModel.scannedQrCode)
        }

        binding.history.setOnClickListener{view: View ->
            view.findNavController().navigate(ResultFragmentDirections.actionResultToHistoryFragment())
        }


        return binding.root
    }

    fun openBrowser(result: String){
        val intent = Intent(Intent.ACTION_VIEW)
        if(result.startsWith("www.")){
            intent.data = Uri.parse("http://$result")
        }else if((result.startsWith("http")) && result.endsWith(".pl")){
            intent.data = Uri.parse(result)
        }else if(result.endsWith(".pl")) {
            intent.data = Uri.parse("http://www.$result")
        }else{
            intent.data = Uri.parse(result)
        }

        activity?.startActivity(intent)

    }


}