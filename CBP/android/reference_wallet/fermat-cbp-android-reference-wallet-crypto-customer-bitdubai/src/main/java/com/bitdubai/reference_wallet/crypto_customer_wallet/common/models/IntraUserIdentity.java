package com.bitdubai.reference_wallet.crypto_customer_wallet.common.models;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ClauseType;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.common.exceptions.CantSendNegotiationException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ClauseInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.dialogs.IdentityDialog;

import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * Created by Yordin Alayn on 03.08.16.
 */
public class IntraUserIdentity {

    private final CryptoCustomerWalletModuleManager moduleManager;
    private final ErrorManager                      errorManager;
    private final String                            TAG;
    private final Activity                          activity;

    public IntraUserIdentity(
            CryptoCustomerWalletModuleManager   moduleManager,
            ErrorManager                        errorManager,
            String                              TAG,
            Activity                            activity
    ){

        this.moduleManager  = moduleManager;
        this.errorManager   = errorManager;
        this.TAG            = TAG;
        this.activity       = activity;

    }

    public boolean isCreateIdentityIntraUser(Map<ClauseType, ClauseInformation> clauses) throws CantSendNegotiationException {

        String customerCurrency = clauses.get(ClauseType.CUSTOMER_CURRENCY).getValue();
        String brokerCurrency = clauses.get(ClauseType.BROKER_CURRENCY).getValue();
        String currencyBTC = "BTC";

        if (customerCurrency != null) {
            if (currencyBTC.equals(customerCurrency))
                return moduleManager.isCreateIdentityIntraUser();
        }

        if (brokerCurrency != null) {
            if (currencyBTC.equals(brokerCurrency))
                return moduleManager.isCreateIdentityIntraUser();
        }

        return true;
    }

    public void dialogCreateIdentityIntraUser(ReferenceAppFermatSession<CryptoCustomerWalletModuleManager> fermatSession, ResourceProviderManager resources){

        IdentityDialog identityDialog = new IdentityDialog(activity, fermatSession, resources);
        identityDialog.setAcceptBtnListener(new IdentityDialog.OnClickAcceptListener() {
            @Override
            public void onClick(String newValue) {
                try {
                    if (newValue.isEmpty())
                        Toast.makeText(activity, "User Name Can Not be Empty.", Toast.LENGTH_LONG).show();
                    else
                        createIdentityIntraUser(newValue);
                } catch (CantSendNegotiationException e) {
                    if (errorManager != null)
                        errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    else
                        Log.e(TAG, e.getMessage(), e);
                }
            }
        });

        identityDialog.configure(
                R.string.ccw_identity_dialog_title_btc,
                R.string.ccw_identity_dialog_description_btc,
                R.string.ccw_identity_dialog_description2_btc);

        identityDialog.show();

    }


    private void createIdentityIntraUser(String alias) throws CantSendNegotiationException{

        byte[] profileImage =  convertImage(R.drawable.ic_profile_male);
        moduleManager.createIdentityIntraUser(alias, profileImage);

    }

    private byte[] convertImage(int resImage){
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), resImage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }

}
