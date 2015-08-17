package com.github.programmerr47.primetesttask.util;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.support.annotation.NonNull;
import android.view.View;

import com.github.programmerr47.primetesttask.PApplication;
import com.github.programmerr47.primetesttask.R;
import com.github.programmerr47.primetesttask.representation.AnimationListener;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public class AnimationUtils {

    //TODO rewrite it without using resource animation
    public static Animator hideView(@NonNull final View view, final AnimationListener listener, int wishDuration) {
        Animator hidingViewAnim = AnimatorInflater.loadAnimator(PApplication.getAppContext(), R.animator.fade_out);

        hidingViewAnim.setTarget(view);
        hidingViewAnim.setDuration(wishDuration);

        AnimatorSet animation = new AnimatorSet();
        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);

                if (listener != null) {
                    listener.onAnimationEnd(view, animation);
                }
            }
        });

        animation.play(hidingViewAnim);
        animation.start();
        return animation;
    }

    public static Animator hideView(@NonNull final View view, final AnimationListener listener) {
        return hideView(view, listener, 300);
    }
}
