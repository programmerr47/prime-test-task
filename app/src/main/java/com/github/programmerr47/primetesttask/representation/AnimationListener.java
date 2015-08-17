package com.github.programmerr47.primetesttask.representation;

import android.animation.Animator;
import android.view.View;

/**
 * @author Michael Spitsin
 * @since 2015-08-17
 */
public interface AnimationListener {
    void onAnimationEnd(View animatedView, Animator animator);
}
