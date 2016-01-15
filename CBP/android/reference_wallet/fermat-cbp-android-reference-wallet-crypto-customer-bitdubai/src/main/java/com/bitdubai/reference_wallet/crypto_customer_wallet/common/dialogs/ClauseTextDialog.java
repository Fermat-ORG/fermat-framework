package com.bitdubai.reference_wallet.crypto_customer_wallet.common.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;


/**
 * Created by nelson on 14/01/16.
 */
public class ClauseTextDialog extends FermatDialog<CryptoCustomerWalletSession, ResourceProviderManager>
        implements View.OnClickListener {

    private FermatEditText editTextView;
    private int titleStringResource;
    private int hintStringResource;
    private String editTextValue;

    private OnClickAcceptListener acceptBtnListener;

    public interface OnClickAcceptListener {
        void onClick(String editTextValue);
    }


    public ClauseTextDialog(Activity activity, CryptoCustomerWalletSession fermatSession, ResourceProviderManager resources) {
        super(activity, fermatSession, resources);

        hintStringResource = R.string.hint;
        titleStringResource = R.string.title;
        editTextValue = null;
    }

    public void configure(int titleRes, int hintRes) {
        titleStringResource = titleRes;
        hintStringResource = hintRes;
    }

    public void setAcceptBtnListener(OnClickAcceptListener acceptBtnListener) {
        this.acceptBtnListener = acceptBtnListener;
    }

    public void setEditTextValue(String editTextValue) {
        this.editTextValue = editTextValue;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ccw_text_dialog_cancel_btn) {
            dismiss();

        } else if (view.getId() == R.id.ccw_text_dialog_accept_btn) {
            if (acceptBtnListener != null)
                acceptBtnListener.onClick(editTextView.getText().toString());
            dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FermatTextView titleTextView = (FermatTextView) findViewById(R.id.ccw_text_dialog_title);
        titleTextView.setText(titleStringResource);
        FermatButton acceptBtn = (FermatButton) findViewById(R.id.ccw_text_dialog_accept_btn);
        acceptBtn.setOnClickListener(this);
        FermatButton cancelBtn = (FermatButton) findViewById(R.id.ccw_text_dialog_cancel_btn);
        cancelBtn.setOnClickListener(this);
        editTextView = (FermatEditText) findViewById(R.id.ccw_text_dialog_edit_text);
        editTextView.setHint(hintStringResource);
        if (editTextValue != null)
            editTextView.setText(editTextValue);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.ccw_clause_text_dialog;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }
}
