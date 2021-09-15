package com.xj.anchortask

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xj.anchortask.anchorTask.AnchorTaskTestActivity
import com.xj.anchortask.asyncInflate.AsyncActivity
import com.xj.anchortask.asyncInflate.page.AsyncUtils
import com.xj.anchortask.asyncInflate.page.AsyncUtils.isHomeFragmentOpen
import com.xj.anchortask.flowlayout.FlowLayoutDemo
import com.xj.anchortask.viewStub.ViewStubDemoActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_view_stub.setOnClickListener {
            startActivity(Intent(this@MainActivity, ViewStubDemoActivity::class.java))
        }

        btn_anchortask.setOnClickListener {
            startActivity(Intent(this@MainActivity, AnchorTaskTestActivity::class.java))
        }

        btn_flow_layout_demo.setOnClickListener {
            startActivity(Intent(this@MainActivity, FlowLayoutDemo::class.java))
        }

        btn_async.setOnClickListener {
            startActivity(Intent(this@MainActivity, AsyncActivity::class.java))
        }


        val isOpen = AsyncUtils.isHomeFragmentOpen()
        updateText(isOpen)
        switch_async.isChecked = isOpen

        ll_switch.setOnClickListener {
            val b = !switch_async.isChecked
            updateChecked(b)
        }

        switch_async.setOnCheckedChangeListener { buttonView, isChecked ->
            updateChecked(isChecked)
        }
    }

    private fun updateChecked(b: Boolean) {
        updateText(b)
        switch_async.isChecked = b
        getSP("async_config").spApplyBoolean("home_fragment_switch", b)
    }


    private fun updateText(b: Boolean) {
        if (b) {
            tv_asnyc_text.setText("首页 Fragment 开启异步加载")
        } else {
            tv_asnyc_text.setText("首页 Fragment 关闭异步加载")
        }
    }
}