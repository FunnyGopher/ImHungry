package com.github.funnygopher.imhungry.ui.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.github.funnygopher.imhungry.R;
import com.github.funnygopher.imhungry.injection.DaggerService;
import com.github.funnygopher.imhungry.ui.activities.NewPlaceActivity;
import com.github.funnygopher.imhungry.ui.recyclerview.adapters.PlaceRecyclerAdapter;
import com.github.funnygopher.imhungry.ui.screens.MyPlacesScreen;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import mortar.MortarScope;
import timber.log.Timber;

public class MyPlacesView extends FrameLayout {

    private static final String FILTER_KEY = "name";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    MyPlacesScreen.Presenter presenter;

    @SuppressWarnings("ConstantConditions")
    public MyPlacesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Timber.tag(getClass().getSimpleName());
        MortarScope scope = Flow.getService(Flow.getKey(this).getClass().getName(), context);
        scope.<MyPlacesScreen.Component>getService(DaggerService.SERVICE_NAME).inject(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        presenter.takeView(this);

        PlaceRecyclerAdapter adapter = new PlaceRecyclerAdapter(getContext(), presenter.getData(), FILTER_KEY);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
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
    }

    @OnClick(R.id.my_places_fab)
    void onFabClick() {
        Intent intent = new Intent(getContext(), NewPlaceActivity.class);
        getContext().startActivity(intent);
    }
}
