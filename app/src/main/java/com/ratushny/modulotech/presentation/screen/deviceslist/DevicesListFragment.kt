package com.ratushny.modulotech.presentation.screen.deviceslist

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ratushny.modulotech.R
import com.ratushny.modulotech.databinding.FragmentDeviceListBinding
import com.ratushny.modulotech.domain.model.device.Device.Heater
import com.ratushny.modulotech.domain.model.device.Device.Light
import com.ratushny.modulotech.domain.model.device.Device.RollerShutter
import com.ratushny.modulotech.presentation.extensions.attachSwipeToDelete
import com.ratushny.modulotech.presentation.screen.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class DevicesListFragment :
    BaseFragment<DevicesListScreenState, FragmentDeviceListBinding, DevicesListViewModel>() {

    override val viewModel: DevicesListViewModel by viewModel()

    private val deviceAdapter: DevicesListAdapter by lazy {
        DevicesListAdapter { device ->
            navigation.navigate(
                when (device) {
                    is Light -> {
                        DevicesListFragmentDirections.actionHomeToLight(device)
                    }
                    is Heater -> {
                        DevicesListFragmentDirections.actionHomeToHeater(device)
                    }
                    is RollerShutter -> {
                        DevicesListFragmentDirections.actionHomeToRollerShutter(device)
                    }
                }
            )
        }
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDeviceListBinding {
        return FragmentDeviceListBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        initToolbarMenu()
        binding.recyclerviewDevices.apply {
            adapter = deviceAdapter
            attachSwipeToDelete(deviceAdapter::getDeviceByPosition) {
                viewModel.removeDevice(it)
            }
        }
        viewModel.filterDialogEvent.observe(viewLifecycleOwner) {
            showFiltersAlertDialog(it)
        }
    }

    private fun initToolbarMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
                menuInflater.inflate(R.menu.menu_device_list, menu)

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.action_filter -> viewModel.handleFilterClicked()
                    R.id.action_refresh -> viewModel.refreshData()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showFiltersAlertDialog(filters: List<DevicesListScreenState.Filter>) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.filter)
            .setMultiChoiceItems(
                filters.map { getString(it.stringRes) }.toTypedArray(),
                filters.map { it.isEnabled }.toBooleanArray()
            ) { _, position, checked ->
                viewModel.updateFilterState(filters[position].productType, checked)
            }
            .show()
    }

    override fun screenStateObserver(): Observer<DevicesListScreenState> = Observer { state ->
        with(binding) {
            progressLoading.isVisible = state.state == DevicesListScreenState.State.LOADING
            textEmpty.isVisible = state.state == DevicesListScreenState.State.ERROR
            textEmpty.isVisible = state.state == DevicesListScreenState.State.SUCCESS
                    && state.filteredDevices.isEmpty()
            recyclerviewDevices.isVisible = state.state == DevicesListScreenState.State.SUCCESS
                    && state.filteredDevices.isNotEmpty()
            deviceAdapter.submitList(state.filteredDevices)
        }
    }
}