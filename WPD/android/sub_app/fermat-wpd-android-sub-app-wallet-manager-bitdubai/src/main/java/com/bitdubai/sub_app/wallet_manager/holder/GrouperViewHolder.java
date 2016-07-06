package com.bitdubai.sub_app.wallet_manager.holder;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentViewHolder;
import com.bitdubai.fermat_api.layer.all_definition.enums.FontType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_wpd.wallet_manager.R;



/**
 * Created by nelson on 21/10/15.
 */
public class GrouperViewHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;
    private static final float PIVOT_VALUE = 0.5f;
    private static final long DEFAULT_ROTATE_DURATION_MS = 200;
    private static final boolean HONEYCOMB_AND_ABOVE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    public ColorDrawable background;
    public ImageView mArrowExpandImageView;
    Resources res;
    private View itemView;
    private FermatTextView txt_grouper;

    /**
     * Public constructor for the CustomViewHolder.
     *
     * @param itemView the view of the parent item. Find/modify views using this.
     */
    public GrouperViewHolder(View itemView, Resources res) {
        super(itemView);

        this.res = res;
        this.itemView = itemView;

        txt_grouper = (FermatTextView) itemView.findViewById(R.id.txt_grouper);
        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.arrow);
    }

    /**
     * Set the data to the views
     *
     */
    public void bind(int childCount,Platforms platforms) {
        txt_grouper.setText(platforms.getTextForm());
        txt_grouper.setFont(FontType.CAVIAR_DREAMS_BOLD);
        txt_grouper.setTextColor(Color.WHITE);
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
    public void onExpansionToggled(boolean wasExpanded) {
        super.onExpansionToggled(wasExpanded);

        //mNumberTextView.setVisibility(wasExpanded ? View.VISIBLE : View.GONE);

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
