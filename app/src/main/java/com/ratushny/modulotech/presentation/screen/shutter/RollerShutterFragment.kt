package com.ratushny.modulotech.presentation.screen.shutter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ratushny.modulotech.databinding.FragmentRollerShutterBinding
import com.ratushny.modulotech.presentation.extensions.doOnProgressChanged
import com.ratushny.modulotech.presentation.screen.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RollerShutterFragment :
    BaseFragment<RollerShutterScreenState, FragmentRollerShutterBinding, RollerShutterViewModel>() {


    override val viewModel: RollerShutterViewModel by viewModel()

    private val args by navArgs<RollerShutterFragmentArgs>()

    private val device by lazy { args.device }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRollerShutterBinding {
        return FragmentRollerShutterBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        viewModel.setDevice(device)
        binding.seekbar.doOnProgressChanged {
            viewModel.setPosition(it)
        }
    }

    override fun screenStateObserver(): Observer<RollerShutterScreenState> = Observer {
        with(binding) {
            seekbar.progress = it.rollerShutter.position
            seekbarValue.text = it.rollerShutter.position.toString()
        }
    }
}