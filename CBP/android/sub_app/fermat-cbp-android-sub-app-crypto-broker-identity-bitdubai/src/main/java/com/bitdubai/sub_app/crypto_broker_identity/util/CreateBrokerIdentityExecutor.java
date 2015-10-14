package com.bitdubai.sub_app.crypto_broker_identity.util;

import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CouldNotCreateCryptoBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.sub_app.crypto_broker_identity.common.interfaces.CreateIdentityExecutor;
import com.bitdubai.sub_app.crypto_broker_identity.session.CryptoBrokerIdentitySubAppSession;

/**
 * Created by nelson on 14/10/15.
 */
public class CreateBrokerIdentityExecutor extends CreateIdentityExecutor {
    CryptoBrokerIdentityModuleManager moduleManager;
    ErrorManager errorManager;
    CryptoBrokerIdentityInformation identity;

    public CreateBrokerIdentityExecutor(CryptoBrokerIdentityModuleManager moduleManager,
                                        ErrorManager errorManager,
                                        byte[] imageInBytes,
                                        String identityName) {

        super(imageInBytes, identityName);
        this.moduleManager = moduleManager;
        this.errorManager = errorManager;
        identity = null;
    }

    public CreateBrokerIdentityExecutor(CryptoBrokerIdentitySubAppSession session, byte[] imageInBytes, String identityName) {
        super(imageInBytes, identityName);
        this.moduleManager = session.getModuleManager();
        this.errorManager = session.getErrorManager();
        identity = null;
    }

    @Override
    public int createNewIdentity() {
        if (entryDataIsInvalid())
            return INVALID_ENTRY_DATA;

        try {
            identity = moduleManager.createCryptoBrokerIdentity(identityName, imageInBytes);

        } catch (CouldNotCreateCryptoBrokerException ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(
                        SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);

            return EXCEPTION_THROWN;
        }

        return SUCCESS;
    }

    public CryptoBrokerIdentityInformation getIdentity(){
        return identity;
    }

    private boolean entryDataIsInvalid() {
        if (moduleManager == null) return true;
        if (imageInBytes == null) return true;
        if (identityName == null) return true;
        if (identityName.isEmpty()) return true;
        return false;
    }
}
