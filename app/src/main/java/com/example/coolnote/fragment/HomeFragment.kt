package com.example.coolnote.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.coolnote.viewModel.HomeViewModel
import com.example.coolnote.R
import com.example.coolnote.adapters.NotesAdapter
import com.example.coolnote.databinding.FragmentHomeBinding
import com.example.coolnote.model.NoteModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val homeViewModel by viewModels<HomeViewModel>()
    private val adapter = NotesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeAdapter()
        initializeListener()
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
                }
                else{
                    binding.lottieAnimation.visibility = View.VISIBLE
                }
            }

        } else {
            binding.lottieAnimation.visibility = View.VISIBLE
        }

    }

    private fun initializeAdapter() {

        homeViewModel.getAllNote().observe(viewLifecycleOwner) {
            Log.e("homeTag", it.toString())
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
        binding.noteRv.adapter = adapter
    }

}