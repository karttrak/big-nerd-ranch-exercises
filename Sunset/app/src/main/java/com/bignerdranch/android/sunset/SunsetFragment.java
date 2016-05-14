package com.bignerdranch.android.sunset;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/13/16.
 */
public class SunsetFragment extends Fragment {

    private static final int ANIMATION_DURATION = 1000 * 5;

    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    private boolean isDay = true;

    public static SunsetFragment newInstance() {
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);

        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });

        return view;
    }

    private void startAnimation() {
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();

        ObjectAnimator heightAnimator = createFloatAnimator(mSunView, "y", sunYStart, sunYEnd);
        ObjectAnimator sunsetSkyAnimator = createIntAnimator(mSkyView, "backgroundColor",
                mBlueSkyColor, mSunsetSkyColor);
        ObjectAnimator nightSkyAnimator = createIntAnimator(ANIMATION_DURATION / 2,
                mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor);

        AnimatorSet animatorSet = new AnimatorSet();
        if (isDay) {
            animatorSet
                    .play(heightAnimator)
                    .with(sunsetSkyAnimator)
                    .before(nightSkyAnimator);
        } else {
            animatorSet
                    .play(reverseFloatAnimator(heightAnimator))
                    .with(reverseIntAnimator(sunsetSkyAnimator))
                    .after(reverseIntAnimator(nightSkyAnimator));
        }
        isDay = !isDay;
        animatorSet.start();
    }

    private ObjectAnimator createFloatAnimator(View v, String p, float... vals) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(v, p, vals)
                .setDuration(ANIMATION_DURATION);
        animator.setInterpolator(new AccelerateInterpolator());
        return animator;
    }

    private ObjectAnimator createIntAnimator(View v, String p, int... vals) {
        return createIntAnimator(ANIMATION_DURATION, v, p, vals);
    }

    private ObjectAnimator createIntAnimator(int d, View v, String p, int... vals) {
        ObjectAnimator animator = ObjectAnimator.ofInt(v, p, vals)
                .setDuration(d);
        animator.setEvaluator(new ArgbEvaluator());
        return animator;
    }

    private ObjectAnimator reverseFloatAnimator(ObjectAnimator a) {
        // TODO: Use the old animator's values
        ObjectAnimator animator = ObjectAnimator.ofFloat(a.getTarget(), a.getPropertyName(),
                mSkyView.getHeight(), mSunView.getTop())
                .setDuration(a.getDuration());
        animator.setInterpolator(a.getInterpolator());
        return animator;
    }

    private ObjectAnimator reverseIntAnimator(ObjectAnimator a) {
        // TODO: Use the old animator's values
        ObjectAnimator animator = ObjectAnimator.ofInt(a.getTarget(), a.getPropertyName(),
                mNightSkyColor, mSunsetSkyColor)
                .setDuration(a.getDuration());
        animator.setEvaluator(new ArgbEvaluator());
        return animator;
    }
}
