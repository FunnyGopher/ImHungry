package com.github.funnygopher.imhungry.ui;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.github.funnygopher.imhungry.R;
import com.github.funnygopher.imhungry.model.Place;
import com.github.funnygopher.imhungry.model.Price;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceCardViewHolder {

    private final CardView cardView;

    @BindView(R.id.place_detail_card_title)
    TextView title;

    @BindView(R.id.place_detail_card_price_distance)
    TextView priceDistance;

    @BindView(R.id.place_detail_card_description)
    TextView description;

    public PlaceCardViewHolder(CardView cardView) {
        this.cardView = cardView;
        ButterKnife.bind(this, cardView);
    }

    public void bind(Place place) {
        title.setText(place.getName());
        priceDistance.setText(Price.getName(place.getPrice()) +
                " - " +
                place.getFormattedDistance());
        description.setText(place.getDescription());
        cardView.setVisibility(View.VISIBLE);
    }
}
