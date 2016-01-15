package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.dialogs;

import android.app.Activity;
import android.view.View;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 1/15/16.
 */
public class DistributeAcceptDialog extends FermatDialog<AssetIssuerSession, ResourceProviderManager> implements
        View.OnClickListener {

    private FermatTextView title;
    private FermatTextView description;
    private FermatTextView userName;
    private FermatButton positiveBtn;
    private FermatButton negativeBtn;

    public DistributeAcceptDialog(Activity activity, AssetIssuerSession fermatSession, ResourceProviderManager resources) {
        super(activity, fermatSession, resources);
    }

    @Override
    protected int setLayoutId() {
        return 0;
    }

    @Override
    protected int setWindowFeature() {
        return 0;
    }

    @Override
    public void onClick(View view) {

    }
}
