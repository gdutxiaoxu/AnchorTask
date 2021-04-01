package com.xj.anchortask.asyncInflate

import android.view.View
import android.view.ViewGroup

/**
 * Created by jun xu on 4/1/21.
 */
class AsyncInflateItem constructor(var inflateKey: String, var layoutResId: Int, var parent: ViewGroup? = null, var callback: OnInflateFinishedCallback? = null) {
    var inflatedView: View? = null
    private var cancelled = false
    private var inflating = false

    fun isCancelled(): Boolean {
        synchronized(this) { return cancelled }
    }

    fun setCancelled(cancelled: Boolean) {
        synchronized(this) { this.cancelled = cancelled }
    }

    fun isInflating(): Boolean {
        synchronized(this) { return inflating }
    }

    fun setInflating(inflating: Boolean) {
        synchronized(this) { this.inflating = inflating }
    }

    override fun toString(): String {
        return "AsyncInflateItem(inflateKey='$inflateKey', layoutResId=$layoutResId, parent=$parent, callback=$callback, inflatedView=$inflatedView, cancelled=$cancelled, inflating=$inflating)"
    }


    interface OnInflateFinishedCallback {
        fun onInflateFinished(item: AsyncInflateItem)
    }
}