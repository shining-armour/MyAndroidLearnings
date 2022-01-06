package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentPickupBinding

/**
 * [PickupFragment] allows the user to choose a pickup date for the cupcake order.
 */
class PickupFragment : Fragment() {
    /**
     * Binding object instance corresponding to the fragment_start.xml layout
     * This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks, when the view hierarchy is attached to the fragment.
     */
    private var _binding: FragmentPickupBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel:OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPickupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
         * Set all the data variables and lifecycle owner for two-way data binding
         */
        binding.apply {
            vm = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            pickUpFragment = this@PickupFragment
        }

    }

    /**
     * Before navigation to summary fragment, create the summary flavour string to be displayed.
     * Navigate to the next screen to see the order summary.
     */
    fun goToNextScreen() {
//      Toast.makeText(activity, "Next", Toast.LENGTH_SHORT).show()
        sharedViewModel.setFlavourSummary()
        val action = PickupFragmentDirections.actionPickupFragmentToSummaryFragment()
        findNavController().navigate(action)
    }

    /**
     * Before navigating to start order fragment, reset all the data saved in the viewModel.
     */
    fun cancelOrder(){
        sharedViewModel.resetOrder()
        val action = PickupFragmentDirections.actionPickupFragmentToStartFragment()
        findNavController().navigate(action)
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