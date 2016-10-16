package com.github.funnygopher.imhungry.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.github.funnygopher.imhungry.R;
import com.github.funnygopher.imhungry.model.Place;
import com.github.funnygopher.imhungry.model.Price;
import com.github.funnygopher.imhungry.model.database.RealmService;
import com.github.funnygopher.imhungry.ui.widgets.Slider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewPlaceActivity extends AppCompatActivity {

    RealmService realmService;

    @BindView(R.id.new_place_toolbar)
    Toolbar mToolbar;
    
    @BindView(R.id.new_place_name)
    AppCompatEditText mNameEditText;

    @BindView(R.id.new_place_description)
    AppCompatEditText mDescEditText;

    @BindView(R.id.new_place_price_slider)
    Slider mPriceSlider;

    @BindView(R.id.new_place_price_textview)
    TextView mPriceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);
        ButterKnife.bind(this);

        realmService = new RealmService();

        mToolbar.setTitle(getString(R.string.new_place));
        setSupportActionBar(mToolbar);

        mDescEditText.setMaxLines(Integer.MAX_VALUE);

        mPriceSlider.setOnSliderChangeListener(new Slider.OnSliderChangeListener() {
            @Override
            public void onSliderChange(Slider slider, int index, int prevIndex) {
                mPriceText.setText(Price.getName(index));
            }
        });

        mPriceText.setText(Price.getName(mPriceSlider.getIndex()));
    }

    @OnClick(R.id.new_place_button_save)
    void onSaveButtonClick() {
        createPlace();
    }

    void createPlace() {
        String name = mNameEditText.getText().toString();
        String desc = mDescEditText.getText().toString();
        int price = Price.getValue(mPriceSlider.getIndex());
        Place place = new Place(name, desc, price, "", false);

        realmService.addPlace(place);

        Intent intent = new Intent();
        intent.putExtra("updateList", true);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}