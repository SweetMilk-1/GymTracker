package ru.sweetmilk.gymtracker.fragments.addEditTrainingPlanExercise

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import ru.sweetmilk.gymtracker.GymApplication
import ru.sweetmilk.gymtracker.databinding.FragAddEditTrainingPlanExerciseBinding
import java.util.UUID

import javax.inject.Inject

class AddEditTrainingPlanExerciseFragment : Fragment() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AddEditTrainingPlanExerciseViewModel> {
        viewModelProviderFactory
    }
    private val args: AddEditTrainingPlanExerciseFragmentArgs by navArgs()

    private var _binding: FragAddEditTrainingPlanExerciseBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TrainingPlanSetsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as GymApplication).appComponent
            .addAddEditTrainingPlanExerciseComponent()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.init(
            args.trainingPlanExercise,
            args.excludedExerciseIds?.map { UUID.fromString(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragAddEditTrainingPlanExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupNavigation()
        setupExerciseSelector()
        setupTrainingPlanSets()
        setupSnackBar()
    }

    private fun setupNavigation() {
        viewModel.saveTrainingPlanItems.observe(viewLifecycleOwner) {
            val action =
                AddEditTrainingPlanExerciseFragmentDirections.actionAddEditTrainingPlanExerciseToTrainingPlan(
                    it
                )
            findNavController().navigate(action)
        }
    }

    private fun setupSnackBar() {
        viewModel.snackbarMessageEvent.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupTrainingPlanSets() {
        adapter = TrainingPlanSetsAdapter(viewModel, layoutInflater)
        binding.trainingPlanSets.adapter = adapter
        viewModel.trainingPlanSets.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupExerciseSelector() {
        viewModel.exercises.observe(viewLifecycleOwner) { exercises ->
            val exerciseNames = exercises.map { it.name }
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, exerciseNames)
            binding.exerciseSelector.apply {
                setAdapter(adapter)
            }
        }
        binding.exerciseSelector.setOnItemClickListener { _, _, position, _ ->
            viewModel.selectExercise(position)
        }
        viewModel.initExerciseEvent.observe(viewLifecycleOwner) {
            val selectedValue = binding.exerciseSelector.adapter.getItem(it)
            binding.exerciseSelector.setText(selectedValue.toString(), false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}