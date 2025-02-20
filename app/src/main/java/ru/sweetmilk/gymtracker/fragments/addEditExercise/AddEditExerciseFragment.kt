package ru.sweetmilk.gymtracker.fragments.addEditExercise

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
import ru.sweetmilk.gymtracker.R
import ru.sweetmilk.gymtracker.databinding.FragAddEditExerciseBinding
import java.util.UUID
import javax.inject.Inject

class AddEditExerciseFragment : Fragment() {
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AddEditExerciseViewModel> {
        viewModelProviderFactory
    }

    private var _binding: FragAddEditExerciseBinding? = null
    private val binding get() = _binding!!

    private val args: AddEditExerciseFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as GymApplication).appComponent
            .addAddEditExerciseComponent()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val exerciseId =
            if (!args.exerciseId.isNullOrEmpty()) UUID.fromString(args.exerciseId) else null
        viewModel.init(exerciseId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragAddEditExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupFormValidation()
        setupSnackBar()
        setupNavigation()
    }

    private fun setupFormValidation() {
        viewModel.nameInvalidMessage.observe(viewLifecycleOwner) {
            binding.exerciseName.error = if (it != null) getString(it) else null
        }
    }

    private fun setupSnackBar() {
        viewModel.snackbarMessageEvent.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupNavigation() {
        viewModel.saveCompletedEvent.observe(viewLifecycleOwner) {
            val action = AddEditExerciseFragmentDirections.actionAddEditExerciseToExerciseDetails(
                it.first.toString(),
                it.second,
                R.string.exercise_saved
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}