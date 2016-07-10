package com.bitdubai.sub_app.crypto_customer_identity.util;

import android.content.Context;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;


/**
 * Created by nelsonalfo on 18/06/16.
 */
public class CreateIdentityWorker extends FermatWorker {
    private static final int SUCCESS = 1;

    private final String alias;
    private GeoFrequency frequency;
    private int accuracy;
    private byte[] imgByteArray;
    private CryptoCustomerIdentityModuleManager moduleManager;
    CryptoCustomerIdentityInformation identityInformation;

    public CreateIdentityWorker(Context context, CryptoCustomerIdentityModuleManager moduleManager, FermatWorkerCallBack callBack,
                                String alias, byte[] imgByteArray, int accuracy, GeoFrequency frequency) {
        super(context, callBack);

        this.moduleManager = moduleManager;
        this.alias = alias;
        this.frequency = frequency;
        this.accuracy = accuracy;
        this.imgByteArray = imgByteArray;
    }

    @Override
    protected Object doInBackground() throws Exception {
        identityInformation = moduleManager.createCryptoCustomerIdentity(alias, imgByteArray, accuracy, frequency);
        moduleManager.publishCryptoCustomerIdentity(identityInformation.getPublicKey());

        return SUCCESS;
    }
}