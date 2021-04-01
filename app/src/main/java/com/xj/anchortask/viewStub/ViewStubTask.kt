import android.view.View
import android.view.ViewStub
import androidx.annotation.CallSuper
import androidx.annotation.IdRes


abstract class ViewStubTask(val decorView: View) {

    companion object {
        const val TAG = "ViewStubTask"
    }

    // ViewStub 转化的 View
    var root: View? = null
        private set

    // 数据是否准备好
    var dataIsReady: Boolean = false

    // ViewStub 是否转化完成
    var onInflateListener: ViewStub.OnInflateListener? = null

    // 当前的 viewStub
    var viewStub: ViewStub? = null
        private set

    // 当前 ViewStub 是否转化完成
    val isInflated: Boolean get() = this.root != null

    abstract fun getViewStubId(): Int


    private fun setUpViewStub(viewStub: ViewStub) {
        this.viewStub = viewStub
        val proxyListener: ViewStub.OnInflateListener = ViewStub.OnInflateListener { stub, inflated ->
                this@ViewStubTask.root = inflated
                this@ViewStubTask.viewStub = null
                onInflateListener?.onInflate(stub, inflated)
                onInflateFinish()
            }
        viewStub.setOnInflateListener(proxyListener)
    }

    /**
     * 调用这个方法，会开始转化 ViewStub
     */
    fun inflate(): View? {
        if (this.root != null) {
            return this.root!!
        }
        val viewStub: ViewStub? = decorView.findViewById(getViewStubId())
        viewStub?.let {
            setUpViewStub(viewStub)
            return viewStub.inflate()
        } ?: kotlin.run {
            return null
        }
    }

    /**
     * ViewStub 转化完成回调
     */
    open fun onInflateFinish() {

    }

    @CallSuper
    open fun onDetach() {

    }


    @CallSuper
    open fun onDataReady() {
        dataIsReady = true
    }

    // 使用 ViewStub ，建议使用 findViewById，如果直接调用 Activity 或者 根布局的 findViewById，当我们的 view 还没有 add 进入的时候，这时候会为 null
    fun <T : View?> findViewById(@IdRes id: Int): T? {
        if (!isInflated) {
            return null
        }
        return if (id == View.NO_ID) {
            null
        } else {
            root?.findViewById<T>(id)
        }

    }


}