package ru.sweetmilk.gymtracker.cases.exercises

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
import ru.sweetmilk.gymtracker.data.Result
import ru.sweetmilk.gymtracker.databinding.FragExercisesBinding
import javax.inject.Inject

class ExercisesFragment : Fragment() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ExercisesViewModel> {
        viewModelProviderFactory
    }

    private var _binding: FragExercisesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ExercisesAdapter

    private val args: ExercisesFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as GymApplication).appComponent
            .addExercisesComponent()
            .create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        adapter = ExercisesAdapter(viewModel, layoutInflater)
        binding.exercisesList.adapter = adapter

        if (args.message != 0) {
            Snackbar.make(requireView(), args.message, Snackbar.LENGTH_SHORT).show()
        }

        viewModel.createNewExerciseEvent.observe(viewLifecycleOwner) {
            val action = ExercisesFragmentDirections.actionExercisesToAddEditExercise(
                null,
                getString(R.string.new_exercise_label)
            )
            findNavController().navigate(action)
        }

        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList((it as Result.Success).data)
        }

        viewModel.openExerciseEvent.observe(viewLifecycleOwner) {
            val action = ExercisesFragmentDirections.actionExercisesToExerciseDetails(
                it.first.toString(),
                it.second
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}