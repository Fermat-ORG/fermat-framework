package com.bitdubai.sub_app.crypto_broker_identity.util;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.utils.CryptoBrokerIdentityInformationImpl;
import com.bitdubai.fermat_cht_api.all_definition.enums.Frecuency;
import com.bitdubai.fermat_cht_api.layer.identity.exceptions.CantUpdateChatIdentityException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.identity.ChatIdentityModuleManager;

/**
 * Created by Lozadaa 23/04/2016.
 */

public class GeolocationIdentityExecutor {
    public static final int SUCCESS = 1;

    private ErrorManager errorManager;
    private CryptoBrokerIdentityModuleManager moduleManager;
    private CryptoBrokerIdentityInformation identityInfo;
    private CryptoBrokerIdentityInformation identity;

    /**
     * <code>GelocationidentityExecutor</code> this is constructor
     * @param session
     *
     */

    public GeolocationIdentityExecutor(ReferenceAppFermatSession<CryptoBrokerIdentityModuleManager> session, CryptoBrokerIdentityInformation identity) {
        this.identity = identity;

        if (session != null) {
            identityInfo = (CryptoBrokerIdentityInformation) session.getData(FragmentsCommons.IDENTITY_INFO);
            this.moduleManager = session.getModuleManager();
        }
    }

    public int execute() {

        try {
            moduleManager.updateCryptoBrokerIdentity(identity);
        } catch (CantUpdateBrokerIdentityException e) {
            e.printStackTrace();
        }


        return SUCCESS;
    }


}