package com.github.funnygopher.imhungry.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.funnygopher.imhungry.ImHungry;
import com.github.funnygopher.imhungry.R;
import com.github.funnygopher.imhungry.flow.Changer;
import com.github.funnygopher.imhungry.injection.AppDependencies;
import com.github.funnygopher.imhungry.injection.DaggerService;
import com.github.funnygopher.imhungry.injection.scopes.DaggerScope;
import com.github.funnygopher.imhungry.mortarflow.DaggerServiceFactory;
import com.github.funnygopher.imhungry.ui.screens.FindFoodScreen;
import com.github.funnygopher.imhungry.ui.screens.MyPlacesScreen;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.Flow;
import flow.KeyDispatcher;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_tablayout)
    TabLayout tabLayout;

    private MortarScope mortarScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mortarScope = MortarScope.findChild(getApplicationContext(), getClass().getName());

        if (mortarScope == null) {
            Component component = DaggerMainActivity_Component.builder()
                    .component(DaggerService.<ImHungry.Component>getDaggerComponent(getApplicationContext()))
                    .build();

            mortarScope = MortarScope.buildChild(getApplicationContext())
                    .withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner())
                    .withService(DaggerService.SERVICE_NAME, component)
                    .build(getClass().getName());
        }

        DaggerService.<Component>getDaggerComponent(this).inject(this);

        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Object screen;
                switch (tab.getPosition()) {
                    default:
                    case 0:
                        screen = new FindFoodScreen();
                        break;
                    case 1:
                        screen = new MyPlacesScreen();
                        break;
                }
                Flow.get(getBaseContext()).set(screen);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void attachBaseContext(Context baseContext) {
        baseContext = Flow.configure(baseContext, this)
                .addServicesFactory(new DaggerServiceFactory(this))
                .dispatcher(KeyDispatcher.configure(this, new Changer(this)).build())
                .defaultKey(new FindFoodScreen())
                .install();
        super.attachBaseContext(baseContext);
    }

    @Override
    public void onBackPressed() {
        if (!Flow.get(this).goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            if (mortarScope != null) {
                mortarScope.destroy();
            }
        }

        super.onDestroy();
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        // see: https://github.com/square/mortar/issues/155
        if (getApplication() == null) {
            return super.getSystemService(name);
        }

        Object service = Flow.getService(name, getApplicationContext());
        if (service == null && mortarScope != null && mortarScope.hasService(name)) {
            service = mortarScope.getService(name);
        }

        return service != null ? service : super.getSystemService(name);
    }

    @dagger.Component(dependencies = ImHungry.Component.class)
    @DaggerScope(Component.class)
    public interface Component extends AppDependencies {
        void inject(MainActivity activity);
    }
}
