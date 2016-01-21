package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.sessions.AssetIssuerSession;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 1/15/16.
 */
public class DistributeAcceptDialog extends FermatDialog<AssetIssuerSession, ResourceProviderManager> implements
        View.OnClickListener {

    private FermatTextView title;
    private FermatTextView description;
    private FermatButton yesBtn;
    private FermatButton noBtn;

    private OnClickAcceptListener btnListener;

    public interface OnClickAcceptListener {
        void onClick();
    }

    public DistributeAcceptDialog(Activity activity, AssetIssuerSession fermatSession, ResourceProviderManager resources) {
        super(activity, fermatSession, resources);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUI();
    }

    private void setupUI() {
        title = (FermatTextView) findViewById(R.id.confirmDialogTitle);
        description = (FermatTextView) findViewById(R.id.confirmDialogContentText);
        yesBtn = (FermatButton) findViewById(R.id.confirmDialogYesButton);
        noBtn = (FermatButton) findViewById(R.id.confirmDialogNoButton);

        yesBtn.setOnClickListener(this);
        noBtn.setOnClickListener(this);

        title.setText("Confirm");
        description.setText("Are you sure that the entered information is correct? This action is irreversible");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dap_wallet_asset_issuer_asset_delivery_confirm;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.confirmDialogYesButton) {
            if (btnListener != null) {
                btnListener.onClick();
            }
            dismiss();
        } else if (view.getId() == R.id.confirmDialogNoButton) {
            dismiss();
        }
    }

    public void setYesBtnListener(OnClickAcceptListener btnListener) {
        this.btnListener = btnListener;
    }
}
