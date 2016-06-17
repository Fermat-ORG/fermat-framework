package com.bitdubai.sub_app.crypto_customer_identity.util;

import android.app.Activity;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;


/**
 * Created by angel on 20/1/16.
 */

public class EditCustomerIdentityWorker extends FermatWorker {
    public static final int SUCCESS = 1;
    public static final int INVALID_ENTRY_DATA = 4;

    private CryptoCustomerIdentityModuleManager moduleManager;
    private CryptoCustomerIdentityInformation identity;

    public EditCustomerIdentityWorker(Activity context,
                                      ReferenceAppFermatSession<CryptoCustomerIdentityModuleManager> session,
                                      CryptoCustomerIdentityInformation identity,
                                      FermatWorkerCallBack callBack) {
        super(context, callBack);

        this.identity = identity;

        if (session != null) {
            this.moduleManager = session.getModuleManager();
        }
    }

    @Override
    protected Object doInBackground() throws Exception {

        if (identity == null) {
            return INVALID_ENTRY_DATA;
        } else {
            //TODO:NELSON Hay que pasarle los valores correcto al accuracy y la frecuencia al objeto identity
            moduleManager.updateCryptoCustomerIdentity(identity);
            return SUCCESS;
        }
    }
}