package com.example.cupcake

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.cupcake.databinding.FragmentStartBinding

/**
 * This is the first screen of the Cupcake app. The user can choose how many cupcakes to order.
 */
class StartFragment : Fragment() {

    /**
     * Binding object instance corresponding to the fragment_start.xml layout
     * This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks, when the view hierarchy is attached to the fragment.
     */
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: OrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startFragment = this@StartFragment
    }

    /**
     * Since, we will always start a new order from this fragment. Reset all the viewModel values before navigating to next fragment.
     */
    fun goToNextScreen(){
        sharedViewModel.resetOrder()
        val action = StartFragmentDirections.actionStartFragmentToFlavorFragment()
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