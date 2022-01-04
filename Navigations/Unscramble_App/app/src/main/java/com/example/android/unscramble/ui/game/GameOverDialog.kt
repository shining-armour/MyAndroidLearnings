package com.example.android.unscramble.ui.game

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.viewModels
import com.example.android.unscramble.R

class GameOverDialog(): DialogFragment() {

    private val viewModel: GameViewModel by activityViewModels()

    /**
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        viewModel.reInitializeData()
//        updateNextWordOnScreen()
    }

    /**
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        isCancelable = false

    val builder = AlertDialog.Builder(requireContext())
        .setTitle(getString(R.string.congratulations))
        .setMessage(getString(R.string.you_scored, viewModel.score.value))
        .setNegativeButton(getString(R.string.exit)) { _, _ ->
            exitGame()
        }
        .setPositiveButton(getString(R.string.play_again)) { _, _ ->
            restartGame()
        }

    val dialog = builder.create()
//    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(false)
    return dialog;
}

    companion object {
        const val TAG = "GameOverDialog"
    }

}
