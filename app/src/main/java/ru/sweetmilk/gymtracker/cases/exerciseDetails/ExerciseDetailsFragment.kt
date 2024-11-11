package ru.sweetmilk.gymtracker.cases.exerciseDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
//import android.view.Menu
//import android.view.MenuInflater
//import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
//import androidx.core.view.MenuHost
//import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import ru.sweetmilk.gymtracker.GymApplication
import ru.sweetmilk.gymtracker.R
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

        viewModel.deleteExerciseCompletedEvent.observe(viewLifecycleOwner) {
            val action = ExerciseDetailsFragmentDirections.actionExerciseDetailsToExercises(
                R.string.delete_exercise_completed
            )
            findNavController().navigate(action)
        }

        setupMenuProvider()
    }

    private fun setupMenuProvider() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.exercise_details_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_delete -> {
                            viewModel.deleteExercise()
                            true
                        }
                        else -> false
                    }
                }
            },
            viewLifecycleOwner, Lifecycle.State.RESUMED
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}