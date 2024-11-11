package ru.sweetmilk.gymtracker.cases.exerciseDetails

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import ru.sweetmilk.gymtracker.GymApplication
import ru.sweetmilk.gymtracker.R
import ru.sweetmilk.gymtracker.cases.addEditExercise.AddEditExerciseFragmentArgs
import ru.sweetmilk.gymtracker.cases.addEditExercise.AddEditExerciseViewModel
import ru.sweetmilk.gymtracker.databinding.FragAddEditExerciseBinding
import ru.sweetmilk.gymtracker.databinding.FragExerciseDetailsBinding
import java.util.UUID
import javax.inject.Inject

class ExerciseDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ExerciseDetailsViewModel> {
        viewModelProviderFactory
    }

    private var _binding: FragExerciseDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: ExerciseDetailsFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as GymApplication).appComponent
            .addExerciseDetailsComponent()
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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragExerciseDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.showSnackBarMessageEvent.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.updateExerciseEvent.observe(viewLifecycleOwner) {
            val action = ExerciseDetailsFragmentDirections.actionExerciseDetailsToAddEditExercise(
                it.toString(),
                getString(R.string.update_exercise_label)
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}