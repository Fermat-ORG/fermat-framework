package com.flip_card;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * Created by mati on 2016.03.08..
 */
public class FlipCardView extends RelativeLayout implements View.OnClickListener{

    /**
     * UI
     */
    private RelativeLayout rootLayout;
    private RelativeLayout cardFace;
    private RelativeLayout cardBack;

    /**
     * Custom card res
     */
    private int cardHeadRes = 0;
    private int cardTailsRes = 0;


    public FlipCardView(Context context) {
        super(context);
        init();
    }

    public FlipCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FlipCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlipCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        rootLayout = (RelativeLayout) findViewById(R.id.main_activity_root);
        cardFace = (RelativeLayout) findViewById(R.id.main_activity_card_face);
        cardBack =  (RelativeLayout) findViewById(R.id.main_activity_card_back);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // heads
        View view = layoutInflater.inflate(cardHeadRes, null);
        view.setLayoutParams(layoutParams);
        cardFace.addView(view);
        // tails
        view = layoutInflater.inflate(cardTailsRes, null);
        view.setLayoutParams(layoutParams);
        cardBack.addView(view);

        //listener
        setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        onCardClick(v);
    }

    public void onCardClick(View view) {
        flipCard();
    }

    private void flipCard() {
        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    public void setCardHeadRes(int cardHeadRes) {
        this.cardHeadRes = cardHeadRes;
    }

    public void setCardTailsRes(int cardTailsRes) {
        this.cardTailsRes = cardTailsRes;
    }

    public static class Builder{

        private Context context;
        private int cardHeadRes = 0;
        private int cardTailsRes = 0;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setCardHeadRes(int cardHeadRes) {
            this.cardHeadRes = cardHeadRes;
            return this;
        }

        public Builder setCardTailsRes(int cardTailsRes) {
            this.cardTailsRes = cardTailsRes;
            return this;
        }

        public FlipCardView build(){
            FlipCardView flipCardView = new FlipCardView(context);
            flipCardView.setCardHeadRes(cardHeadRes);
            flipCardView.setCardTailsRes(cardTailsRes);
            return flipCardView;
        }


    }

}
