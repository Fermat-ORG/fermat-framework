package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by nelson on 21/10/15.
 */
public class GrouperViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;
    private static final float PIVOT_VALUE = 0.5f;
    private static final long DEFAULT_ROTATE_DURATION_MS = 200;
    private static final boolean HONEYCOMB_AND_ABOVE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

    public TextView mNumberTextView;
    public TextView mDataTextView;
    public ImageView mArrowExpandImageView;

    /**
     * Public constructor for the CustomViewHolder.
     *
     * @param itemView the view of the parent item. Find/modify views using this.
     */
    public GrouperViewHolder(View itemView) {
        super(itemView);

        mNumberTextView = (TextView) itemView.findViewById(R.id.cbw_customer_count);
        mDataTextView = (TextView) itemView.findViewById(R.id.cbw_group_description);
        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.cbw_arrow);
    }

    /**
     * Set the data to the views
     *
     * @param childCount the number of children contained by this grouper item
     * @param parentText the text show by this grouper item
     */
    public void bind(int childCount, String parentText) {
        mNumberTextView.setText(String.valueOf(childCount));
        mDataTextView.setText(parentText);
    }

    @Override
    @SuppressLint("NewApi")
    public void setExpanded(boolean expanded) {
        super.setExpanded(expanded);
        if (!HONEYCOMB_AND_ABOVE) {
            return;
        }

        if (expanded) {
            mArrowExpandImageView.setRotation(ROTATED_POSITION);
        } else {
            mArrowExpandImageView.setRotation(INITIAL_POSITION);
        }
    }

    @Override
    public void onExpansionToggled(boolean expanded) {
        super.onExpansionToggled(expanded);

        mNumberTextView.setVisibility(expanded ? View.GONE : View.VISIBLE);

        if (!HONEYCOMB_AND_ABOVE) {
            return;
        }

        RotateAnimation rotateAnimation = new RotateAnimation(
                ROTATED_POSITION, INITIAL_POSITION,
                RotateAnimation.RELATIVE_TO_SELF, PIVOT_VALUE,
                RotateAnimation.RELATIVE_TO_SELF, PIVOT_VALUE);

        rotateAnimation.setDuration(DEFAULT_ROTATE_DURATION_MS);
        rotateAnimation.setFillAfter(true);

        mArrowExpandImageView.startAnimation(rotateAnimation);
    }
}
