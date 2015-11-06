package com.bitdubai.fermat_core.layer.cbp.wallet_module;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_core.layer.cbp.sub_app_module.crypto_broker_identity.CryptoBrokerIdentitySubsystem;
import com.bitdubai.fermat_core.layer.cbp.wallet_module.crypto_broker.CryptoBrokerWalletModuleSubsystem;

/**
 * Created by jorge on 14-10-2015.
 */
public class CBPWalletModuleLayer implements PlatformLayer {

    private Plugin cryptoBrokerWallet;

    @Override
    public void start() throws CantStartLayerException {
        try{
            CryptoBrokerWalletModuleSubsystem cryptoBrokerWalletSubsystem = new CryptoBrokerWalletModuleSubsystem();

            cryptoBrokerWalletSubsystem.start();

            cryptoBrokerWallet = cryptoBrokerWalletSubsystem.getPlugin();
        } catch (CantStartSubsystemException ex) {
            throw new CantStartLayerException(CantStartLayerException.DEFAULT_MESSAGE, ex, "", "");
        }
    }

    public Plugin getCryptoBrokerWallet() {
        return cryptoBrokerWallet;
    }
}
