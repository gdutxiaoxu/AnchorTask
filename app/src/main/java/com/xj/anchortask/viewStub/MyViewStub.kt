package com.xj.anchortask.viewStub

import ViewStubTask
import android.util.Log
import android.view.View
import com.xj.anchortask.R

/**
 * Created by jun xu on 4/1/21.
 */
class ViewStubTaskTitle(decorView: View) : ViewStubTask(decorView) {

    override fun getViewStubId(): Int {
        return R.id.vs_title
    }

    override fun onInflateFinish() {
        super.onInflateFinish()
        Log.i(TAG, "onInflateFinish: ViewStubTaskTitle")
    }


}

class ViewStubTaskContent(decorView: View) : ViewStubTask(decorView) {

    override fun getViewStubId(): Int {
        return R.id.vs_content
    }

    override fun onInflateFinish() {
        super.onInflateFinish()
        Log.i(TAG, "onInflateFinish: ViewStubTaskContent")
    }


}

class ViewStubTaskBottom(decorView: View) : ViewStubTask(decorView) {

    override fun getViewStubId(): Int {
        return R.id.vs_bottom
    }

    override fun onInflateFinish() {
        super.onInflateFinish()
        Log.i(TAG, "onInflateFinish: ViewStubTaskBottom")
    }


}