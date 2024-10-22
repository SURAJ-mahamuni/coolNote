package com.example.coolnote.fragment

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.coolnote.R
import com.example.coolnote.activity.MainActivity
import com.example.coolnote.databinding.FragmentAddNoteBinding
import com.example.coolnote.model.NoteModel
import com.example.coolnote.utils.JsonConvertor
import com.example.coolnote.utils.toastMsg
import com.example.coolnote.viewModel.AddNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNoteFragment : BindingFragment<FragmentAddNoteBinding>(), MenuProvider {

    private val addNoteViewModel by viewModels<AddNoteViewModel>()

    private val args by navArgs<AddNoteFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = JsonConvertor.jsonToObject<NoteModel>(args.noteData ?: "")
        Log.e("data", args.noteData ?: "")
        data?.let {
            addNoteViewModel.noteData = it
        }
    }

    override val backPressedHandler: () -> Unit
        get() = {}
    override val onDestroyViewHandler: () -> Unit
        get() = {}
    override val onCreateViewHandler: () -> Unit
        get() = {
            binding.viewModel = addNoteViewModel
            binding.lifecycleOwner = this
        }
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentAddNoteBinding::inflate


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
        binding.addNoteToolbar.title = ""
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
        val title = addNoteViewModel.noteData.noteTitle ?: ""
        val note = addNoteViewModel.noteData.noteInfo ?: ""
        if (title.isNotEmpty() && note.isNotEmpty()) {
            addNoteViewModel.addNote(
                NoteModel(
                    addNoteViewModel.noteData.noteId,
                    title,
                    note
                )
            )
            findNavController().popBackStack()
            toastMsg("Note Added!")
        }
    }
}