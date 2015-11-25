package com.bitdubai.sub_app.crypto_broker_identity.util;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantPublishCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.exceptions.CantHideCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.sub_app.crypto_broker_identity.session.CryptoBrokerIdentitySubAppSession;

import static com.bitdubai.sub_app.crypto_broker_identity.session.CryptoBrokerIdentitySubAppSession.IDENTITY_INFO;

/**
 * Created by nelson on 14/10/15.
 */
public class PublishIdentityExecutor {
    private static final String TAG = "PublishIdentityExecutor";

    public static final int SUCCESS = 1;
    public static final int DATA_NOT_CHANGED = 2;
    public static final int EXCEPTION_THROWN = 3;
    public static final int INVALID_ENTRY_DATA = 4;

    private CryptoBrokerIdentityModuleManager moduleManager;
    private ErrorManager errorManager;

    CryptoBrokerIdentityInformation identityInfo;
    boolean wantToPublish;

    public PublishIdentityExecutor(SubAppsSession session, boolean wantToPublish) {
        this.wantToPublish = wantToPublish;

        if (session != null) {
            CryptoBrokerIdentitySubAppSession subAppSession = (CryptoBrokerIdentitySubAppSession) session;
            identityInfo = (CryptoBrokerIdentityInformation) subAppSession.getData(IDENTITY_INFO);
            this.moduleManager = subAppSession.getModuleManager();
            this.errorManager = subAppSession.getErrorManager();
        }
    }

    public int execute() {
        if (identityInfo == null)
            return INVALID_ENTRY_DATA;

        String publicKey = identityInfo.getPublicKey();
        boolean valueChanged = (wantToPublish != identityInfo.isPublished());

        if (valueChanged) {
            if (wantToPublish) {
                return runPublish(publicKey);
            } else {
                return runUnPublish(publicKey);
            }
        }

        return DATA_NOT_CHANGED;
    }

    private int runPublish(String publicKey) {
        try {
            moduleManager.publishIdentity(publicKey);
            return SUCCESS;

        } catch (CantPublishCryptoBrokerException ex) {
            if (errorManager != null){
                errorManager.reportUnexpectedSubAppException(
                        SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }

            return EXCEPTION_THROWN;
        }
    }

    private int runUnPublish(String publicKey) {
        try {
            moduleManager.hideIdentity(publicKey);
            return SUCCESS;

        } catch (CantHideCryptoBrokerException ex) {
            if(errorManager != null){
                errorManager.reportUnexpectedSubAppException(
                        SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }

            return EXCEPTION_THROWN;
        }
    }
}
