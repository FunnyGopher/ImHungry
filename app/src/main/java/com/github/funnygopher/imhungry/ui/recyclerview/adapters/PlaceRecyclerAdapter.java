package com.github.funnygopher.imhungry.ui.recyclerview.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.funnygopher.imhungry.R;
import com.github.funnygopher.imhungry.model.Place;
import com.github.funnygopher.imhungry.ui.recyclerview.viewholders.PlaceViewHolder;

import io.realm.Realm;

public class PlaceRecyclerAdapter extends RealmSearchAdapter<Place, PlaceViewHolder> {

    public PlaceRecyclerAdapter(@NonNull Context context, @NonNull Realm realm, @NonNull String filterKey) {
        super(context, realm, filterKey);
        filter("");
    }

    @Override
    public PlaceViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int i) {
        return new PlaceViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.my_places_list_item, viewGroup, false));
    }

    @Override
    public void onBindRealmViewHolder(PlaceViewHolder viewHolder, int i) {
        Place place = realmResults.get(i);
        viewHolder.bind(place);
    }
}
