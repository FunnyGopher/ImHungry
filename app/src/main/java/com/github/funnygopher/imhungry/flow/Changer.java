package com.github.funnygopher.imhungry.flow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.funnygopher.imhungry.R;
import com.github.funnygopher.imhungry.flow.keys.FindFoodKey;
import com.github.funnygopher.imhungry.flow.keys.MyPlacesKey;

import java.util.Map;

import flow.Direction;
import flow.Flow;
import flow.KeyChanger;
import flow.State;
import flow.TraversalCallback;
import timber.log.Timber;

public class Changer implements KeyChanger {

    private final Activity activity;

    public Changer(Activity activity) {
        Timber.tag(getClass().getSimpleName());
        this.activity = activity;
    }

    /**
     * Transition from outgoing state to incoming state.  Implementations should call
     * {@link State#restore(View)} on the incoming view, and (if outgoingState is not null)
     * {@link State#save(View)} on the outgoing view.  And don't forget to declare your screen layouts
     * with ids (only layouts with ids will have their state saved/restored)!
     */
    @Override
    public void changeKey(@Nullable State outgoingState, @NonNull State incomingState, @NonNull final Direction direction, @NonNull Map<Object, Context> incomingContexts, @NonNull final TraversalCallback callback) {
        final ViewGroup frame = (ViewGroup) activity.findViewById(R.id.content);

        Object destinationKey = incomingState.getKey();

        View currentView = null;
        // We're already showing something, clean it up
        if (frame.getChildCount() > 0) {
            currentView = frame.getChildAt(0);

            // Save the outgoing view state
            if (outgoingState != null) {
                outgoingState.save(currentView);
            }

            // Stop if we would just be showing the same view again
            final Object currentKey = Flow.getKey(currentView);
            if (destinationKey.equals(currentKey)) {
                callback.onTraversalCompleted();
                return;
            }

            frame.removeAllViews();
        }

        Context context = incomingContexts.get(destinationKey);
        @LayoutRes final int layout;
        if (destinationKey instanceof FindFoodKey) {
            layout = R.layout.find_food_screen;
        } else if (destinationKey instanceof MyPlacesKey) {
            layout = R.layout.my_places_screen;
        } else {
            throw new AssertionError("Unrecognized screen " + destinationKey);
        }

        final View incomingView = LayoutInflater.from(context).inflate(layout, frame, false);
        if (outgoingState != null) {
            outgoingState.restore(incomingView);
        }

        if (currentView == null || direction == Direction.REPLACE) {
            frame.removeAllViews();
            frame.addView(incomingView);
            callback.onTraversalCompleted();
        } else {
            frame.addView(incomingView);
            final View finalCurrentView = currentView;
            FlowUtils.waitForMeasure(incomingView, new FlowUtils.OnMeasuredCallback() {
                @Override
                public void onMeasured(View view, int width, int height) {
                    runAnimation(frame, finalCurrentView, incomingView, direction, new TraversalCallback() {
                        @Override
                        public void onTraversalCompleted() {
                            frame.removeView(finalCurrentView);
                            callback.onTraversalCompleted();
                        }
                    });
                }
            });
        }
    }

    private void runAnimation(final ViewGroup container, final View from, final View to,
                              Direction direction, final TraversalCallback callback) {
        Animator animator = createSegue(from, to, direction);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                container.removeView(from);
                callback.onTraversalCompleted();
            }
        });
        animator.start();
    }

    private Animator createSegue(View from, View to, Direction direction) {
        boolean backward = direction == Direction.BACKWARD;
        int fromTranslation = backward ? from.getWidth() : -from.getWidth();
        int toTranslation = backward ? -to.getWidth() : to.getWidth();

        AnimatorSet set = new AnimatorSet();

        set.play(ObjectAnimator.ofFloat(from, View.TRANSLATION_X, fromTranslation));
        set.play(ObjectAnimator.ofFloat(to, View.TRANSLATION_X, toTranslation, 0));

        return set;
    }
}
