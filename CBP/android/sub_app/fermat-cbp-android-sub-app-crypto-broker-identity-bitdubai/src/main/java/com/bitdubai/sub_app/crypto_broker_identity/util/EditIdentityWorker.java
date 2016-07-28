package com.bitdubai.sub_app.crypto_broker_identity.util;

import android.app.Activity;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;


/**
 * Created by angel on 20/1/16.
 */
public class EditIdentityWorker extends FermatWorker {
    public static final int SUCCESS = 1;

    private CryptoBrokerIdentityModuleManager moduleManager;
    private CryptoBrokerIdentityInformation identityInfo;
    private CryptoBrokerIdentityInformation identity;

    public EditIdentityWorker(Activity context, ReferenceAppFermatSession<CryptoBrokerIdentityModuleManager> session, CryptoBrokerIdentityInformation identity, FermatWorkerCallBack callBack) {
        super(context, callBack);

        this.identity = identity;

        if (session != null) {
            identityInfo = (CryptoBrokerIdentityInformation) session.getData(FragmentsCommons.IDENTITY_INFO);
            this.moduleManager = session.getModuleManager();
        }
    }

    @Override
    protected Object doInBackground() throws Exception {


        boolean valueChanged = (identity.isPublished() != identityInfo.isPublished());

        moduleManager.updateCryptoBrokerIdentity(identity);

        if (valueChanged) {
            if (identity.isPublished()) {

                System.out.println("VLZ: Publicando");

                moduleManager.unHideIdentity(identity.getPublicKey());
            } else {

                System.out.println("VLZ: Ocultando");

                moduleManager.hideIdentity(identity.getPublicKey());
            }
        }
        return SUCCESS;
    }
}