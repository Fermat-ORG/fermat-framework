package com.bitdubai.sub_app.crypto_customer_identity.util;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.exceptions.CouldNotCreateCryptoCustomerException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.sub_app.crypto_customer_identity.session.CryptoCustomerIdentitySubAppSession;

/**
 * Created by nelson on 19/10/15.
 */
public class CreateCustomerIdentityExecutor {
    public static final int EXCEPTION_THROWN = 3;
    public static final int SUCCESS = 1;
    public static final int INVALID_ENTRY_DATA = 2;

    private byte[] imageInBytes;
    private String identityName;

    private CryptoCustomerIdentityModuleManager moduleManager;
    private ErrorManager errorManager;
    private CryptoCustomerIdentityInformation identity;

    public CreateCustomerIdentityExecutor(byte[] imageInBytes, String identityName) {
        this.imageInBytes = imageInBytes;
        this.identityName = identityName;
    }

    public CreateCustomerIdentityExecutor(CryptoCustomerIdentityModuleManager moduleManager, ErrorManager errorManager, byte[] imageInBytes, String identityName) {
        this(imageInBytes, identityName);

        this.moduleManager = moduleManager;
        this.errorManager = errorManager;
        identity = null;
    }

    public CreateCustomerIdentityExecutor(FermatSession session, String identityName, byte[] imageInBytes) {
        this(imageInBytes, identityName);
        identity = null;

        if (session != null) {
            CryptoCustomerIdentitySubAppSession subAppSession = (CryptoCustomerIdentitySubAppSession) session;
            this.moduleManager = subAppSession.getModuleManager();
            this.errorManager = subAppSession.getErrorManager();
        }
    }

    public int execute() {
        if (entryDataIsInvalid())
            return INVALID_ENTRY_DATA;

        try {
            identity = moduleManager.createCryptoCustomerIdentity(identityName, imageInBytes);

        } catch (CouldNotCreateCryptoCustomerException ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(
                        SubApps.CBP_CRYPTO_BROKER_IDENTITY,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);

            return EXCEPTION_THROWN;
        }

        return SUCCESS;
    }

    public CryptoCustomerIdentityInformation getIdentity() {
        return identity;
    }

    private boolean entryDataIsInvalid() {
        if (moduleManager == null) return true;
        if (imageInBytes == null) return true;
        if (imageInBytes.length == 0) return true;
        if (identityName == null) return true;
        return identityName.isEmpty();
    }
}
