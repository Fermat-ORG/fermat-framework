package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by Nelson Ramirez
 *
 * @since 22/12/15.
 */
public class ActorIdentityViewHolder extends FermatViewHolder {
    private Resources res;

    private ImageView identityImage;
    private FermatTextView identityAlias;

    public ActorIdentityViewHolder(View itemView) {
        super(itemView);
        res = itemView.getResources();

        identityImage = (ImageView) itemView.findViewById(R.id.cbw_identity_image);
        identityAlias = (FermatTextView) itemView.findViewById(R.id.cbw_identity_alias);
    }

    public void bind(ActorIdentity data, boolean selected) {
        identityAlias.setText(data.getAlias());

        int colorRes = selected ? R.color.white : R.color.black;
        identityAlias.setTextColor(getColor(colorRes));

        BitmapWorkerTask bitmapWorker = new BitmapWorkerTask(identityImage, res, R.drawable.person, false);
        bitmapWorker.execute(data.getProfileImage());
    }

    private int getColor(int colorRes) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return res.getColor(colorRes);
        return res.getColor(colorRes, null);
    }
}
