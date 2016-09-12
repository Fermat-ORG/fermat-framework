package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.SessionConstant;

import java.util.UUID;

/**
 * Created by root on 29/08/16.
 */
public class DeleteWalletContactDialog extends FermatDialog<ReferenceAppFermatSession<CryptoWallet>,ResourceProviderManager> implements View.OnClickListener {

    /**
     * UI components
     */
    //private final CryptoWallet intraUserInformation;
    private final UUID ContactId;

    private FermatButton positiveBtn;
    private FermatButton negativeBtn;
    private TextView ContactName;
    private String mContactName;

    public DeleteWalletContactDialog(Context activity, ReferenceAppFermatSession<CryptoWallet> fermatSession, ResourceProviderManager resources, UUID ContactId, String mContactName) {
        super(activity, fermatSession, resources);
        this.ContactId = ContactId;
        this.mContactName = mContactName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        positiveBtn = (FermatButton)   findViewById(R.id.positive_button);
        negativeBtn = (FermatButton)   findViewById(R.id.negative_button);

        ContactName = (TextView)       findViewById(R.id.contact_name);

        ContactName.setText(mContactName);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.delete_wallet_contact_dialog;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.positive_button) {

            try {
                if (ContactId != null) {
                    getSession().getModuleManager().deleteWalletContact(ContactId);
                    getSession().setData(SessionConstant.CONTACT_DELETED, Boolean.TRUE);
                    Toast.makeText(getContext(), R.string.deleted_toast, Toast.LENGTH_SHORT).show();
                } else {
                    super.toastDefaultError();
                    getSession().setData(SessionConstant.CONTACT_DELETED, Boolean.FALSE);
                }
                dismiss();
            } catch (final Exception e) {

                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            dismiss();

        } else if (i == R.id.negative_button) {
            dismiss();
            getSession().setData(SessionConstant.CONTACT_DELETED, Boolean.FALSE);
        }
    }
}
