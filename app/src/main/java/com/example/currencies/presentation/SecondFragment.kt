package com.example.currencies.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.currencies.R
import com.example.currencies.databinding.FragmentSecondBinding
import com.example.currencies.di.App
import com.example.currencies.di.AppComponent
import com.example.currencies.di.DaggerAppComponent
import com.example.currencies.di.DataModule
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

private const val LogTag = "SecondFragment"

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {
    private lateinit var dagger: AppComponent
    private lateinit var adapter: SettingsAdapter
    private lateinit var _binding: FragmentSecondBinding
    private val _viewModel: SettingsItemViewModel by viewModels {
        dagger.settingsViewModelFactory()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dagger = DaggerAppComponent.builder().dataModule(DataModule(activity?.application as App))
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SettingsAdapter(object : TicketOperations {
            override fun onRemoveClick(ticket: String) {
                itemRemoveClick(ticket)
            }

            override fun onTicketChoice(ticket: String) {
                ticketChoice(ticket)
            }
        })

        binding.buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        binding.buttonSave.setOnClickListener {
            val tickets = (binding.currenciesStr.text?:"").toString().uppercase().split(",")
            lifecycleScope.launch {
                _viewModel.save(tickets)
                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }

        _binding.currencies.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                _viewModel.state.collect {
                    changeState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                _viewModel.dataChannel.collect {
                    //adapter.setData(it)
                    binding.currenciesStr.setText(it.filter { it.isUse && it.ticket.length>0 }
                        .joinToString { it.ticket })
                }
            }
        }

        _viewModel.updateData()
    }

    private fun itemRemoveClick(ticket: String) {
        lifecycleScope.launch {
            _viewModel.save(ticket, false)
        }
    }

    private fun ticketChoice(ticket: String) {
        lifecycleScope.launch {
            //_viewModel.save(ticket, true)
        }
    }

    private fun changeState(state: State) {
        when (state) {
            is State.Error -> {
                binding.currencies.isEnabled = true
                Log.e(LogTag, state.message)
                Snackbar.make(binding.root, state.message, Snackbar.LENGTH_SHORT).show()
            }

            State.Loading -> {
                binding.currencies.isEnabled = false
            }

            State.Success -> {
                binding.currencies.isEnabled = true
            }

            State.Unready -> {
                binding.currencies.isEnabled = false
            }
        }
    }
}