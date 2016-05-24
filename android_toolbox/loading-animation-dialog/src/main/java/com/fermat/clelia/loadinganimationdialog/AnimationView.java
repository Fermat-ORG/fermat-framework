package com.fermat.clelia.loadinganimationdialog;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fermat.clelia.loadinganimationdialog.Custom.CircleShape;
import com.fermat.clelia.loadinganimationdialog.Utility.AnimationBuilder;
import com.fermat.clelia.loadinganimationdialog.Utility.Logger;
import com.fermat.clelia.loadinganimationdialog.Utility.ScreenUnitConverter;

import java.util.ArrayList;

/**
 * Created by Clelia López on 4/14/16
 */
public class AnimationView
        extends LinearLayout {

    /**
     * Attributes
     */
    private Context context;
    private LinearLayout container;
    private ImageView backgroundCircle;
    private ImageView greenWalletImageView;
    private ImageView redWalletImageView;
    private ImageView orangeWalletImageView;
    private ImageView darkWalletImageView;
    private ImageView loadingTextView;
    private CircleShape firstDot;
    private CircleShape secondDot;
    private CircleShape thirdDot;

    ArrayList<AnimationBuilder> animations;

    private final Logger logger = new Logger(getClass().getSimpleName());

    private float from;
    private float to;


    public AnimationView(Context context) {
        super(context);
        this.context = context;

        initializeViews();
    }

    public ArrayList<AnimationBuilder> getAnimations() {
        return animations;
    }

    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initializeViews();
    }

    public void initializeViews() {
        animations = new ArrayList<>();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        container = (LinearLayout) inflater.inflate(R.layout.animation_layout, this, true);
        container = (LinearLayout) container.getChildAt(0);

        FrameLayout logoContainer = (FrameLayout) container.getChildAt(0);
        backgroundCircle = (ImageView) logoContainer.getChildAt(0);
        FrameLayout walletsContainer = (FrameLayout) logoContainer.getChildAt(1);
        redWalletImageView = (ImageView) walletsContainer.getChildAt(0);
        darkWalletImageView = (ImageView) walletsContainer.getChildAt(1);
        orangeWalletImageView = (ImageView) walletsContainer.getChildAt(2);
        greenWalletImageView = (ImageView) walletsContainer.getChildAt(3);

        LinearLayout labelContainer = (LinearLayout) container.getChildAt(1);
        loadingTextView = (ImageView) labelContainer.getChildAt(0);
        LinearLayout dotsContainer = (LinearLayout) labelContainer.getChildAt(1);
        firstDot = (CircleShape) dotsContainer.getChildAt(0);
        secondDot = (CircleShape) dotsContainer.getChildAt(1);
        thirdDot = (CircleShape) dotsContainer.getChildAt(2);

        // Listener for parameter calculation, post layout rendering
        logoContainer.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    //Remove the listener before proceeding
                    container.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    // measure your views here
                    int positionGreenWallet[] = new int[2];
                    int positionCircle[] = new int[2];
                    greenWalletImageView.getLocationInWindow(positionGreenWallet);
                    backgroundCircle.getLocationInWindow(positionCircle);
                    to = positionGreenWallet[1] - positionCircle[1];
                    to = to - ScreenUnitConverter.convertValueTo(context, 20, ScreenUnitConverter.Unit.PIXELS);;
                    from = greenWalletImageView.getY() - ScreenUnitConverter.convertValueTo(context, 35, ScreenUnitConverter.Unit.PIXELS);;

                    buildAnimations(from, to);
                }
            }
        );
    }

    public void buildAnimations(float from, float to) {
        int delay;
        int duration = 500;
        int factor = 15;

        AnimationBuilder walletAnimation = new AnimationBuilder().new Builder()
                .createAnimation(orangeWalletImageView, "OrangeAnimationUp")
                .setType(AnimationBuilder.Type.MOVE_Y)
                .setFrom(-from)
                .setTo(-to + factor)
                .setInterpolator(AnimationBuilder.Interpolator.LINEAR)
                .setDuration(duration)
                .playAfter(100)
                .add()

                .createAnimation(darkWalletImageView, "DarkAnimationUp")
                .setType(AnimationBuilder.Type.MOVE_Y)
                .setFrom(-from)
                .setTo(-to)
                .setInterpolator(AnimationBuilder.Interpolator.LINEAR)
                .setDuration(duration)
                .add()

                .createAnimation(redWalletImageView, "RedAnimationUp")
                .setType(AnimationBuilder.Type.MOVE_Y)
                .setFrom(-from)
                .setTo(-to)
                .setInterpolator(AnimationBuilder.Interpolator.LINEAR)
                .setDuration(duration)
                .add()

                .createAnimation(orangeWalletImageView, "OrangeAnimationDown")
                .setType(AnimationBuilder.Type.MOVE_Y)
                .setFrom(-to + factor)
                .setTo(-from)
                .setInterpolator(AnimationBuilder.Interpolator.LINEAR)
                .setDuration(duration)
                .add()

                .createAnimation(darkWalletImageView, "DarkAnimationDown")
                .setType(AnimationBuilder.Type.MOVE_Y)
                .setFrom(-to)
                .setTo(-from)
                .setInterpolator(AnimationBuilder.Interpolator.LINEAR)
                .setDuration(duration)
                .add()

                .createAnimation(redWalletImageView, "RedAnimationDown")
                .setType(AnimationBuilder.Type.MOVE_Y)
                .setFrom(-to)
                .setTo(-from)
                .setInterpolator(AnimationBuilder.Interpolator.LINEAR)
                .setDuration(duration)
                .add()

                .repeatMode(AnimationBuilder.RepeatMode.INFINITE)
                .playSequentially()
                .start();

        duration = 1000;
        delay = 1500;
        AnimationBuilder dotsAnimation = new AnimationBuilder().new Builder()
                .createAnimation(firstDot, "FirstDot")
                .setType(AnimationBuilder.Type.FADE)
                .setFrom(0)
                .setTo(1)
                .setInterpolator(AnimationBuilder.Interpolator.LINEAR)
                .setDuration(duration)
                .playAfter(delay)
                .add()

                .createAnimation(secondDot, "SecondDot")
                .setType(AnimationBuilder.Type.FADE)
                .setFrom(0)
                .setTo(1)
                .setInterpolator(AnimationBuilder.Interpolator.LINEAR)
                .setDuration(duration)
                .add()

                .createAnimation(thirdDot, "ThirdDot")
                .setType(AnimationBuilder.Type.FADE)
                .setFrom(0)
                .setTo(1)
                .setInterpolator(AnimationBuilder.Interpolator.LINEAR)
                .setDuration(duration)
                .add()

                .repeatMode(AnimationBuilder.RepeatMode.INFINITE)
                .playSequentially()
                .start();

        animations.add(walletAnimation);
        animations.add(dotsAnimation);
    }
}
