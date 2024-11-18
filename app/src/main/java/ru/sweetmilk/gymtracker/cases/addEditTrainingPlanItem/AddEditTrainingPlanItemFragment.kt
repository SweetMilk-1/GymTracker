package ru.sweetmilk.gymtracker.cases.addEditTrainingPlanItem

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.sweetmilk.gymtracker.GymApplication
import ru.sweetmilk.gymtracker.databinding.FragAddEditTrainingPlanItemBinding

import javax.inject.Inject

class AddEditTrainingPlanItemFragment : Fragment() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AddEditTrainingPlanItemViewModel> {
        viewModelProviderFactory
    }

    private var _binding: FragAddEditTrainingPlanItemBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TrainingPlanListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as GymApplication).appComponent
            .addAddEditTrainingPlanItemComponent()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragAddEditTrainingPlanItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupExerciseSelector()
        setupTrainingPlanList()
        setupSnackBar()
    }

    private fun setupSnackBar() {
        viewModel.snackbarMessageEvent.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        }
    }


    private fun setupTrainingPlanList() {
        adapter = TrainingPlanListAdapter(viewModel, layoutInflater)
        binding.trainingPlanList.adapter = adapter
        viewModel.trainingPlanItems.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupExerciseSelector() {
        viewModel.exercises.observe(viewLifecycleOwner) { exercises ->
            val exerciseNames = exercises.map { it.name }
            val adapter =
                ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, exerciseNames)
            binding.exerciseSelector.apply {
                setAdapter(adapter)
            }
        }
        binding.exerciseSelector.setOnItemClickListener { _, _, position, _ ->
            viewModel.selectExercise(position)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}