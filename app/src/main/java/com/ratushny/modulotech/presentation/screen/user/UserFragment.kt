package com.ratushny.modulotech.presentation.screen.user

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.ratushny.modulotech.R
import com.ratushny.modulotech.databinding.UserFragmentBinding
import com.ratushny.modulotech.presentation.extensions.hideKeyboard
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat

class UserFragment : ScopeFragment() {

    private var _binding: UserFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = UserFragmentBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
                menuInflater.inflate(R.menu.user_menu, menu)

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.action_confirm -> {
                        viewModel.updateUserData()
                        hideKeyboard()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeData()
        observeEditTextListeners()

        if (viewModel.firstName.value.isNullOrEmpty() && viewModel.lastName.value.isNullOrEmpty()) viewModel.refreshUserData()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeEditTextListeners() {
        binding.apply {
            firstNameText.addTextChangedListener(object : UserTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    if (viewModel.firstName.value != text) viewModel.updateFirstName(text)
                }
            })

            lastNameText.addTextChangedListener(object : UserTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    if (viewModel.lastName.value != text) viewModel.updateLastName(text)
                }
            })

            birthdateText.addTextChangedListener(object : UserTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)

                    val date = DateFormat.getDateInstance(DateFormat.SHORT).parse(text)
                    if (viewModel.birthdate.value != date) date?.let { viewModel.updateBirthdate(it) }
                }
            })

            cityText.addTextChangedListener(object : UserTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    if (viewModel.city.value != text) viewModel.updateCity(text)
                }
            })

            postalCodeText.addTextChangedListener(object : UserTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)

                    val code = text.toInt()
                    if (viewModel.postalCode.value != code) viewModel.updatePostalCode(code)
                }
            })

            streetText.addTextChangedListener(object : UserTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    if (viewModel.street.value != text) viewModel.updateStreet(text)
                }
            })

            streetCodeText.addTextChangedListener(object : UserTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    if (viewModel.streetCode.value != text) viewModel.updateStreetCode(text)
                }
            })

            countryText.addTextChangedListener(object : UserTextWatcher() {
                override fun onTextChanged(text: String) {
                    super.onTextChanged(text)
                    if (viewModel.country.value != text) viewModel.updateCountry(text)
                }
            })
        }
    }

    private fun observeData() {
        viewModel.apply {
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
}