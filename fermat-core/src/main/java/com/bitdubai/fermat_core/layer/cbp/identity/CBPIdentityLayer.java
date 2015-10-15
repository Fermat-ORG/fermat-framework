package com.bitdubai.fermat_core.layer.cbp.identity;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.cbp.identity.crypto_broker.CryptoBrokerIdentitySubsystem;
import com.bitdubai.fermat_core.layer.cbp.identity.crypto_customer.CryptoCustomerIdentitySubsystem;

/**
 * Created by jorge on 13-10-2015.
 */
public class CBPIdentityLayer implements PlatformLayer {

    private Plugin cryptoBrokerIdentity;
    private Plugin cryptoCustomerIdentity;

    @Override
    public void start() throws CantStartLayerException {
        try{
            CryptoBrokerIdentitySubsystem cryptoBrokerSubsystem = new CryptoBrokerIdentitySubsystem();
            CryptoCustomerIdentitySubsystem cryptoCustomerSubsystem = new CryptoCustomerIdentitySubsystem();

            cryptoBrokerSubsystem.start();
            cryptoCustomerSubsystem.start();

            cryptoBrokerIdentity = cryptoBrokerSubsystem.getPlugin();
            cryptoCustomerIdentity = cryptoCustomerSubsystem.getPlugin();

        } catch(CantStartSubsystemException ex){
            throw new CantStartLayerException(CantStartLayerException.DEFAULT_MESSAGE, ex, "CBP Identity Layer", "Check the cause");
        }
    }

    public Plugin getCryptoBrokerIdentity(){
        return cryptoBrokerIdentity;
    }

    public Plugin getCryptoCustomerIdentity(){
        return cryptoCustomerIdentity;
    }

}