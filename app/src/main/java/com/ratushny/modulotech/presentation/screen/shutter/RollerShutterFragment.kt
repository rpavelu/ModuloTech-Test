package com.ratushny.modulotech.presentation.screen.shutter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.navigation.fragment.navArgs
import com.ratushny.modulotech.databinding.RollerShutterFragmentBinding
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class RollerShutterFragment : ScopeFragment() {

    private var _binding: RollerShutterFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RollerShutterViewModel by viewModel()

    private val args by navArgs<RollerShutterFragmentArgs>()

    private val device by lazy { args.device }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RollerShutterFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.position.value == null) {
            viewModel.setDeviceValues(device)
        }

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.setPosition(progress, device)
            }

            override fun onStartTrackingTouch(seekbar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekbar: SeekBar?) {

            }
        })

        viewModel.position.observe(viewLifecycleOwner) {
            binding.seekbarValue.text = it.toString()
            binding.seekbar.progress = it
        }
    }
}