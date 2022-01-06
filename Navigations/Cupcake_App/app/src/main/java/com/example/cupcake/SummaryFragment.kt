package com.example.cupcake

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentSummaryBinding

/**
 * [SummaryFragment] contains a summary of the order details with a button to share the order
 * via another app.
 */
class SummaryFragment : Fragment() {
    /**
     * Binding object instance corresponding to the fragment_start.xml layout
     * This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks, when the view hierarchy is attached to the fragment.
     */
    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel:OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
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
            summaryFragment = this@SummaryFragment
        }
    }

    /**
     * Submit the order by sharing out the order details to another app via an implicit intent.
     */
    fun sendOrder() {
//      Toast.makeText(activity, "Send Order", Toast.LENGTH_SHORT).show()
        Log.i("Test plurals for 1 CC", resources.getQuantityString(R.plurals.cupcake_qtt, 1, 1))
        Log.i("Test plurals for CC>1 ", resources.getQuantityString(R.plurals.cupcake_qtt, 12, 12))

        val numberOfCupcakes = sharedViewModel.orderQuantity.value ?: 0

        val finalOrder = getString(
            R.string.order_details,
            resources.getQuantityString(R.plurals.cupcake_qtt, numberOfCupcakes, numberOfCupcakes),
            sharedViewModel.summaryFlavourString,
            sharedViewModel.pickUpDate.value,
            sharedViewModel.price.value)

        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.new_cupcake_order))
            .putExtra(Intent.EXTRA_TEXT, finalOrder)

        // check if suitable apps are there in users device to handle this intent
        if (activity?.packageManager?.resolveActivity(intent, 0) != null){
            startActivity(intent)
            }
    }


    /**
     * Before navigating to start order fragment, reset all the data saved in the viewModel.
     */
    fun cancelOrder(){
        sharedViewModel.resetOrder()
        val action = SummaryFragmentDirections.actionSummaryFragmentToStartFragment()
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