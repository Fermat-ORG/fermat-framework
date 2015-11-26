package com.bitdubai.sub_app.crypto_broker_identity.util;

import android.app.Activity;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantHideCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CryptoBrokerNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.sub_app.crypto_broker_identity.session.CryptoBrokerIdentitySubAppSession;

import java.util.concurrent.ExecutorService;

import static com.bitdubai.sub_app.crypto_broker_identity.session.CryptoBrokerIdentitySubAppSession.IDENTITY_INFO;

/**
 * Created by nelson on 14/10/15.
 */
public class PublishIdentityWorker extends FermatWorker {
    public static final int SUCCESS = 1;
    public static final int DATA_NOT_CHANGED = 2;
    public static final int INVALID_ENTRY_DATA = 4;

    private CryptoBrokerIdentityModuleManager moduleManager;

    private CryptoBrokerIdentityInformation identityInfo;
    private boolean wantToPublish;

    public PublishIdentityWorker(Activity context, SubAppsSession session, boolean wantToPublish, FermatWorkerCallBack callBack) {
        super(context, callBack);

        this.wantToPublish = wantToPublish;

        if (session != null) {
            CryptoBrokerIdentitySubAppSession subAppSession = (CryptoBrokerIdentitySubAppSession) session;
            identityInfo = (CryptoBrokerIdentityInformation) subAppSession.getData(IDENTITY_INFO);
            this.moduleManager = subAppSession.getModuleManager();
        }
    }

    @Override
    protected Object doInBackground() throws Exception {
        if (identityInfo == null)
            return INVALID_ENTRY_DATA;

        String publicKey = identityInfo.getPublicKey();
        boolean valueChanged = (wantToPublish != identityInfo.isPublished());

        if (valueChanged) {
            if (wantToPublish)
                moduleManager.publishIdentity(publicKey);
            else
                moduleManager.hideIdentity(publicKey);
            return SUCCESS;
        }
        return DATA_NOT_CHANGED;
    }
}
