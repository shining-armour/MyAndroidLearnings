package com.example.inventory.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventory.R
import com.example.inventory.data.InventoryApplication
import com.example.inventory.data.Item
import com.example.inventory.data.formatPrice
import com.example.inventory.databinding.FragmentItemDetailBinding
import com.example.inventory.viewModel.InventoryViewModel
import com.example.inventory.viewModel.InventoryViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * [ItemDetailFragment] displays the details of the selected item.
 */
class ItemDetailFragment : Fragment() {
    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var item:Item

    private val viewModel: InventoryViewModel by activityViewModels{
        InventoryViewModelFactory((activity?.application as InventoryApplication).itemDatabase.getItemDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAnItem(navigationArgs.itemId).observe(viewLifecycleOwner){
            item = it
            bind(item)
        }
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the item.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { dialog, _ ->  dialog.dismiss()}
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteItem()
            }
            .show()
    }

    private fun bind(item:Item){
        binding.apply {
            itemName.text = item.itemName
            itemPrice.text = item.formatPrice()
            itemCount.text = item.quantityInStock.toString()
            sellItem.isEnabled = viewModel.isStockAvailable(item)
            sellItem.setOnClickListener{
                viewModel.sellItem(item)
            }
            deleteItem.setOnClickListener {
                showConfirmationDialog()
            }

            editItem.setOnClickListener{
                editItem()
            }
        }
    }

    /**
     * Deletes the current item and navigates to the list fragment.
     */
    private fun deleteItem() {
        viewModel.deleteItemFromDB(item)
        findNavController().navigateUp()
    }

    private fun editItem(){
        val action = ItemDetailFragmentDirections.actionItemDetailFragmentToAddItemFragment(getString(
            R.string.edit_fragment_title
        ), item.id)
        findNavController().navigate(action)
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
