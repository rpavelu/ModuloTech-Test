package com.ratushny.modulotech.presentation.screen.light

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.navigation.fragment.navArgs
import com.ratushny.modulotech.databinding.LightFragmentBinding
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class LightFragment : ScopeFragment() {

    private var _binding: LightFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LightViewModel by viewModel()

    private val args by navArgs<LightFragmentArgs>()

    private val device by lazy { args.device }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LightFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.mode.value == null && viewModel.intensity.value == null) {
            viewModel.setDeviceValues(device)
        }

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
}