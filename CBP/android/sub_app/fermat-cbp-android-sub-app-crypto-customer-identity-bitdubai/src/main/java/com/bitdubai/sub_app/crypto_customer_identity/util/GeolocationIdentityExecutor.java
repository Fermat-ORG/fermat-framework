package com.bitdubai.sub_app.crypto_customer_identity.util;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateCustomerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_identity.interfaces.CryptoCustomerIdentityModuleManager;
import com.bitdubai.fermat_cht_api.all_definition.enums.Frecuency;

/**
 * Created by Lozadaa 23/04/2016.
 */

public class GeolocationIdentityExecutor {
    public static final int SUCCESS = 1;

    private CryptoCustomerIdentityModuleManager moduleManager;
    private CryptoCustomerIdentityInformation identityInfo;
    private CryptoCustomerIdentityInformation identity;


    /**
     * <code>GelocationidentityExecutor</code> this is constructor
     * @param session
     *
     */

    public GeolocationIdentityExecutor(ReferenceAppFermatSession<CryptoCustomerIdentityModuleManager> session, CryptoCustomerIdentityInformation identity) {
        this.identity = identity;

        if (session != null) {
            identityInfo = (CryptoCustomerIdentityInformation) session.getData(FragmentsCommons.IDENTITY_INFO);
            this.moduleManager = session.getModuleManager();
        }
    }

    public int execute() {


        try {
            moduleManager.updateCryptoCustomerIdentity(identity);
        } catch (CantUpdateCustomerIdentityException e) {
            e.printStackTrace();
        }


        return SUCCESS;
    }


}