package com.example.wordsapp

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentLetterListBinding



class LetterListFragment : Fragment() {

    // A property must have underscore if it shouldn't be accessed directly outside of LetterListFragment - similar to private
    private var _binding: FragmentLetterListBinding? = null
    // By using double bang, we are assuring that the property will be assigned before accessing it.
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private var isLinearLayoutManager = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout in onCreateView
        _binding = FragmentLetterListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Bind the views in onViewCreated
        recyclerView = binding.recyclerView
        recyclerView.adapter = LetterAdapter()
        chooseLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * While the Activity class has a global property called menuInflater, Fragment does not have this property.
     * The menu inflater is instead passed into onCreateOptionsMenu().
     * Also note that the onCreateOptionsMenu() method used with fragments doesn't require a return statement.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_switch_layout -> {
                isLinearLayoutManager =! isLinearLayoutManager
                chooseLayout()
                setIcon(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun chooseLayout(){
        if (isLinearLayoutManager){
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        } else {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
        }
    }


    private fun setIcon(menuItem: MenuItem?){
        if (menuItem != null) {
            menuItem.icon = if (isLinearLayoutManager) {
                // requireContext() returns the Context this fragment is currently associated with. [Note: requireContext(), this.requireContext(), context!! are all same]
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_view_module)
            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_format_list)
            }
        }
    }

}