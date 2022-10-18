package com.ratushny.modulotech.presentation.screen.deviceslist

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ratushny.modulotech.R
import com.ratushny.modulotech.databinding.DeviceListFragmentBinding
import com.ratushny.modulotech.domain.entity.device.Heater
import com.ratushny.modulotech.domain.entity.device.Light
import com.ratushny.modulotech.domain.entity.device.ProductType
import com.ratushny.modulotech.domain.entity.device.RollerShutter
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DevicesListFragment : ScopeFragment() {

    private var _binding: DeviceListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DevicesListViewModel by viewModel()

    // TODO: Remove duplication with activity
    private val navigation by lazy { activity?.findNavController(R.id.nav_host_fragment_content_main) }

    private val adapter: DevicesListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        DevicesListAdapter { device ->
            when (device) {
                is Light -> {
                    navigation?.navigate(
                        DevicesListFragmentDirections.actionHomeToLight(device)
                    )
                }
                is Heater -> {
                    navigation?.navigate(
                        DevicesListFragmentDirections.actionHomeToHeater(device)
                    )
                }
                is RollerShutter -> {
                    navigation?.navigate(
                        DevicesListFragmentDirections.actionHomeToRollerShutter(device)
                    )
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeviceListFragmentBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
                menuInflater.inflate(R.menu.filter_menu, menu)

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.action_filter -> showFiltersAlertDialog()
                    R.id.action_refresh -> viewModel.forceRefreshData()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    private fun showFiltersAlertDialog() {
        val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        alertDialogBuilder.setTitle(getString(R.string.filter))

        val productTypes = arrayOf(
            getString(R.string.light),
            getString(R.string.heater),
            getString(R.string.roller_shutter)
        )

        // TODO: Rewrite to objects
        val checkedItems = booleanArrayOf(
            viewModel.isLightFilterEnabled.value == true,
            viewModel.isHeaterFilterEnabled.value == true,
            viewModel.isRollerShutterFilterEnabled.value == true
        )

        alertDialogBuilder.setMultiChoiceItems(
            productTypes, checkedItems
        ) { _, position, checked ->
            when (position) {
                0 -> viewModel.updateFilterByProductType(ProductType.LIGHT, checked)
                1 -> viewModel.updateFilterByProductType(ProductType.HEATER, checked)
                2 -> viewModel.updateFilterByProductType(ProductType.ROLLERSHUTTER, checked)
            }

            viewModel.refreshDeviceList()
        }

        alertDialogBuilder.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deviceListRecyclerview.adapter = adapter

        val swipeHandler = object : DevicesListSwipeToDelete(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition

                viewModel.removeDevice(adapter.getDeviceByPosition(position))
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.deviceListRecyclerview)

        if (viewModel.moduloList.value.isNullOrEmpty()) {
            viewModel.loadData()
        }

        viewModel.refreshDeviceList()

        viewModel.moduloList.observe(viewLifecycleOwner) {
            binding.loadingProgress.visibility = View.GONE
            adapter.updateDevicesList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}