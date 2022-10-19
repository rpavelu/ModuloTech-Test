package com.ratushny.modulotech.presentation.screen.light

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ratushny.modulotech.databinding.FragmentLightBinding
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
        viewModel.setDeviceValues(device)

        binding.modeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setMode(isChecked, device)
        }

        binding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.setIntensity(progress, device)
            }

            override fun onStartTrackingTouch(seekbar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekbar: SeekBar?) {

            }
        })

        viewModel.mode.observe(viewLifecycleOwner) {
            binding.modeSwitch.isChecked = it
        }

        viewModel.intensity.observe(viewLifecycleOwner) {
            binding.seekbarValue.text = it.toString()
            binding.seekbar.progress = it
        }
    }

    override fun screenStateObserver(): Observer<LightScreenState> {
        //TODO
        return Observer { }
    }
}