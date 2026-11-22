package com.droiddeck.launcher.session

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.core.util.Consumer
import androidx.window.java.layout.WindowInfoTrackerCallbackAdapter
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo

/**
 * FoldDeck: follows a foldable's hinge so a half-folded phone (Flex mode, hinge across the window)
 * shows the game above the hinge and the controls below it.
 */
class FlexPosture(private val activity: Activity, private val onChange: () -> Unit) {
    /** Top of the hinge in window pixels while half-folded with a horizontal hinge, otherwise null. */
    @Volatile
    var hingeTop: Int? = null
        private set

    private val tracker = WindowInfoTrackerCallbackAdapter(WindowInfoTracker.getOrCreate(activity))
    private var listening = false

    private val listener = Consumer<WindowLayoutInfo> { info ->
        val fold = info.displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
        val top = fold?.takeIf {
            it.state == FoldingFeature.State.HALF_OPENED && it.orientation == FoldingFeature.Orientation.HORIZONTAL
        }?.bounds?.top
        if (top != hingeTop) {
            hingeTop = top
            Log.i(TAG, if (top != null) "flex mode: hinge at y=$top" else "flex mode: off")
            onChange()
        }
    }

    fun start() {
        if (listening) return
        listening = true
        tracker.addWindowLayoutInfoListener(activity, activity.mainExecutor, listener)
    }

    fun stop() {
        if (!listening) return
        listening = false
        tracker.removeWindowLayoutInfoListener(listener)
        if (hingeTop != null) {
            hingeTop = null
            onChange()
        }
    }

    companion object {
        private const val TAG = "FlexPosture"
        private const val PREFS = "folddeck"
        private const val KEY_ENABLED = "flexMode"

        fun enabled(context: Context): Boolean =
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(KEY_ENABLED, true)

        fun setEnabled(context: Context, on: Boolean) {
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putBoolean(KEY_ENABLED, on).apply()
        }

        /** Game height that ends at the hinge, keeping the tab row above it like the unfolded layout does. */
        fun gameHeight(hingeTop: Int, rootTop: Int, height: Int, tabRow: Int): Int =
            (hingeTop - rootTop - tabRow.coerceAtLeast(0)).coerceIn(0, height) and 1.inv()
    }
}
