package com.xj.anchortask.asyncInflate.page

import android.content.Context
import com.xj.anchortask.R
import com.xj.anchortask.asyncInflate.AsyncInflateItem
import com.xj.anchortask.asyncInflate.AsyncInflateManager
import com.xj.anchortask.asyncInflate.LaunchInflateKey.LAUNCH_FRAGMENT_MAIN
import com.xj.anchortask.getSP

/**
 * Created by jun xu on 4/1/21.
 */
object AsyncUtils {

    fun asyncInflate(context: Context?) {
        context ?: return
        val asyncInflateItem =
            AsyncInflateItem(
                LAUNCH_FRAGMENT_MAIN,
                R.layout.fragment_asny,
                null,
                null
            )
        AsyncInflateManager.instance.asyncInflate(context, asyncInflateItem)
    }

    fun isHomeFragmentOpen() =
        getSP("async_config").getBoolean("home_fragment_switch", true)
}