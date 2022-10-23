package com.ratushny.modulotech.presentation.screen.heater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ratushny.modulotech.R
import com.ratushny.modulotech.databinding.FragmentHeaterBinding
import com.ratushny.modulotech.domain.model.device.DeviceMode
import com.ratushny.modulotech.presentation.extensions.doOnProgressChanged
import com.ratushny.modulotech.presentation.screen.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeaterFragment : BaseFragment<HeaterScreenState, FragmentHeaterBinding, HeaterViewModel>() {

    override val viewModel: HeaterViewModel by viewModel()

    private val args by navArgs<HeaterFragmentArgs>()

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHeaterBinding {
        return FragmentHeaterBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        viewModel.setDevice(args.device)

        binding.switchMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setMode(isChecked)
        }

        binding.seekbar.doOnProgressChanged {
            viewModel.setRawTemperature(it)
        }
    }

    override fun screenStateObserver(): Observer<HeaterScreenState> = Observer<HeaterScreenState> {
        with(binding) {
            switchMode.isChecked = it.heater.mode == DeviceMode.ON
            seekbar.apply {
                max = ((it.maxTemp - it.minTemp) / it.temperatureStep).toInt()
            }

            textSeekbarValue.text =
                getString(R.string.format_heater_temperature, it.heater.temperature)
            seekbar.progress = it.currentRawTemp
        }
    }
}