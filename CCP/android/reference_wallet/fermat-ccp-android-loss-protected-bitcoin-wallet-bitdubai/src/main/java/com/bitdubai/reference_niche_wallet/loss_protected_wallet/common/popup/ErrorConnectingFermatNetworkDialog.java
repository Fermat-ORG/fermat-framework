package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;

import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;


/**
 * Created by Jose Manuel De Sousa Dos Santos on 2015.12.03
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ErrorConnectingFermatNetworkDialog extends FermatDialog<ReferenceAppFermatSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * onClick listeners
     */
    private View.OnClickListener leftClick;
    private View.OnClickListener rightClick;
    /**
     * Positive and negative button option text
     */
    private CharSequence leftButton;
    private CharSequence rightButton;
    /**
     * UI components
     */
    private FermatTextView mDescription;
    private CharSequence description;

    public ErrorConnectingFermatNetworkDialog(final Activity activity,
                                              final ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession,
                                              final SubAppResourcesProviderManager subAppResources) {

        super(activity, referenceWalletSession, subAppResources);

    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FermatTextView positiveButtonView = (FermatTextView) findViewById(R.id.positive_button);
        FermatTextView negativeButtonView = (FermatTextView) findViewById(R.id.negative_button);
        mDescription = (FermatTextView) findViewById(R.id.description);
        positiveButtonView.setOnClickListener(rightClick);
        negativeButtonView.setOnClickListener(leftClick);
        mDescription.setText(description != null ? description : "");
        negativeButtonView.setText(leftButton != null ? leftButton : "");
        positiveButtonView.setText(rightButton != null ? rightButton : "");
        negativeButtonView.setOnClickListener(leftClick);
        positiveButtonView.setOnClickListener(rightClick);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_error_conecting_fermat_network;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.positive_button) {
            dismiss();
        }
        if (i == R.id.negative_button) {
            dismiss();
        }
    }


    /**
     * Set positive button listener and text
     *
     * @param text    CharSequence
     * @param onClick View.OnClickListener
     */
    public void setLeftButton(CharSequence text, View.OnClickListener onClick) {
        leftClick = onClick;
        leftButton = text;
    }

    /**
     * Set negative button listener and text
     *
     * @param text    CharSequence
     * @param onClick View.OnClickListener
     */
    public void setRightButton(CharSequence text, View.OnClickListener onClick) {
        rightClick = onClick;
        rightButton = text;
    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }
}