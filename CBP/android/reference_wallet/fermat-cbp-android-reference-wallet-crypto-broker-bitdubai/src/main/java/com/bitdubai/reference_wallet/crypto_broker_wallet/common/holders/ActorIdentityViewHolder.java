package com.bitdubai.reference_wallet.crypto_broker_wallet.common.holders;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_android_api.ui.util.BitmapWorkerTask;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by Nelson Ramirez
 * @since 22/12/15.
 */
public class ActorIdentityViewHolder extends FermatViewHolder {
    ImageView identityImage;
    FermatTextView identityAlias;

    public ActorIdentityViewHolder(View itemView) {
        super(itemView);
    }

    public void bind(ActorIdentity data) {
        identityAlias.setText(data.getAlias());

        BitmapWorkerTask bitmapWorker = new BitmapWorkerTask(identityImage, identityImage.getResources(), R.drawable.person, false);
        bitmapWorker.execute(data.getProfileImage());
    }
}
