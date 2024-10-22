package com.example.coolnote.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.coolnote.viewModel.HomeViewModel
import com.example.coolnote.R
import com.example.coolnote.adapters.GenericAdapter
import com.example.coolnote.databinding.FragmentHomeBinding
import com.example.coolnote.databinding.NoteItemBinding
import com.example.coolnote.model.NoteModel
import com.example.coolnote.utils.JsonConvertor
import com.example.coolnote.utils.setupUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>() {

    private val homeViewModel by viewModels<HomeViewModel>()

    private var _adapter: GenericAdapter<NoteModel, NoteItemBinding>? = null
    private val adapter get() = _adapter!!

    override val backPressedHandler: () -> Unit
        get() = {}
    override val onDestroyViewHandler: () -> Unit
        get() = {}
    override val onCreateViewHandler: () -> Unit
        get() = {}
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI(view){}

        initializeAdapter()

        observables()

        initializeListener()

    }

    private fun observables() {
        homeViewModel.getAllNote().observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.lottieAnimation.visibility = View.VISIBLE
                binding.noteRv.visibility = View.GONE
                binding.addNoteMsg.visibility = View.VISIBLE
            } else {
                binding.lottieAnimation.visibility = View.GONE
                binding.noteRv.visibility = View.VISIBLE
                binding.addNoteMsg.visibility = View.GONE
                adapter.setData(it as ArrayList<NoteModel>)
            }
        }
    }

    private fun initializeListener() {
        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchDataBase(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchDataBase(newText)
                } else {
                    searchDataBase("")
                }
                return true
            }
        })
        binding.addNoteFAB.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addNoteFragment)
        }
    }

    private fun searchDataBase(query: String) {
        val searchQuery = "%${query.trim()}%"
        if (searchQuery.isNotEmpty()) {
            binding.lottieAnimation.visibility = View.GONE
            homeViewModel.getNotes(searchQuery).observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    adapter.setData(it as ArrayList<NoteModel>)
                } else {
                    adapter.setData(ArrayList())
                    binding.lottieAnimation.visibility = View.VISIBLE
                }
            }

        } else {
            binding.lottieAnimation.visibility = View.VISIBLE
        }

    }

    private fun initializeAdapter() {
        _adapter = GenericAdapter(
            NoteItemBinding::inflate,
            onBind = { itemData, itemBinding, position, listSize ->
                itemBinding.apply {
                    noteTitle.text = itemData.noteTitle
                    noteInfo.text = itemData.noteInfo
                    container.setOnClickListener {
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToAddNoteFragment(
                                JsonConvertor.toJason(
                                    itemData
                                )
                            )
                        )
                    }
                }
            })
        binding.noteRv.adapter = adapter
    }


}