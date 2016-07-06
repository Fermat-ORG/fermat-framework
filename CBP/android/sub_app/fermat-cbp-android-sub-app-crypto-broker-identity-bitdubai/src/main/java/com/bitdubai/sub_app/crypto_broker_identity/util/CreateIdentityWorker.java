package com.bitdubai.sub_app.crypto_broker_identity.util;

import android.content.Context;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.all_definition.enums.GeoFrequency;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;


/**
 * Created by nelsonalfo on 18/06/16.
 */
public class CreateIdentityWorker extends FermatWorker {
    private static final int SUCCESS = 1;

    private final String alias;
    private GeoFrequency frecuency;
    private int accuracy;
    private byte[] imgByteArray;
    private CryptoBrokerIdentityModuleManager moduleManager;
    CryptoBrokerIdentityInformation identityInformation;

    public CreateIdentityWorker(Context context, CryptoBrokerIdentityModuleManager moduleManager, FermatWorkerCallBack callBack,
                                String alias, byte[] imgByteArray, int accuracy, GeoFrequency frecuency) {
        super(context, callBack);

        this.moduleManager = moduleManager;
        this.alias = alias;
        this.frecuency = frecuency;
        this.accuracy = accuracy;
        this.imgByteArray = imgByteArray;
    }

    @Override
    protected Object doInBackground() throws Exception {
        identityInformation = moduleManager.createCryptoBrokerIdentity(alias, imgByteArray, accuracy, frecuency);
        moduleManager.publishIdentity(identityInformation.getPublicKey());

        return SUCCESS;
    }
}