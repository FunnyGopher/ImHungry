package com.github.funnygopher.imhungry.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.github.funnygopher.imhungry.R;

public class Slider extends SeekBar {

    private static int POINT_VALUE = 100;

    private OnSliderChangeListener mListener;
    private int mPoints;
    private int index;

    public Slider(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Retrieves attributes
        TypedArray array = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.Slider, 0, 0);
        try {
            mPoints = array.getInteger(R.styleable.Slider_points, 2);

            // The first point should always be at 0, so
            // this allows the seek bar to be equally
            // separated into n - 1 segments
            setMax((mPoints - 1) * POINT_VALUE);
        } finally {
            if(array != null) {
                array.recycle();
            }
        }

        init();
    }

    private void init() {
        index = 0;

        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Finds the closes point, and then snaps the bar to it
                int smallestABSIndex = 0;
                float smallestABS = -1;
                for (int i = 0; i < mPoints; i++) {
                    int abs = Math.abs(i * POINT_VALUE - progress);
                    if (smallestABS == -1) {
                        smallestABS = abs;
                    }

                    if (abs < smallestABS) {
                        smallestABS = abs;
                        smallestABSIndex = i;
                    }
                }
                int prevIndex = index;
                index = smallestABSIndex;

                if(mListener != null) {
                    mListener.onSliderChange(Slider.this, index, prevIndex);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(index * POINT_VALUE);
            }
        });
    }

    public void setPoints(int points) {
        mPoints = points;
        setMax((mPoints - 1) * POINT_VALUE);
    }

    public void setIndex(int index) {
        setProgress(index * POINT_VALUE);
    }

    public int getIndex() {
        return index;
    }

    public void setOnSliderChangeListener(Slider.OnSliderChangeListener listener) {
        mListener = listener;
    }

    public interface OnSliderChangeListener {
        void onSliderChange(Slider slider, int index, int prevIndex);
    }
}
