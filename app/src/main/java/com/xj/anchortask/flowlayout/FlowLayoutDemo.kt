package com.xj.anchortask.flowlayout

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xj.anchortask.R
import java.util.*


class FlowLayoutDemo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_layout_demo)

        val tagGroup = findViewById<TagGroup>(R.id.tag_group)
        val texts: List<String> = Arrays.asList(
            "zhang",
            "phil",
            "csdn",
            "android",
            "zhang",
            "phil",
            "csdn",
            "android",
            "zhang",
            "phil",
            "csdn",
            "android"
        )
        val colors: List<Int> = Arrays.asList(
            Color.RED,
            Color.DKGRAY,
            Color.BLUE,
            Color.RED,
            Color.DKGRAY,
            Color.BLUE,
            Color.RED,
            Color.DKGRAY,
            Color.BLUE,
            Color.RED,
            Color.DKGRAY,
            Color.BLUE
        )
        tagGroup.setTagView(texts, colors)
    }
}