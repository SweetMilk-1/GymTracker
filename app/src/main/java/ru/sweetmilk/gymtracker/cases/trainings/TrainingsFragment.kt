package ru.sweetmilk.gymtracker.cases.trainings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import ru.sweetmilk.gymtracker.GymApplication
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.databinding.FragTrainingsBinding
import javax.inject.Inject

class TrainingsFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<TrainingsViewModel> {
        viewModelProviderFactory
    }

    private var _binding: FragTrainingsBinding? = null
    private val binding get() = _binding!!

    private var _startTrainingButtonAnimator: StartTrainingButtonAnimator? = null
    private val startTrainingButtonAnimator get() = _startTrainingButtonAnimator!!

    private lateinit var adapter: TrainingsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as GymApplication).appComponent
            .addTrainingsComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragTrainingsBinding.inflate(inflater, container, false)
        _startTrainingButtonAnimator = StartTrainingButtonAnimator(binding, requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner



        setupTrainingList()
        setupBottomSheet()
    }

    private fun setupBottomSheet() {
        BottomSheetBehavior.from<View>(binding.bottomSheet)
            .addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {}

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    startTrainingButtonAnimator.updateTrainingBtn(slideOffset)
                }
            })

        var isInitialized = false
        requireView().viewTreeObserver.addOnDrawListener {
            if (isInitialized)
                return@addOnDrawListener
            isInitialized = true
            startTrainingButtonAnimator.updateTrainingBtn(0f)
        }
    }


    private fun setupTrainingList() {
        adapter = TrainingsAdapter(viewModel, layoutInflater)
        binding.trainingsList.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) {
            if (it is Result.Success)
                adapter.submitList(it.data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _startTrainingButtonAnimator = null
    }
}