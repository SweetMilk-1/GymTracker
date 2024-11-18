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
        viewModel.init(args.exerciseAndTrainingPlanItems)
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
        setupExercisesList()
    }

    private fun setupExercisesList() {
        adapter = ExerciseAndTrainingPlanItemsAdapter(layoutInflater, viewModel)
        binding.exercisesList.adapter = adapter
        viewModel.exercisesAndTrainingPlanItems.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Success -> adapter.submitList(it.data)
                else -> Unit
            }
        }
    }

    private fun setupNavigation() {
        viewModel.createNewTrainingPlanItemEvent.observe(viewLifecycleOwner) {
            val action =
                TrainingPlanFragmentDirections.actionTrainingPlanToNavAddEditTrainingPlanItem(
                    null,
                    viewModel.getUsingExerciseIds()?.toTypedArray()
                )
            findNavController().navigate(action)
        }
        viewModel.updateTrainingPlanItemEvent.observe(viewLifecycleOwner) {
            val action =
                TrainingPlanFragmentDirections.actionTrainingPlanToNavAddEditTrainingPlanItem(
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