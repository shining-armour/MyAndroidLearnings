package com.example.inventory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inventory.R
import com.example.inventory.adapter.ItemListAdapter
import com.example.inventory.data.InventoryApplication
import com.example.inventory.databinding.ItemListFragmentBinding
import com.example.inventory.viewModel.InventoryViewModel
import com.example.inventory.viewModel.InventoryViewModelFactory

/**
 * Main fragment displaying details for all items in the database.
 */
class ItemListFragment : Fragment() {

    private var _binding: ItemListFragmentBinding? = null
    private val binding get() = _binding!!

    // create viewModel instance using viewModel factory method
    private val viewModel: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory((activity?.application as InventoryApplication).itemDatabase.getItemDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ItemListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set Layout Manager for the RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        val itemAdapter = ItemListAdapter{
            val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(it.id)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = itemAdapter

        viewModel.getAllItems.observe(viewLifecycleOwner){
            it.let {
                itemAdapter.submitList(it)
            }
        }
        binding.floatingActionButton.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(getString(
                R.string.add_fragment_title
            ))
            this.findNavController().navigate(action)
        }
    }
}
