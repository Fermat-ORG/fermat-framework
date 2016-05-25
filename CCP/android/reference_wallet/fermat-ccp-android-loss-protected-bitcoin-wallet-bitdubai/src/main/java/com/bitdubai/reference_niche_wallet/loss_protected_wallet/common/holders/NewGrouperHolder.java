package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.holders;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentViewHolder;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentViewHolder;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.formatBalanceString;


/**
 * Created by Joaquin Carrasquero on 04/05/16.
 */
public class NewGrouperHolder extends ParentViewHolder {

    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;
    private static final float PIVOT_VALUE = 0.5f;
    private static final long DEFAULT_ROTATE_DURATION_MS = 200;
    private static final boolean HONEYCOMB_AND_ABOVE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    public ColorDrawable background;
    public ImageView mArrowExpandImageView;
    Resources res;
    private View itemView;
    private TextView txt_amount;
    private TextView txt_time;
    /**
     * Public constructor for the CustomViewHolder.
     *
     * @param itemView the view of the parent item. Find/modify views using this.
     */
    public NewGrouperHolder(View itemView,Resources res) {
        super(itemView);

        this.res = res;
        this.itemView = itemView;

        txt_amount = (TextView) itemView.findViewById(R.id.txt_amount2);
        txt_time = (TextView) itemView.findViewById(R.id.txt_time2);
        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.cbw_arrow2);
    }

    /**
     * Set the data to the views
     *
     */
    public void bind(int childCount,LossProtectedWalletTransaction lossProtectedWalletTransaction) {


        txt_amount.setText(formatBalanceString(lossProtectedWalletTransaction.getAmount() , ShowMoneyType.BITCOIN.getCode())+ " BTC");

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy HH:mm", Locale.US);
        txt_time.setText(sdf.format(lossProtectedWalletTransaction.getTimestamp()) + " hs");

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

    private Drawable getImgDrawable(byte[] customerImg) {
        if (customerImg != null && customerImg.length > 0)
            return ImagesUtils.getRoundedBitmap(res, customerImg);

        return ImagesUtils.getRoundedBitmap(res, R.drawable.ic_profile_male);
    }
}
