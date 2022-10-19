package com.ratushny.modulotech.presentation.screen.heater

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ratushny.modulotech.databinding.FragmentHeaterBinding
import com.ratushny.modulotech.presentation.screen.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeaterFragment : BaseFragment<HeaterScreenState, FragmentHeaterBinding, HeaterViewModel>() {

    override val viewModel: HeaterViewModel by viewModel()

    private val args by navArgs<HeaterFragmentArgs>()

    private val device by lazy { args.device }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHeaterBinding {
        return FragmentHeaterBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        viewModel.setDeviceValues(device)

        binding.modeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setMode(isChecked, device)
        }

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.setTemperature(progress.toFloat() / 2 + MINIMAL_TEMPERATURE, device)
            }

            override fun onStartTrackingTouch(seekbar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekbar: SeekBar?) {

            }
        })

        viewModel.mode.observe(viewLifecycleOwner) {
            binding.modeSwitch.isChecked = it
        }

        viewModel.temperature.observe(viewLifecycleOwner) {
            binding.seekbarValue.text = "$it$CELSIUS_SYMBOL"
            binding.seekbar.progress = ((it - MINIMAL_TEMPERATURE) * 2).toInt()
        }
    }

    override fun screenStateObserver(): Observer<HeaterScreenState> {
        //TODO
        return Observer<HeaterScreenState> { }
    }

    companion object {
        private const val CELSIUS_SYMBOL = " \u2103"
        private const val MINIMAL_TEMPERATURE = 7
    }
}