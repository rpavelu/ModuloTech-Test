package com.ratushny.modulotech.presentation.screen.deviceslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ratushny.modulotech.databinding.DeviceListFragmentBinding
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DevicesListFragment : ScopeFragment() {

    private var _binding: DeviceListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DevicesListViewModel by viewModel()

    private val adapter: DevicesListAdapter by lazy { DevicesListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeviceListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deviceListRecyclerview.adapter = adapter

        viewModel.moduloList.observe(viewLifecycleOwner) {
            adapter.addItemList(it)
        }

        viewModel.getDeviceList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}