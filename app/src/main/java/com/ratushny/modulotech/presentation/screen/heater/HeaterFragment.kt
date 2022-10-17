package com.ratushny.modulotech.presentation.screen.heater

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.ratushny.modulotech.databinding.HeaterFragmentBinding
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeaterFragment : ScopeFragment() {

    private var _binding: HeaterFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HeaterViewModel by viewModel()

    private val args by navArgs<HeaterFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HeaterFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Here goes everything
    }
}