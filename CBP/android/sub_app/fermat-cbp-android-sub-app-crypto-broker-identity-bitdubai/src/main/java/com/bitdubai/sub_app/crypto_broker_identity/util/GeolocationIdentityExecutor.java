package com.bitdubai.sub_app.crypto_broker_identity.util;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_cbp_api.all_definition.enums.Frecuency;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantUpdateBrokerIdentityException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;


/**
 * Created by Lozadaa 23/04/2016.
 */

public class GeolocationIdentityExecutor {
    public static final int SUCCESS = 1;

    private final ReferenceAppFermatSession<CryptoBrokerIdentityModuleManager> session;
    private CryptoBrokerIdentityInformation identity;
    private final Frecuency frequencyData;
    private final long accuracyData;

    public GeolocationIdentityExecutor(ReferenceAppFermatSession<CryptoBrokerIdentityModuleManager> session, CryptoBrokerIdentityInformation identity, Frecuency frequencyData, long accuracyData) {
        this.session = session;
        this.identity = identity;

        this.frequencyData = frequencyData;
        this.accuracyData = accuracyData;
    }

    public int execute() {

        try {

            session.getModuleManager().updateCryptoBrokerIdentity(identity);
        } catch (CantUpdateBrokerIdentityException e) {
            e.printStackTrace();
        }


        return SUCCESS;
    }


}