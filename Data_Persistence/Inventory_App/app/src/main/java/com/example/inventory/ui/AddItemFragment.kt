package com.example.inventory.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventory.data.InventoryApplication
import com.example.inventory.data.Item
import com.example.inventory.databinding.FragmentAddItemBinding
import com.example.inventory.viewModel.InventoryViewModel
import com.example.inventory.viewModel.InventoryViewModelFactory

/**
 * Fragment to add or update an item in the Inventory database.
 */
class AddItemFragment : Fragment() {

    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    // Binding object instance corresponding to the fragment_add_item.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    /**
     * Use the by activityViewModels() Kotlin property delegate to share the ViewModel across fragments.
     */
    private val viewModel: InventoryViewModel by activityViewModels{
        InventoryViewModelFactory((activity?.application as InventoryApplication).itemDatabase.getItemDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId

        // Edit Item Screen - pre-populate the TextViews
        if (id > 0){
            viewModel.getAnItem(id).observe(viewLifecycleOwner){
                bind(it)
            }
            binding.saveAction.setOnClickListener{
                fetchAndUpdate()
            }

        } else // Add Item
        {
            binding.saveAction.setOnClickListener{
                grabAndAdd()
            }
        }
    }

    private fun isEntryValid(): Boolean{
        return viewModel.isEntryValid(
            binding.itemName.text.toString(),
            binding.itemPrice.text.toString(),
            binding.itemCount.text.toString()
        )
    }

    private fun grabAndAdd() {
            if (isEntryValid()){
                viewModel.addNewItem(binding.itemName.text.toString(),
                    binding.itemPrice.text.toString(),
                    binding.itemCount.text.toString())

                val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
                findNavController().navigate(action)
            }
    }

    private fun fetchAndUpdate(){
        if (isEntryValid()){
            viewModel.updateExistingItem(
                navigationArgs.itemId,
                binding.itemName.text.toString(),
                binding.itemPrice.text.toString(),
                binding.itemCount.text.toString()
            )

            val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
            findNavController().navigate(action)
        }
    }

    private fun bind(item: Item){
        val price = "%.2f".format(item.itemPrice)
        binding.apply {
            itemName.setText(item.itemName, TextView.BufferType.SPANNABLE)
            itemPrice.setText(price, TextView.BufferType.SPANNABLE)
            itemCount.setText(item.quantityInStock.toString(), TextView.BufferType.SPANNABLE)
        }
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}
