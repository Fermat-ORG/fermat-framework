package org.fermat.fermat_dap_android_wallet_asset_user.v2.common.holders;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ParentViewHolder;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Issuer;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/26/16.
 */
public class HomeIssuerGrouperViewHolder extends ParentViewHolder {
    //ANIMATED ARROW
    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;
    private static final float PIVOT_VALUE = 0.5f;
    private static final long DEFAULT_ROTATE_DURATION_MS = 200;
    private static final boolean HONEYCOMB_AND_ABOVE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

    //RESOURCES
    private final Resources res;

    //UI
    private ImageView homeIssuerImage;
    private FermatTextView homeIssuerNameText;
    private FermatTextView homeIssuerNumAssets;
    public ImageView mArrowExpandImageView;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public HomeIssuerGrouperViewHolder(View itemView, Resources resources) {
        super(itemView);
        this.res = resources;

        homeIssuerImage = (ImageView) itemView.findViewById(R.id.homeIssuerImage);
        homeIssuerNameText = (FermatTextView) itemView.findViewById(R.id.homeIssuerNameText);
        homeIssuerNumAssets = (FermatTextView) itemView.findViewById(R.id.homeIssuerNumAssets);
        mArrowExpandImageView = (ImageView) itemView.findViewById(R.id.homeIssuerArrow);
    }

    public void bind(int assetsCount, Issuer issuer) {
        Bitmap bitmap;
        if (issuer.getImage() != null && issuer.getImage().length > 0) {
            bitmap = BitmapFactory.decodeByteArray(issuer.getImage(), 0, issuer.getImage().length);
        } else {
            bitmap = BitmapFactory.decodeResource(res, R.drawable.img_detail_without_image);
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, 65, 65, true);
        homeIssuerImage.setImageDrawable(ImagesUtils.getRoundedBitmap(res, bitmap));

        homeIssuerNameText.setText(issuer.getName());

        if (issuer.getAssets() != null) {
            homeIssuerNumAssets.setText(assetsCount + " " + res.getString(R.string.dap_user_wallet_v2_home_issuer_item_num_assets));
        }
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
