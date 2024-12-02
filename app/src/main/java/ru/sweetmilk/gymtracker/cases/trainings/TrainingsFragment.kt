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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

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
    }
}