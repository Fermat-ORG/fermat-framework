package com.bitdubai.fermat_core.layer.cbp.sub_app_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.cbp.sub_app_module.crypto_broker_identity.CryptoBrokerIdentitySubsystem;
import com.bitdubai.fermat_core.layer.cbp.sub_app_module.crypto_customer_identity.CryptoCustomerIdentitySubsystem;

/**
 * Created by jorge on 14-10-2015.
 */
public class CBPSubAppModuleLayer implements PlatformLayer {

    private Plugin cryptoBrokerIdentity;
    private Plugin cryptoCustomerIdentity;

    @Override
    public void start() throws CantStartLayerException {
        try{
            CryptoBrokerIdentitySubsystem cryptoBrokerIdentitySubsystem = new CryptoBrokerIdentitySubsystem();
            CryptoCustomerIdentitySubsystem cryptoCustomerIdentitySubsystem = new CryptoCustomerIdentitySubsystem();

            cryptoBrokerIdentitySubsystem.start();
            cryptoCustomerIdentitySubsystem.start();

            cryptoBrokerIdentity = cryptoBrokerIdentitySubsystem.getPlugin();
            cryptoCustomerIdentity = cryptoCustomerIdentitySubsystem.getPlugin();
        } catch (CantStartSubsystemException ex) {
            throw new CantStartLayerException(CantStartLayerException.DEFAULT_MESSAGE, ex, "", "");
        }
    }

    public Plugin getCryptoBrokerIdentity() {
        return cryptoBrokerIdentity;
    }

    public Plugin getCryptoCustomerIdentity() {
        return cryptoCustomerIdentity;
    }
}
