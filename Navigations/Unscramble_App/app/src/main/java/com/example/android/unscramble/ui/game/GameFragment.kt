package com.example.android.unscramble.ui.game
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.viewModels

import com.example.android.unscramble.R
import com.example.android.unscramble.databinding.GameFragmentBinding


/**
 * Fragment where the game is played, contains the game logic.
 */
open class GameFragment : Fragment() {

    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding

    // Create a ViewModel instance the first time this fragment is created.
    // If the fragment is re-created, it receives the same GameViewModel instance created by the first fragment
    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        binding = GameFragmentBinding.inflate(inflater)
        Log.d("GameFragment", "GameFragment created/re-created!")
        Log.d("GameFragment", "Word: ${viewModel.currentScrambledWord.value} " +
                "Score: ${viewModel.score.value} WordCount: ${viewModel.currentWordCount.value}")
//        binding.score.text = getString(R.string.score, 0 )
//        binding.wordCount.text = getString(R.string.word_count, 0, MAX_NO_OF_WORDS)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.gameViewModel = viewModel
//        binding.maxNoOfWords = MAX_NO_OF_WORDS
//        // Instead of observing individual values, use lifecycleOwner
//        // Specify the fragment view as the lifecycle owner of the binding.
//        // This is used so that the binding can observe LiveData updates
//        binding.lifecycleOwner = viewLifecycleOwner

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        Log.d("GameFragment", " on View Created ")

        // Update the UI
//        updateNextWordOnScreen()/

        // Observe the currentScrambledWord LiveData.
        viewModel.currentScrambledWord.observe(viewLifecycleOwner, {
            newWord -> binding.textViewUnscrambledWord.text = newWord
        })

        viewModel.score.observe(viewLifecycleOwner, {
            newScore -> binding.score.text = getString(R.string.score, newScore)
        })

        viewModel.currentWordCount.observe(viewLifecycleOwner, {
            nextWordCount -> binding.wordCount.text = getString(R.string.word_count, nextWordCount, MAX_NO_OF_WORDS)
        })
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("GameFragment", "GameFragment destroyed!")
    }

    /**
     * Checks the user's word, and updates the score accordingly.
     * Displays the next scrambled word.
     */
    private fun onSubmitWord(){

        val userWord = binding.textInputEditText.text.toString()

        // Clear edit text before jumping to next word
        binding.textInputEditText.text = null

        // If the user word turns out to be correct
        // Then, if the error has persisted because user typed in wrong word earlier, remove that error now.
        // Check if wordCount >= 10, if it is, Don't generate next word. Game's over hence, show final score dialog.
        // Otherwise, the word typed in is wrong hence, show error.
        if (viewModel.isUserWordCorrect(userWord)) {
            setErrorTextField(false)

            if (!viewModel.shouldGetNextWord()){
                showFinalScoreDialog()
            }
        } else {
            setErrorTextField(true)
        }
    }

    /**
     * Skips the current word without changing the score.
     * Increases the word count.
     */
    private fun onSkipWord(){
        // Clear edit text before jumping to next word
        binding.textInputEditText.text = null
        // If wordCount < 10, generate next word
        // Hide error if it had persisted because user typed in wrong word earlier
        // Otherwise, if user has skipped the last word, show final score dialog.
        // Before showing the final dialog, omit error msg if it is visible.
        if (viewModel.shouldGetNextWord()){
            setErrorTextField(false)
        } else {
            setErrorTextField(false)
            showFinalScoreDialog()
        }
    }

    /**
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(error:Boolean){
        if(error){
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textField.error = null
        }
    }

    /*
     * Displays the next scrambled word on screen.
     */
//    private fun updateNextWordOnScreen() {
//        binding.textViewUnscrambledWord.text = viewModel.currentScrambledWord
//        binding.textInputEditText.text = null
//        setErrorTextField(false)
//    }

    // Shows a dialog that can handle configuration changes
    private fun showFinalScoreDialog(){
        GameOverDialog().show(childFragmentManager, GameOverDialog.TAG)
    }


}




