package com.pablisco.android.estimate;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class EstimationCardView extends FrameLayout {

    private View frontView;
    private View backView;
    private TextView frontTextView;
    private Side selectedSide = Side.FRONT;

    public EstimationCardView(Context context) {
        this(context, null);
    }

    public EstimationCardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EstimationCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EstimationCardView(Context context, AttributeSet attrs, int defStyleAttr,
                              int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        setClipChildren(false);

        LayoutInflater.from(getContext()).inflate(R.layout.estimation_card_view, this, true);
        frontView = findViewById(R.id.front_view);
        backView = findViewById(R.id.back_view);
        frontTextView = (TextView) findViewById(R.id.front_card_content);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setText(@StringRes int resid) {
        frontTextView.setText(resid);
    }

    public void setText(CharSequence text) {
        frontTextView.setText(text);
    }

    public void setSelectedSide(Side newSide) {
        if (selectedSide != newSide) {
            if (newSide == Side.FRONT) {
                final AnimatorSet setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_right_in);
                final AnimatorSet setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_left_out);

                setRightOut.setTarget(frontView);
                setLeftIn.setTarget(backView);
                setRightOut.start();
                setLeftIn.start();

            } else if (newSide == Side.BACK) {
                final AnimatorSet setRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_right_out);
                final AnimatorSet setLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_left_in);

                setRightOut.setTarget(frontView);
                setLeftIn.setTarget(backView);
                setRightOut.start();
                setLeftIn.start();
            }
            this.selectedSide = newSide;
        }
    }

    public Side getSelectedSide() {
        return selectedSide;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), (int) (getMeasuredWidth() * 1.4));
    }

    public enum Side {
        FRONT,
        BACK
    }

}
