package com.pablisco.android.estimate.view.util;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

public class ViewAnimationUtilsCompat {

    private static final ViewAnimationUtilCompatBase DELEGATE;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DELEGATE = new ViewAnimationUtilCompatLollipop();
        } else {
            DELEGATE = new ViewAnimationUtilCompatBase();
        }
    }

    @SuppressWarnings("SameParameterValue")
    public static Animator createCircularReveal(View view, int centerX, int centerY, float startRadius, float endRadius) {
        return DELEGATE.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
    }

    static class ViewAnimationUtilCompatBase {

        public Animator createCircularReveal(View view, int centerX, int centerY, float startRadius, float endRadius) {
            return AnimatorInflater.loadAnimator(view.getContext(),
                    endRadius > startRadius ? android.R.animator.fade_in : android.R.animator.fade_out);
        }

    }

    private static class ViewAnimationUtilCompatLollipop extends ViewAnimationUtilCompatBase {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public Animator createCircularReveal(View view, int centerX, int centerY, float startRadius, float endRadius) {
            return ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        }

    }

}
