package ru.sweetmilk.gymtracker.cases.trainingPlan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import ru.sweetmilk.gymtracker.GymApplication
import ru.sweetmilk.gymtracker.data.Result

import ru.sweetmilk.gymtracker.databinding.FragTrainingPlanBinding
import javax.inject.Inject

class TrainingPlanFragment : Fragment() {
    private lateinit var adapter: ExerciseAndTrainingPlanItemsAdapter

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<TrainingPlanViewModel> {
        viewModelProviderFactory
    }

    val args: TrainingPlanFragmentArgs by navArgs()

    private var _binding: FragTrainingPlanBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as GymApplication).appComponent
            .addTrainingPlanComponent()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(args.updatedTrainingPlanExercise)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragTrainingPlanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupNavigation()
        setupSnackBar()
        setupTrainingPlanExercises()
    }

    private fun setupTrainingPlanExercises() {
        adapter = ExerciseAndTrainingPlanItemsAdapter(layoutInflater, viewModel)
        binding.trainingPlanExercises.adapter = adapter
        viewModel.trainingPlanExercises.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> adapter.submitList(it.data)
                else -> Unit
            }
        }
    }

    private fun setupNavigation() {
        viewModel.createNewTrainingPlanExerciseEvent.observe(viewLifecycleOwner) {
            val action =
                TrainingPlanFragmentDirections.actionTrainingPlanToNavAddEditTrainingPlanExercise(
                    null,
                    viewModel.getUsingExerciseIds()?.toTypedArray()
                )
            findNavController().navigate(action)
        }
        viewModel.updateTrainingPlanExerciseEvent.observe(viewLifecycleOwner) {
            val action =
                TrainingPlanFragmentDirections.actionTrainingPlanToNavAddEditTrainingPlanExercise(
                    it,
                    viewModel.getUsingExerciseIds()?.toTypedArray()
                )
            findNavController().navigate(action)
        }
    }

    private fun setupSnackBar() {
        viewModel.snackbarMessageEvent.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}