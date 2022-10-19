package com.ratushny.modulotech.presentation.screen.user

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.ratushny.modulotech.R
import com.ratushny.modulotech.databinding.FragmentUserBinding
import com.ratushny.modulotech.presentation.extensions.hideKeyboard
import com.ratushny.modulotech.presentation.screen.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat

class UserFragment : BaseFragment<UserScreenState, FragmentUserBinding, UserViewModel>() {


    override val viewModel: UserViewModel by viewModel()

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentUserBinding {
        return FragmentUserBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
                menuInflater.inflate(R.menu.menu_user_profile, menu)

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.action_confirm -> {
                        updateData()
                        viewModel.updateUserData()
                        hideKeyboard()

                        Toast.makeText(
                            context,
                            getString(R.string.data_updated),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        observeData()
        viewModel.refreshUserData()
    }

    private fun updateData() {
        with(viewModel) {
            with(binding) {
                updateFirstName(firstNameText.text.toString())
                updateLastName(lastNameText.text.toString())
                updateBirthdate(birthdateText.text.toString())
                updateCity(cityText.text.toString())
                updatePostalCode(postalCodeText.text.toString())
                updateStreet(streetText.text.toString())
                updateStreetCode(streetCodeText.text.toString())
                updateCountry(countryText.text.toString())
            }
        }
    }

    private fun observeData() {
        with(viewModel) {
            firstName.observe(viewLifecycleOwner) {
                binding.firstNameText.setText(it)
            }

            lastName.observe(viewLifecycleOwner) {
                binding.lastNameText.setText(it)
            }

            birthdate.observe(viewLifecycleOwner) {
                binding.birthdateText.setText(
                    DateFormat.getDateInstance(DateFormat.SHORT).format(it)
                )
            }

            city.observe(viewLifecycleOwner) {
                binding.cityText.setText(it)
            }

            postalCode.observe(viewLifecycleOwner) {
                binding.postalCodeText.setText(it.toString())
            }

            street.observe(viewLifecycleOwner) {
                binding.streetText.setText(it)
            }

            streetCode.observe(viewLifecycleOwner) {
                binding.streetCodeText.setText(it)
            }

            country.observe(viewLifecycleOwner) {
                binding.countryText.setText(it)
            }
        }
    }

    override fun screenStateObserver(): Observer<UserScreenState> {
        //TODO
        return Observer { }
    }


}