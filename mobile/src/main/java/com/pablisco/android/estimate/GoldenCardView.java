package com.pablisco.android.estimate;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

public class GoldenCardView extends AppCompatButton {

    public GoldenCardView(Context context) {
        super(context);
    }

    public GoldenCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GoldenCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredWidth() * 1.4));
    }
}
