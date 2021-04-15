package com.example.skanerqrkodw.fragments.menu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.skanerqrkodw.R
import com.example.skanerqrkodw.databinding.FragmentMenuBinding
import com.example.skanerqrkodw.fragments.MenuFragmentDirections
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE


class MenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentMenuBinding>(
            inflater, R.layout.fragment_menu, container, false
        )

        binding.history.setOnClickListener{view: View ->
           view.findNavController().navigate(MenuFragmentDirections.actionMenuToHistoryFragment())
        }

        binding.start.setOnClickListener{ view: View ->
            val integrator = IntentIntegrator.forSupportFragment(this)
            integrator.setRequestCode(REQUEST_CODE)
            integrator.setOrientationLocked(true)
            integrator.initiateScan()
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode != REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data)
            return
        }

        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        if(result != null) {

            if(result.contents == null) {
                Toast.makeText(activity, "Scan cancelled", Toast.LENGTH_LONG).show()
            }
            else {
               findNavController().navigate(MenuFragmentDirections.actionMenuToResult(result.contents))
            }

        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }


}