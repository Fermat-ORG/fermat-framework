package com.bitdubai.reference_wallet.crypto_broker_wallet.common.navigationDrawer;

import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

/**
 * Created by nelson on 19/12/15.
 */
public class NavTitleFooterViewHolder extends FermatViewHolder {
    private FermatTextView navFooterTitle;


    protected NavTitleFooterViewHolder(View itemView) {
        super(itemView);
        navFooterTitle = (FermatTextView) itemView.findViewById(R.id.cbw_nav_footer_title);
    }

    public FermatTextView getNavFooterTitle() {
        return navFooterTitle;
    }
}
