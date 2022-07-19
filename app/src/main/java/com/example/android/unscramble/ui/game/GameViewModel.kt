package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG_FRAGMENT:String = "GameFragment"
class GameViewModel : ViewModel() {
    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        Log.d(TAG_FRAGMENT, "GameViewModel created !")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG_FRAGMENT, "GameViewModel destroyed!")
    }

    /*
    * Updates currentWord and currentScrambledWord with the next word.
    */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()

        do tempWord.shuffle()
        while (String(tempWord).equals(currentWord, false))

        if (wordsList.contains(currentWord))
            getNextWord()
        else {
            _currentScrambledWord = String(tempWord)
            _currentWordCount++
            wordsList.add(currentWord)
        }
    }

    /*
    * Returns true if the current word count is less than MAX_NO_OF_WORDS.
    * Updates the next word.
    */
    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun isUserWordCorrect(playerWord: String) : Boolean {
        var isCorrect = false
        if(playerWord.equals(currentWord, true)){
            increaseScore()
            isCorrect = true
        }
        return isCorrect
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    /*
    * Re-initializes the game data to restart the game.
    */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }
}