package com.github.funnygopher.imhungry;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.funnygopher.imhungry.views.Slider;

public class FindFoodFragment extends Fragment {

    private Slider mPriceSlider;
    private TextView mPriceText;

    private Slider mDistanceSlider;
    private TextView mDistanceText;

    private Button mButton;

    private CardView mPlaceDetail;
    private boolean invisible; // Temporary toggle value for running the cardview animation

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_food, container, false);

        // The price slider and textview
        mPriceSlider = (Slider) view.findViewById(R.id.find_food_price_slider);
        mPriceSlider.setOnSliderChangeListener(new Slider.OnSliderChangeListener() {
            @Override
            public void onSliderChange(Slider slider, int index, int prevIndex) {
                mPriceText.setText(Price.getName(index));
            }
        });
        mPriceText = (TextView) view.findViewById(R.id.find_food_price_textview);
        mPriceText.setText(Price.getName(mPriceSlider.getIndex()));

        // The distance slider and textview
        mDistanceSlider = (Slider) view.findViewById(R.id.find_food_distance_slider);
        mDistanceSlider.setOnSliderChangeListener(new Slider.OnSliderChangeListener() {
            @Override
            public void onSliderChange(Slider slider, int index, int prevIndex) {
                mDistanceText.setText(Distance.values()[index].getReadableString());
            }
        });
        mDistanceText = (TextView) view.findViewById(R.id.find_food_distance_textview);
        mDistanceText.setText(Distance.values()[mDistanceSlider.getIndex()].getReadableString());

        // The place detail card
        mPlaceDetail = (CardView) view.findViewById(R.id.find_food_place_detail);
        invisible = mPlaceDetail.getVisibility() == View.INVISIBLE;

        // The find food button
        mButton = (Button) view.findViewById(R.id.find_food_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(invisible)
                    mPlaceDetail.setVisibility(View.VISIBLE);
                else
                    mPlaceDetail.setVisibility(View.INVISIBLE);

                invisible = mPlaceDetail.getVisibility() == View.INVISIBLE;
            }
        });

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.find_food_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
