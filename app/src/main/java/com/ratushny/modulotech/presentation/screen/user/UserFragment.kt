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
import com.google.android.material.textfield.TextInputEditText
import com.ratushny.modulotech.R
import com.ratushny.modulotech.databinding.FragmentUserBinding
import com.ratushny.modulotech.presentation.extensions.doOnTextChanged
import com.ratushny.modulotech.presentation.extensions.updateTextIfNeeded
import com.ratushny.modulotech.presentation.screen.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

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
                        viewModel.handleSaveClicked()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        initTextChangedListeners()

        viewModel.savedEvent.observe(viewLifecycleOwner) {
            Toast.makeText(context, R.string.data_updated, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initTextChangedListeners() {
        with(binding) {
            firstNameText.doOnTextChanged { viewModel.updateFirstName(it) }
            lastNameText.doOnTextChanged { viewModel.updateLastName(it) }
            birthdateText.doOnTextChanged { viewModel.updateBirthdate(it) }
            cityText.doOnTextChanged { viewModel.updateCity(it) }
            postalCodeText.doOnTextChanged { viewModel.updatePostalCode(it) }
            streetText.doOnTextChanged { viewModel.updateStreet(it) }
            streetCodeText.doOnTextChanged { viewModel.updateStreetCode(it) }
            countryText.doOnTextChanged { viewModel.updateCountry(it) }
        }
    }

    override fun screenStateObserver(): Observer<UserScreenState> {
        return Observer {
            with(binding) {
                firstNameText.updateTextIfNeeded(it.firstName.value)
                lastNameText.updateTextIfNeeded(it.lastName.value)
                birthdateText.updateTextIfNeeded(it.birthDate.value)
                cityText.updateTextIfNeeded(it.city.value)
                postalCodeText.updateTextIfNeeded(it.postalCode.value)
                streetText.updateTextIfNeeded(it.street.value)
                streetCodeText.updateTextIfNeeded(it.streetCode.value)
                countryText.updateTextIfNeeded(it.country.value)

                // Show errors
                firstNameText.setError(it.firstName.error)
                lastNameText.setError(it.lastName.error)
                birthdateText.setError(it.birthDate.error)
                cityText.setError(it.city.error)
                postalCodeText.setError(it.postalCode.error)
                streetText.setError(it.street.error)
                streetCodeText.setError(it.streetCode.error)
                countryText.setError(it.country.error)
            }
        }
    }

    private fun TextInputEditText.setError(error: UserScreenState.FieldError?) {
        when (error) {
            is UserScreenState.DateFormatError -> setError(
                getString(
                    R.string.user_error_date_format,
                    error.dateFormat
                )
            )
            UserScreenState.EmptyFieldError -> setError(getString(R.string.user_error_empty))
            is UserScreenState.MinLengthError -> setError(
                getString(R.string.user_error_min_length, error.minLength),
            )
            null -> setError(null)
        }
    }


}