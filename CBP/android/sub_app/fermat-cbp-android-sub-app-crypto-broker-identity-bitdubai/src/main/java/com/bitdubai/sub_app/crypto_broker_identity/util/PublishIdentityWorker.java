package com.bitdubai.sub_app.crypto_broker_identity.util;

import android.app.Activity;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;

/**
 * Created by nelson on 14/10/15.
 */
public class PublishIdentityWorker extends FermatWorker {

    public static final int SUCCESS = 1;
    public static final int INVALID_ENTRY_DATA = 4;

    private CryptoBrokerIdentityModuleManager moduleManager;

    private String identityPublicKey;
    private boolean wantToPublish;

    public PublishIdentityWorker(final Activity                          context          ,
                                 final CryptoBrokerIdentityModuleManager moduleManager    ,
                                 final String                            identityPublicKey,
                                 final boolean                           wantToPublish    ,
                                 final FermatWorkerCallBack              callBack         ) {

        super(context, callBack);

        this.wantToPublish     = wantToPublish    ;
        this.identityPublicKey = identityPublicKey;
        this.moduleManager     = moduleManager    ;
    }

    @Override
    protected Object doInBackground() throws Exception {

        if (identityPublicKey == null)
            return INVALID_ENTRY_DATA;

        if (wantToPublish)
            moduleManager.publishIdentity(identityPublicKey);
        else
            moduleManager.hideIdentity(identityPublicKey);

        return SUCCESS;
    }
}
