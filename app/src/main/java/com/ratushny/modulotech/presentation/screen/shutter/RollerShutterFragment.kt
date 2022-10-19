package com.ratushny.modulotech.presentation.screen.shutter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.ratushny.modulotech.databinding.FragmentRollerShutterBinding
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
        viewModel.setDeviceValues(device)

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

    override fun screenStateObserver(): Observer<RollerShutterScreenState> {
        //TODO
        return Observer { }
    }
}