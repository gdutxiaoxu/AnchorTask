package com.xj.anchortask.flowlayout;

/**
 * Created by jun xu on 4/19/21.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

//https://blog.csdn.net/qq_36699930/article/details/112249170
public class TagGroup extends FlexboxLayout {
    private List<String> mTexts;
    private List<Integer> mColors;
    private Context mContext;
    private int TAG_VIEW_COUNT = 0;

    public TagGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mTexts = new ArrayList<>();
        mColors = new ArrayList<>();

        this.setFlexDirection(FlexDirection.ROW);
        this.setJustifyContent(JustifyContent.FLEX_START);
        this.setFlexWrap(FlexWrap.WRAP);
    }

    public void setTagView(@NonNull List<String> texts, @Nullable List<Integer> colors) {
        if (texts == null || texts.size() == 0) {
            throw new RuntimeException("tag view文本字段不能为空");
        }

        this.mTexts = texts;
        TAG_VIEW_COUNT = texts.size();

        if (colors == null || colors.size() == 0) {
            for (int i = 0; i < TAG_VIEW_COUNT; i++) {
                mColors.clear();
                mColors.add(Color.WHITE);
            }
        } else {
            this.mColors = colors;
        }

        this.removeAllViews();

        for (int i = 0; i < TAG_VIEW_COUNT; i++) {
            TextView textView = makeTextView(mTexts.get(i), mColors.get(i));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置每一个子View在整体布局中与其他子View的上下左右的margin
            layoutParams.setMargins(0, 1, 5, 1);

            this.addView(textView, layoutParams);
        }

        this.invalidate();
    }

    //绘制圆角描边的TextView
    private TextView makeTextView(String s, Integer c) {
        TextView textView = new TextView(mContext);
        textView.setText(s);
        textView.setPadding(10, 5, 10, 5);

        int strokeWidth = 5; // 5px
        int roundRadius = 15; // 15px
        int strokeColor = Color.GRAY;
        int fillColor = c;

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);

        textView.setBackground(gd);

        return textView;
    }
}
