package com.xj.anchortask.appstartup

import android.content.Context
import android.util.Log
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import androidx.work.WorkManager


/**
 * Created by jun xu on 4/17/21.
 */

const val TAG = "AnchorTaskApplication"

class WorkManagerInitializer : Initializer<WorkManager> {
    override fun create(context: Context): WorkManager {
        Log.i(TAG, "create: WorkManagerInitializer init")
        AppInitializer.getInstance(context)
            .initializeComponent(ExampleLoggerInitializer::class.java)
        val let: Int = context.let {
            123
        }
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // No dependencies on other libraries.

        return emptyList()
    }
}

// Initializes ExampleLogger.
class ExampleLoggerInitializer : Initializer<ExampleLogger> {
    override fun create(context: Context): ExampleLogger {
        Log.i(TAG, "create: ExampleLoggerInitializer init")
        val apply: Context = context.apply { }
        return ExampleLogger(WorkManager.getInstance(context))
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        // Defines a dependency on WorkManagerInitializer so it can be
        // initialized after WorkManager is initialized.
        return listOf(WorkManagerInitializer::class.java)
    }
}

class ExampleLogger(val workManager: WorkManager) {

}

fun <T, R> T.let2(block: (T) -> R): R {
    return block(this)
}

fun <T> T.apply2(block: T.() -> Unit): T {
    block()
    return this
}
