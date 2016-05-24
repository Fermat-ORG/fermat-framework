package com.fermat.clelia.loadinganimationdialog.Utility;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.os.Handler;
import android.util.Pair;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by Clelia López on 4/15/16
 */
public class AnimationBuilder {

    /**
     * Attributes
     */
    private LinkedHashMap<String,ObjectAnimator> animatorMap;
    private LinkedHashMap<String,PropertyAnimation> propertyMap;
    private ArrayList<Integer> durations;
    private ArrayList<Integer> delays;
    private AnimationScheduler scheduler;
    private Iterator<PropertyAnimation> propertyIterator;
    private Iterator<ObjectAnimator> animatorIterator;

    private RepeatMode repeatMode;
    private int repeatCount = 1;
    private boolean playTogether= false;
    private boolean playSequentially = false;

    protected Logger logger;

    /**
     * Constructor
     */
    public AnimationBuilder() {
        animatorMap = new LinkedHashMap<>();
        propertyMap = new LinkedHashMap<>();
        durations = new ArrayList<>();
        delays = new ArrayList<>();

        logger = new Logger(getClass().getSimpleName());
    }

    public void setRepeatMode(RepeatMode repeatMode) {
        this.repeatMode = repeatMode;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    private void setPlayTogether(boolean playTogether) {
        this.playTogether = playTogether;
    }

    private void setPlaySequentially(boolean playSequentially) {
        this.playSequentially = playSequentially;
    }

    public RepeatMode getRepeatMode() {
        return repeatMode;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public boolean isPlayTogether() {
        return playTogether;
    }

    public boolean isPlaySequentially() {
        return playSequentially;
    }


    private int getMaximumDuration() {
        return Collections.max(durations);
    }

    private int getMaximumDelay() {
        return Collections.max(delays);
    }

    private int getAccumulativeDuration() {
        int accumulativeDuration = 0;
        for(LinkedHashMap.Entry<String, PropertyAnimation> property : propertyMap.entrySet())
            accumulativeDuration = accumulativeDuration + property.getValue().getDuration();
        return accumulativeDuration;
    }

    private int getAccumulativeDelay() {
        int accumulativeDelay = 0;
        for(LinkedHashMap.Entry<String, PropertyAnimation> property : propertyMap.entrySet())
            accumulativeDelay = accumulativeDelay + property.getValue().getDelay();
        return accumulativeDelay;
    }

    private int getAnimationsCount() {
        return animatorMap.size();
    }

    private PropertyAnimation getNextProperty() {
        PropertyAnimation property = null;
        if(propertyIterator.hasNext())
            property = propertyIterator.next();
        return property;
    }

    private ObjectAnimator getNextAnimator() {
        ObjectAnimator animator = null;
        if(animatorIterator.hasNext())
            animator = animatorIterator.next();
        return animator;
    }

    private void initializeIterators() {
        propertyIterator = propertyMap.values().iterator();
        animatorIterator = animatorMap.values().iterator();
    }


    protected void createAnimations(LinkedHashMap<String,PropertyAnimation> list) {
        propertyMap = list;
        ObjectAnimator animator;

        for(LinkedHashMap.Entry<String, PropertyAnimation> property : propertyMap.entrySet()) {
            Property type = null;
            switch (property.getValue().getType()) {
                case FADE:
                    type = View.ALPHA;
                    break;
                case ROTATE:
                    type = View.ROTATION;
                    break;
                case ROTATE_X:
                    type = View.ROTATION_X;
                    break;
                case ROTATE_Y:
                    type = View.ROTATION_Y;
                    break;
                case SCALE_X:
                    type = View.SCALE_X;
                    break;
                case SCALE_Y:
                    type = View.SCALE_Y;
                    break;
                case MOVE_X:
                    type = View.TRANSLATION_X;
                    break;
                case MOVE_Y:
                    type = View.TRANSLATION_Y;
                    break;
            }

            TimeInterpolator interpolator = null;
            switch (property.getValue().getInterpolator()) {
                case ACCELERATE:
                    interpolator = new AccelerateInterpolator();
                    break;
                case BOUNCE:
                    interpolator = new BounceInterpolator();
                    break;
                case DECELERATE:
                    interpolator = new DecelerateInterpolator();
                    break;
                case LINEAR:
                    interpolator = new LinearInterpolator();
                    break;
            }

            //noinspection unchecked
            animator = ObjectAnimator.ofFloat(property.getValue().getView(), type,
                    property.getValue().getFrom(), property.getValue().getTo());
            animator.setInterpolator(interpolator);
            animator.setDuration(property.getValue().getDuration());

            durations.add(property.getValue().getDuration());
            delays.add(property.getValue().getDelay());

            animatorMap.put(property.getValue().getName(), animator);
        }
    }

    public void start() {
        propertyIterator = propertyMap.values().iterator();
        animatorIterator = animatorMap.values().iterator();

        scheduler = new AnimationScheduler(this);

        if(repeatMode == RepeatMode.INFINITE)
            repeatCount = 100;
        scheduler.onAnimationSetStart();
    }

    public void stop() {
        scheduler.stopScheduler();
    }

    public enum Type {
        FADE,
        ROTATE,ROTATE_X, ROTATE_Y,
        SCALE_X, SCALE_Y,
        MOVE_X, MOVE_Y
    }

    public enum Interpolator { ACCELERATE, BOUNCE, DECELERATE, LINEAR }

    public enum RepeatMode { COUNTER, INFINITE }

    public enum Notification { START, END }

    interface AnimationListener {
        void onAnimationStart();
        void onAnimationEnd();
    }

    private class AnimationScheduler
            implements AnimationListener {

        private ObjectAnimator currentAnimator;
        private PropertyAnimation currentProperty;
        private AnimationBuilder animationSet;
        private int playedCount = 0;
        private int repeatCount = 0;
        private Handler handler;
        private Runnable runnable;
        private volatile boolean stopRequested = false;

        public AnimationScheduler(AnimationBuilder builder) {
            handler = new Handler();
            animationSet = builder;
        }

        @Override
        public void onAnimationStart() {
            logger.log("STOP = " +  stopRequested);
            if(!stopRequested) {
                logger.log("Animation: " + currentProperty.getName() + " has STARTED");
                currentAnimator.start();
                playedCount++;
                if(playedCount < animationSet.getAnimationsCount() && playTogether) {
                    currentAnimator = animationSet.getNextAnimator();
                    currentProperty = animationSet.getNextProperty();
                    scheduleNotification(Notification.START, currentProperty.getDelay());
                    scheduleNotification(Notification.END, currentProperty.getDelay() + currentAnimator.getDuration());
                }
            }
        }

        @Override
        public void onAnimationEnd() {
            logger.log("STOP = " +  stopRequested);
            if(!stopRequested) {
                logger.log("Animation: " + currentProperty.getName() + " has ENDED");
                if(playedCount < animationSet.getAnimationsCount()) {
                    if (animationSet.isPlaySequentially()) {
                        currentAnimator = animationSet.getNextAnimator();
                        currentProperty = animationSet.getNextProperty();
                        scheduleNotification(Notification.START, currentProperty.getDelay());
                        scheduleNotification(Notification.END, currentProperty.getDelay() + currentAnimator.getDuration());
                    }
                } else if (playedCount == animationSet.getAnimationsCount()) {
                    playedCount = 0;
                    animationSet.initializeIterators();
                    onAnimationSetEnd();
                }
            }
        }

        public void onAnimationSetStart() {
            logger.log("STOP = " +  stopRequested);
            if(!stopRequested) {
                logger.log("onAnimationSetStart");
                currentAnimator = animationSet.getNextAnimator();
                currentProperty = animationSet.getNextProperty();
                if (currentAnimator != null) {
                    if (playSequentially || playTogether) {
                        scheduleNotification(Notification.START, currentProperty.getDelay());
                        scheduleNotification(Notification.END, currentProperty.getDelay() + currentAnimator.getDuration());
                        if (playTogether)
                            repeatCount++;
                    }
                }
            }
        }

        public void onAnimationSetEnd() {
            logger.log("STOP = " +  stopRequested);
            if(!stopRequested) {
                logger.log("onAnimationSetEnd");
                int delay = 0;
                if(playTogether)
                    delay = getTotalTime();

                switch (animationSet.getRepeatMode()) {
                    case COUNTER:
                        scheduleSetNotification(repeatCount, delay);
                        repeatCount++;
                        break;
                    case INFINITE:
                        scheduleSetNotification(-1, delay);
                        break;
                }
            }
        }

        private void scheduleNotification(final Notification notification, long time) {
            logger.log("STOP = " +  stopRequested);
            if(!stopRequested) {
                logger.log("ON scheduleNotification");
                runnable = new Runnable() {
                    public void run() {
                        if(!stopRequested) {
                            switch (notification) {
                                case START:
                                    onAnimationStart();
                                    break;
                                case END:
                                    onAnimationEnd();
                                    break;
                            }
                        }
                    }
                };
                handler.postDelayed(runnable, time);
            }
        }

        private void scheduleSetNotification(final int counter, long time) {
            logger.log("STOP = " +  stopRequested);
            if(!stopRequested) {
                logger.log("ON scheduleSetNotification");
                runnable = new Runnable() {
                    public void run() {
                        if(!stopRequested) {
                            if (counter < animationSet.getRepeatCount())
                                onAnimationSetStart();
                        }
                    }
                };
                handler.postDelayed(runnable, time);
            }
        }

        private int getTotalTime() {
            Pair<Integer, Integer> timer = null;
            if(animationSet.isPlayTogether())
                timer = new Pair<>(animationSet.getMaximumDelay(), animationSet.getMaximumDuration());
            else if(animationSet.isPlaySequentially())
                timer = new Pair<>((animationSet.getAccumulativeDelay()), animationSet.getAccumulativeDuration());

            //noinspection ConstantConditions
            return (timer.first + timer.second);
        }

        private void stopScheduler() {
            handler.removeCallbacksAndMessages(null);
            stopRequested = true;
        }
    }

    /**
     * Class: Manages the creation of a data structure associated with one animation
     */
    private class PropertyAnimation {
        protected View view;
        protected String name;
        protected Type type;
        protected Interpolator interpolator;
        protected float from;
        protected float to;
        protected int duration;
        private int delay;

        public PropertyAnimation(View View, String Name) {
            this.view = View;
            this.name = Name;
            type = null;
            interpolator = null;
            from = -1;
            to = -1;
            duration = -1;
            delay = 0;
        }

        public void setType(Type Type) {
            this.type = Type;
        }

        public void setInterpolator(Interpolator Interpolator) {
            this.interpolator = Interpolator;
        }

        public void setFrom(float From) {
            this.from = From;
        }

        public void setTo(float To) {
            this.to = To;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getName() {
            return name;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }

        public View getView() {
            return view;
        }

        public Type getType() {
            return type;
        }

        public Interpolator getInterpolator() {
            return interpolator;
        }

        public float getFrom() {
            return from;
        }

        public float getTo() {
            return to;
        }

        public int getDuration() {
            return duration;
        }

        public int getDelay() {
            return delay;
        }
    }

    /**
     *
     */
    public class Builder {
        private PropertyAnimation property = null;
        private LinkedHashMap<String, PropertyAnimation> list = null;

        private boolean playTogether = false;
        private boolean playSequentially = false;

        private AnimationBuilder animationBuilder;

        public Builder() {
            list = new LinkedHashMap<>();
            animationBuilder = new AnimationBuilder();
        }

        public Builder createAnimation(View view, String  name) {
            property = new PropertyAnimation(view, name);
            return this;
        }

        public Builder setType(Type type) {
            property.setType(type);
            return this;
        }

        public Builder setInterpolator(Interpolator interpolator) {
            property.setInterpolator(interpolator);
            return this;
        }

        public Builder setFrom(float from) {
            property.setFrom(from);
            return this;
        }

        public Builder setTo(float to) {
            property.setTo(to);
            return this;
        }

        public Builder setDuration(int duration) {
            property.setDuration(duration);
            return this;
        }

        public Builder add() {
            if(property != null)
                list.put(property.getName(), property);

            property = null;

            return this;
        }

        public Builder playTogether() {
            this.playTogether = true;
            return this;
        }

        public Builder playSequentially() {
            this.playSequentially = true;
            return this;
        }

        /**
         * Sets delay time for this animation
         * @param delay - desired delay time in milliseconds
         */
        public Builder playAfter(int delay) {
            property.setDelay(delay);
            return this;
        }

        public Builder repeatMode(RepeatMode mode) {
            animationBuilder.setRepeatMode(mode);
            return this;
        }

        public Builder repeatMode(RepeatMode mode, int counter) {
            animationBuilder.setRepeatMode(mode);
            animationBuilder.setRepeatCount(counter);
            return this;
        }

        public AnimationBuilder start() {
            animationBuilder.createAnimations(list);
            if(playTogether)
                animationBuilder.setPlayTogether(true);
            else if(playSequentially)
                animationBuilder.setPlaySequentially(true);

            animationBuilder.start();

            return animationBuilder;
        }
    }
}
