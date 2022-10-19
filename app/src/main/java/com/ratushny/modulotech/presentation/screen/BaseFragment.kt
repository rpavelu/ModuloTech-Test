package com.ratushny.modulotech.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import org.koin.androidx.scope.ScopeFragment

abstract class BaseFragment<State, VB : ViewBinding, VM : BaseViewModel<State>> : ScopeFragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    protected abstract val viewModel: VM

    protected val navigation by lazy { findNavController() }

    abstract fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): VB

    abstract fun initViews()

    abstract fun screenStateObserver(): Observer<State>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateView(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.attach()
        viewModel.screenState.observe(viewLifecycleOwner, screenStateObserver())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}