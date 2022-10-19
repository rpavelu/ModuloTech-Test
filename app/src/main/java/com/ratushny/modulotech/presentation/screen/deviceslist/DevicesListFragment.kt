package com.ratushny.modulotech.presentation.screen.deviceslist

import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ratushny.modulotech.R
import com.ratushny.modulotech.databinding.FragmentDeviceListBinding
import com.ratushny.modulotech.domain.model.device.Heater
import com.ratushny.modulotech.domain.model.device.Light
import com.ratushny.modulotech.domain.model.device.RollerShutter
import com.ratushny.modulotech.presentation.common.SwipeToDeleteTouchHelper
import com.ratushny.modulotech.presentation.extensions.attachSwipeToDelete
import com.ratushny.modulotech.presentation.extensions.changeVisibility
import com.ratushny.modulotech.presentation.screen.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class DevicesListFragment :
    BaseFragment<DevicesListScreenState, FragmentDeviceListBinding, DevicesListViewModel>() {

    override val viewModel: DevicesListViewModel by viewModel()

    private val adapter: DevicesListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        DevicesListAdapter { device ->
            when (device) {
                is Light -> {
                    navigation.navigate(
                        DevicesListFragmentDirections.actionHomeToLight(device)
                    )
                }
                is Heater -> {
                    navigation.navigate(
                        DevicesListFragmentDirections.actionHomeToHeater(device)
                    )
                }
                is RollerShutter -> {
                    navigation.navigate(
                        DevicesListFragmentDirections.actionHomeToRollerShutter(device)
                    )
                }
            }
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

        binding.deviceListRecyclerview.adapter = adapter

        val swipeHandler = object : SwipeToDeleteTouchHelper(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition

                viewModel.removeDevice(adapter.getDeviceByPosition(position))
            }
        }

        binding.deviceListRecyclerview.attachSwipeToDelete(swipeHandler)
        viewModel.filterDialogState.observe(viewLifecycleOwner) {
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
                filters.map { it.state }.toBooleanArray()
            ) { _, position, checked ->
                viewModel.updateFilterState(filters[position].productType, checked)
            }
            .show()
    }

    override fun screenStateObserver(): Observer<DevicesListScreenState> = Observer { state ->
        with(binding) {
            loadingProgress.changeVisibility(state.state == DevicesListScreenState.State.LOADING)
            errorText.changeVisibility(state.state == DevicesListScreenState.State.ERROR)
            emptyText.changeVisibility(
                state.state == DevicesListScreenState.State.SUCCESS
                        && state.filteredDevices.isEmpty()
            )
            deviceListRecyclerview.changeVisibility(
                state.state == DevicesListScreenState.State.SUCCESS
                        && state.filteredDevices.isNotEmpty()
            )
            adapter.updateDevicesList(state.filteredDevices)
        }
    }
}