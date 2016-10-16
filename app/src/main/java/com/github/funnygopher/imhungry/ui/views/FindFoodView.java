package com.github.funnygopher.imhungry.ui.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.funnygopher.imhungry.R;
import com.github.funnygopher.imhungry.injection.DaggerService;
import com.github.funnygopher.imhungry.model.Distance;
import com.github.funnygopher.imhungry.model.Place;
import com.github.funnygopher.imhungry.model.Price;
import com.github.funnygopher.imhungry.ui.PlaceCardViewHolder;
import com.github.funnygopher.imhungry.ui.screens.FindFoodScreen;
import com.github.funnygopher.imhungry.ui.widgets.Slider;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import mortar.MortarScope;
import timber.log.Timber;

public class FindFoodView extends FrameLayout {

    @BindView(R.id.find_food_price_slider)
    Slider mPriceSlider;

    @BindView(R.id.find_food_price_textview)
    TextView mPriceText;

    @BindView(R.id.find_food_distance_slider)
    Slider mDistanceSlider;

    @BindView(R.id.find_food_distance_textview)
    TextView mDistanceText;

    @BindView(R.id.find_food_place_detail)
    CardView mPlaceDetail;

    Place currentPlace;

    @Inject
    FindFoodScreen.Presenter presenter;

    @SuppressWarnings("ConstantConditions")
    public FindFoodView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Timber.tag(getClass().getSimpleName());
        MortarScope scope = Flow.getService(Flow.getKey(this).getClass().getName(), context);
        scope.<FindFoodScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.dropView(this);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        mPriceSlider.setOnSliderChangeListener(new Slider.OnSliderChangeListener() {
            @Override
            public void onSliderChange(Slider slider, int index, int prevIndex) {
                mPriceText.setText(Price.getName(index));
            }
        });

        mDistanceSlider.setOnSliderChangeListener(new Slider.OnSliderChangeListener() {
            @Override
            public void onSliderChange(Slider slider, int index, int prevIndex) {
                mDistanceText.setText(Distance.values()[index].getReadableString());
            }
        });

        mPriceText.setText(Price.getName(mPriceSlider.getIndex()));
        mDistanceText.setText(Distance.values()[mDistanceSlider.getIndex()].getReadableString());
    }

    @OnClick(R.id.find_food_button)
    void onFindFoodButtonClick() {
        Place place = presenter.getRandomPlace(Price.getValue(mPriceSlider.getIndex()));
        // Couldn't find a random place...
        if (place == null) {
            Toast.makeText(getContext(), "There is no " + Price.getName(mPriceSlider.getIndex()).toLowerCase() + " place!", Toast.LENGTH_SHORT).show();
            return;
        } else if (currentPlace != null && place.getId() == currentPlace.getId()) {
            Toast.makeText(getContext(), "This is the only " + Price.getName(mPriceSlider.getIndex()).toLowerCase() + " place!", Toast.LENGTH_SHORT).show();
            return;
        }

        currentPlace = place;

        PlaceCardViewHolder holder = new PlaceCardViewHolder(mPlaceDetail);
        holder.bind(currentPlace);
    }
}
