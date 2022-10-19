package com.ratushny.modulotech.presentation.screen.light

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ratushny.modulotech.databinding.FragmentLightBinding
import com.ratushny.modulotech.domain.model.device.DeviceMode
import com.ratushny.modulotech.presentation.extensions.doOnProgressChanged
import com.ratushny.modulotech.presentation.screen.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LightFragment : BaseFragment<LightScreenState, FragmentLightBinding, LightViewModel>() {

    override val viewModel: LightViewModel by viewModel()

    private val args by navArgs<LightFragmentArgs>()

    private val device by lazy { args.device }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLightBinding {
        return FragmentLightBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        viewModel.setDevice(device)
        binding.modeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setMode(isChecked)
        }
        binding.seekbar.doOnProgressChanged {
            viewModel.setIntensity(it)
        }
    }

    override fun screenStateObserver(): Observer<LightScreenState> = Observer {
        with(binding) {
            modeSwitch.isChecked = it.light.mode == DeviceMode.ON
            seekbar.progress = it.light.intensity
            seekbarValue.text = it.light.intensity.toString()
        }
    }
}