package com.github.funnygopher.imhungry.ui.recyclerview.viewholders;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.github.funnygopher.imhungry.R;
import com.github.funnygopher.imhungry.model.Place;
import com.github.funnygopher.imhungry.model.Price;
import com.github.funnygopher.imhungry.ui.PlaceCardViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;

public class PlaceViewHolder extends RealmSearchViewHolder {

    @BindView(R.id.my_places_list_item_title)
    TextView name;

    @BindView(R.id.my_places_list_item_desc)
    TextView description;

    @BindView(R.id.my_places_list_item_price)
    TextView price;

    public PlaceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final Place place) {
        name.setText(place.getName());
        description.setText(place.getDescription());
        price.setText(Price.getName(place.getPrice()));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View item) {
                CardView cardView = (CardView) CardView.inflate(item.getContext(), R.layout.place_detail_cardview, null);
                PlaceCardViewHolder holder = new PlaceCardViewHolder(cardView);
                holder.bind(place);

                new AlertDialog.Builder(item.getContext()).setView(cardView).show();
            }
        });
    }
}
