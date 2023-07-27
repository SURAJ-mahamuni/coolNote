package com.example.coolnote.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coolnote.R
import com.example.coolnote.activity.MainActivity
import com.example.coolnote.databinding.FragmentAddNoteBinding
import com.example.coolnote.model.NoteModel
import com.example.coolnote.viewModel.AddNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : Fragment(), MenuProvider {
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    val addNoteViewModel by viewModels<AddNoteViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBar()
        activity?.addMenuProvider(this)
        initializeListener()

    }

    private fun initializeListener() {
        binding.addNoteToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setActionBar() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.addNoteToolbar)
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as MainActivity).removeMenuProvider(this)
        activity?.removeMenuProvider(this)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.add_note_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.done -> {
                addNoteAndGoHome()
                true
            }

            else -> false
        }
    }

    private fun addNoteAndGoHome() {
        val title = binding.noteTitle.text.toString()
        val note = binding.note.text.toString()
        if (title.isNotBlank() && note.isNotBlank()) {
            addNoteViewModel.addNote(
                NoteModel(
                    null,
                    binding.noteTitle.text.toString(),
                    binding.note.text.toString()
                )
            )
            findNavController().popBackStack()
            Toast.makeText(requireContext(), "Note Added!", Toast.LENGTH_SHORT).show()
        }
    }
}