package com.example.currencies.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencies.databinding.FragmentFirstBinding
import com.example.currencies.di.App
import com.example.currencies.di.AppComponent
import com.example.currencies.di.DaggerAppComponent
import com.example.currencies.di.DataModule
import com.google.android.material.snackbar.Snackbar
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private const val LogTag = "FirstFragment"

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private lateinit var _binding: FragmentFirstBinding
    private lateinit var dagger: AppComponent
    private lateinit var adapter: CurrencyAdapter
    private val _viewModel: CurrencyItemViewModel by viewModels {
        dagger.currencyViewModelFactory()
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
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CurrencyAdapter { onItemEdit(it) }
        _binding.recyclerView.adapter = adapter
        lifecycleScope.launchWhenStarted {
            _viewModel.state.collect {
                changeState(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            _viewModel.date.collect {
                changeDate(it)
            }
        }

        lifecycleScope.launchWhenStarted {
            _viewModel.dataChannel.collect {
                adapter.setData(it)
            }
        }

        _viewModel.updateData()
    }

    private fun changeState(state: State) {
        when (state) {
            is State.Error -> {
                binding.recyclerView.isEnabled = true
                adapter.isCanChange = false
                Log.e(LogTag, state.message)
                Snackbar.make(binding.root, state.message, Snackbar.LENGTH_SHORT).show()
            }
            State.Loading -> {
                binding.recyclerView.isEnabled = false
                adapter.isCanChange = true
            }
            State.Success -> {
                binding.recyclerView.isEnabled = true
                adapter.isCanChange = false
            }
            State.Unready -> {
                binding.recyclerView.isEnabled = false
                adapter.isCanChange = false
            }
        }
    }

    private fun changeDate(date: OffsetDateTime?) {
        val dateValue = date?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm"))
        binding.info.text = dateValue
    }

    private fun onItemEdit(item: CurrencyItem) {

    }
}