package com.github.funnygopher.imhungry.model.database;

import android.support.annotation.Nullable;

import com.github.funnygopher.imhungry.model.Place;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmService {

    private final Realm realm;
    private long lastPlaceId;

    public RealmService() {
        realm = Realm.getDefaultInstance();
    }

    public void closeRealm() {
        realm.close();
    }

    public void addPlace(Place place) {
        place.setId(getPrimaryKey(place));

        realm.beginTransaction();
        realm.insert(place);
        realm.commitTransaction();
    }

    private long getPrimaryKey(RealmObject object) {
        Number primaryKey = realm.where(object.getClass()).max("id");
        if (primaryKey == null) primaryKey = 1;
        return primaryKey.longValue() + 1;
    }

    public Realm getRealmInstance() {
        return realm;
    }

    @Nullable
    public Place getRandomPlace(int value) {
        RealmResults<Place> places = realm.where(Place.class)
                .notEqualTo("id", lastPlaceId)
                .equalTo("price", value).findAll();
        if (places.isEmpty()) {
            return realm.where(Place.class).equalTo("id", lastPlaceId).findFirst();
        }

        Random random = new Random();
        int i = random.nextInt(places.size());
        Place place = places.get(i);
        lastPlaceId = place.getId();
        return places.get(i);
    }
}
