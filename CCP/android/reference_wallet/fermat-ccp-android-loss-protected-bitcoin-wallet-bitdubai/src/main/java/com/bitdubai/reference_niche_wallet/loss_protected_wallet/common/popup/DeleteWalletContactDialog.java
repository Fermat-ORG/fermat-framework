package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;

import java.util.UUID;

/**
 * Created by root on 29/08/16.
 */
public class DeleteWalletContactDialog extends Dialog implements View.OnClickListener {


    /**
     * UI components
     */
    //private final CryptoWallet intraUserInformation;
    private final UUID ContactId;
    private final Context activity;
    private final ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession;
    public Dialog d;
    private Button positiveBtn;
    private Button negativeBtn;
    private TextView ContactName;
    private String mContactName;
    private TextView text_message;
    private FermatTextView Title;

    public DeleteWalletContactDialog(Context activity, ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession, UUID ContactId, String mContactName) {
        super(activity);
        this.activity = activity;
        this.referenceWalletSession = referenceWalletSession;

        this.ContactId = ContactId;
        this.mContactName = mContactName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreenComponents();
    }

    private void setUpScreenComponents(){

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.loss_delete_wallet_contact_dialog);

            negativeBtn = (Button) findViewById(R.id.cancel_btn);
            positiveBtn = (Button) findViewById(R.id.accept_btn);

            Title = (FermatTextView) findViewById(R.id.txt_title);
            text_message = (TextView) findViewById(R.id.description_msg);
            ContactName = (FermatTextView) findViewById(R.id.description_msg2);
            ContactName.setText(mContactName);

            Title.setText(R.string.delete_title);
            text_message.setText(R.string.delete_sub_title);

            positiveBtn.setOnClickListener(this);
            negativeBtn.setOnClickListener(this);




        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.accept_btn) {

            try {
                   if (ContactId != null) {
                    referenceWalletSession.getModuleManager().deleteWalletContact(ContactId);
                    referenceWalletSession.setData(SessionConstant.CONTACT_DELETED, Boolean.TRUE);
                    Toast.makeText(getContext(), R.string.deleted_toast, Toast.LENGTH_SHORT).show();
                } else {
                  //  super.toastDefaultError();
                    referenceWalletSession.setData(SessionConstant.CONTACT_DELETED, Boolean.FALSE);
                }
                this.dismiss();
            } catch (final Exception e) {

                referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                this.dismiss();
               // super.toastDefaultError();
            }


        } else if (i == R.id.cancel_btn) {
            this.dismiss();
            referenceWalletSession.setData(SessionConstant.CONTACT_DELETED, Boolean.FALSE);
        }
    }
}
