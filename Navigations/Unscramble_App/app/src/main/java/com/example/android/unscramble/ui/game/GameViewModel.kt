package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    // Declare private mutable variable that can only be modified within the viewModel class.
    private var _score = MutableLiveData(0)
    // Declare another public immutable field and override its getter method.
    // Return the private property's value in the getter method.
    // When score is accessed, the get() function is called and the value of _score is returned.
    val score: LiveData<Int>
        get() = _score

    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private val _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    // This list will contain the 10 words generated during a single game session.
    private var _wordsList: MutableList<String> = mutableListOf()
    // Stores the current original word generated and sets it's shuffled version to _currentScrambled word,
    // only if it was not asked during a game session.
    private lateinit var _currentWord:String

    init {
        Log.d("GameFragment", "GameViewModel created!")
        // show the first scrambled word on the screen
        getNextWord()
    }


    /**
     * Gets a random word for the list of words and shuffles the letters in it.
     */
    private fun getNextWord() {

        _currentWord = allWordsList.random()

        // Temp word will be the shuffled version of _currentWord
        // TempWord is converted to charArray to use shuffle method later
        val tempWord = _currentWord.toCharArray()

        // Shuffle the tempWord until it becomes unequal to _currentWord
        while (String(tempWord) == _currentWord) {
            tempWord.shuffle()
        }

        Log.d("Current word", _currentWord)
        Log.d("Temp word/Current scrambled word", String(tempWord))

        // If the word is already asked during a game,
        // then recursively call getNextWord() to generate other random word from allWordsList
        // Otherwise, if the word is generated for the first time
        // Then, set current Scrambled word on screen with the shuffled version of the generated word
        // Increase current word count by 1
        // And, add the original word to the list of words generated during a game.
        if (_wordsList.contains(_currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            _wordsList.add(_currentWord)

            Log.d("Current word count", _currentWordCount.value.toString())
            Log.d("Word list", _wordsList.toString())
        }
    }


    /**
     * Helper function to check whether or not the next word should be generated.
     * If the current word count is less than MAX_NO_OF_WORDS. Call the getNextWord() and return true.
     * Otherwise, just return false.
    */
    fun shouldGetNextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    /**
     * Helper function to increase score count
     */
    private fun increaseScore() {
    _score.value = _score.value?.plus(SCORE_INCREASE)
    }

    /**
     * Helper function to check if user typed in the correct word.
     */
    fun isUserWordCorrect(userWord: String): Boolean {
        if (userWord == _currentWord){
            increaseScore()
            return true
        }
        return false
    }

    /**
     * Helper function that is called when the game is restarted
     */
    fun reInitializeData(){
        _score.value = 0
        _currentWordCount.value = 0
        _wordsList.clear()
        getNextWord()
    }

    /**
     * ViewModel lifecycle method
     * onCleared() is called after onDestroy() only if it is NOT a configuration change
     */

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }


}