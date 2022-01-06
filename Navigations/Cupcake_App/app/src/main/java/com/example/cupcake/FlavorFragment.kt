package com.example.cupcake

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentFlavorBinding

/**
 * [FlavorFragment] allows a user to choose a cupcake flavor for the order.
 */
class FlavorFragment : Fragment() {

    /**
     * Binding object instance corresponding to the fragment_start.xml layout
     * This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks, when the view hierarchy is attached to the fragment.
     */
    private var _binding: FragmentFlavorBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("FlavorFragment", "onCreateView called.......")
        _binding = FragmentFlavorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.i("FlavorFragment", "onViewCreated called.......")
        /**
         * Set all the data variables and lifecycle owner for two-way data binding
         */
        binding.apply {
            vm = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            flavorFragment = this@FlavorFragment
        }

        /**
         * Called to update the price value because every time a new order is created
         * By default, vanilla with qty=1 will be selected and we need to update the price text for the UI
         */
        sharedViewModel.calculateTotalOrderQtyAndTotalPrice()

        /**
         * Made specifically to handle back stack case i.e from PickUpFragment to FlavorFragment
         * Enable or disable the views based on the selected cupcake flavours inside cupcakeFlavoursList
         */
        sharedViewModel.cupcakeFlavoursList.observe(viewLifecycleOwner, { newData ->
            if (newData != null) {
                if (newData.contains(getString(R.string.vanilla))) {
                    disableEnableVanillaOptionCounter(true)
                } else {
                    disableEnableVanillaOptionCounter(false)
                }
                if (newData.contains(getString(R.string.chocolate))) {
                    disableEnableChocolateOptionCounter(true)
                } else {
                    disableEnableChocolateOptionCounter(false)
                }
                if (newData.contains(getString(R.string.red_velvet))) {
                    disableEnableRedVelvetOptionCounter(true)
                } else {
                    disableEnableRedVelvetOptionCounter(false)
                }
            }
        })


    }

    /**
     * For safe navigation, check if cupcakeFlavoursList is not Empty.
     * If if it empty then ask user to select atleast 1 cupcake.
     * Otherwise, Navigate safely to the next screen to choose pickup date.
     */
    fun goToNextScreen() {
//      Toast.makeText(activity, "Next", Toast.LENGTH_SHORT).show()
        if (sharedViewModel.hasNoFlavourSet()){
            Toast.makeText(activity, "Please select a cupcake!", Toast.LENGTH_SHORT).show()
        } else {
        val action = FlavorFragmentDirections.actionFlavorFragmentToPickupFragment()
        findNavController().navigate(action)
    }}

    /**
     * Before navigating to start order fragment, reset all the data saved in the viewModel.
     */
    fun cancelOrder(){
        val action = FlavorFragmentDirections.actionFlavorFragmentToStartFragment2()
        findNavController().navigate(action)
    }

    /**
     * If a flavour checkbox is checked, then enable the counter for it and set its default qty as 1.
     * If a flavour checkbox is unchecked, then disable the counter for it and roll back it's qty value to 0.
     */
    fun handleCheckBoxCases(selectedFlavour: String){
        when(selectedFlavour){
            getString(R.string.vanilla) -> {
                if (binding.vanilla.isChecked ) {
                    sharedViewModel.setCupcakeFlavour(selectedFlavour, Flavour.ADD)
                    sharedViewModel.setVanillaQty(1)
                    disableEnableVanillaOptionCounter(true)
                } else {
                    sharedViewModel.setCupcakeFlavour(selectedFlavour, Flavour.REMOVE)
                    sharedViewModel.setVanillaQty(0)
                    disableEnableVanillaOptionCounter(false)
                }
            }

            getString(R.string.chocolate) -> {
                if (binding.chocolate.isChecked){
                    sharedViewModel.setCupcakeFlavour(selectedFlavour, Flavour.ADD)
                    sharedViewModel.setChocolateQty(1)
                   disableEnableChocolateOptionCounter(true)
                } else {
                    sharedViewModel.setCupcakeFlavour(selectedFlavour, Flavour.REMOVE)
                    sharedViewModel.setChocolateQty(0)
                  disableEnableChocolateOptionCounter(false)
                }
            }

            getString(R.string.red_velvet) -> {
                if (binding.redVelvet.isChecked){
                    sharedViewModel.setCupcakeFlavour(selectedFlavour, Flavour.ADD)
                    sharedViewModel.setRedVelvetQty(1)
                 disableEnableRedVelvetOptionCounter(true)
                } else {
                  disableEnableRedVelvetOptionCounter(false)
                    sharedViewModel.setRedVelvetQty(0)
                    sharedViewModel.setCupcakeFlavour(selectedFlavour, Flavour.REMOVE)
                }
            }

        }

    }

    /**
     * Disable the counter for vanilla option from 1st view (remove button) to last view (add button)
     * Don't disable the 0th view which is the checkbox
     */
    private fun disableEnableVanillaOptionCounter(value: Boolean){
        for (i in 1 until binding.vanillaOption.childCount) {
            val view: View = binding.vanillaOption.getChildAt(i)
            view.isEnabled = value
        }
    }

    /**
     * Disable the counter for chocolate option from 1st view (remove button) to last view (add button)
     * Don't disable the 0th view which is the checkbox
     */
    private fun disableEnableChocolateOptionCounter(value: Boolean){
        for (i in 1 until binding.chocolateOption.childCount) {
            val view: View = binding.chocolateOption.getChildAt(i)
            view.isEnabled = value
        }
    }

    /**
     * Disable the counter for red velvet option from 1st view (remove button) to last view (add button)
     * Don't disable the 0th view which is the checkbox
     */
    private fun disableEnableRedVelvetOptionCounter(value: Boolean){
        for (i in 1 until binding.redVelvetOption.childCount) {
            val view: View = binding.redVelvetOption.getChildAt(i)
            view.isEnabled = value
        }
    }

    /**
     * This fragment lifecycle method is called when the view hierarchy associated with the fragment
     * is being removed. As a result, clear out the binding object.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}