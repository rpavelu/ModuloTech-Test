package com.ratushny.modulotech.presentation.screen.deviceslist

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.ratushny.modulotech.R
import com.ratushny.modulotech.data.network.model.ProductTypeResponse
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

    private val navigation by lazy { activity?.findNavController(R.id.nav_host_fragment_content_main) }

    private val adapter: DevicesListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        DevicesListAdapter {
            when (it.productType) {
                ProductTypeResponse.Light -> {
                    navigation?.navigate(DevicesListFragmentDirections.actionHomeToLight(it))
                }
                ProductTypeResponse.Heater -> {
                    navigation?.navigate(DevicesListFragmentDirections.actionHomeToHeater(it))
                }
                ProductTypeResponse.RollerShutter -> {
                    navigation?.navigate(DevicesListFragmentDirections.actionHomeToRollerShutter(it))
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
                showFiltersAlertDialog()
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    private fun showFiltersAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.filter))

        val productTypes = arrayOf(
            getString(R.string.light),
            getString(R.string.heater),
            getString(R.string.roller_shutter)
        )

        val checkedItems = booleanArrayOf(
            viewModel.isLightFilterEnabled.value == true,
            viewModel.isHeaterFilterEnabled.value == true,
            viewModel.isRollerShutterFilterEnabled.value == true
        )

        builder.setMultiChoiceItems(
            productTypes, checkedItems
        ) { _, position, checked ->
            when (position) {
                0 -> viewModel.updateFilterByProductType(ProductTypeResponse.Light, checked)
                1 -> viewModel.updateFilterByProductType(ProductTypeResponse.Heater, checked)
                2 -> viewModel.updateFilterByProductType(ProductTypeResponse.RollerShutter, checked)
            }

            viewModel.refreshDeviceList()
        }

        builder.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deviceListRecyclerview.adapter = adapter

        if (viewModel.moduloList.value.isNullOrEmpty()) {
            viewModel.refreshDeviceList()
        }

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