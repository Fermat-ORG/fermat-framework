package org.fermat.fermat_dap_android_wallet_asset_user.v2.common.dialogs;

import android.content.Context;
import android.view.View;
import android.view.Window;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 3/3/16.
 */
public class ConfirmDialog extends FermatDialog<ReferenceAppFermatSession, ResourceProviderManager> implements
        View.OnClickListener {

    public ConfirmDialog(Context activity, ReferenceAppFermatSession referenceAppFermatSession, ResourceProviderManager resources) {
        super(activity, referenceAppFermatSession, resources);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dav2_wallet_asset_user_confirm_dialog;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.agreeButton) {

            dismiss();
        } else if (view.getId() == R.id.disagreeButton) {
            dismiss();
        }
    }
}
