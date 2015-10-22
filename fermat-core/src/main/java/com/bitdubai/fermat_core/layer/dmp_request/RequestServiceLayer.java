package com.bitdubai.fermat_core.layer.dmp_request;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkSubsystem;
import com.bitdubai.fermat_core.layer.dmp_request.money.MoneySubsystem;

/**
 * Created by natalia on 03/08/15.
 */
public class RequestServiceLayer implements PlatformLayer {

    private Plugin mRequestMoneyPlugin;

    public Plugin getMoney() {
        return mRequestMoneyPlugin;

    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         *  I start request money plugin
         */

        NetworkSubsystem moneySubsystem = new MoneySubsystem();

        try {
            moneySubsystem.start();
            mRequestMoneyPlugin = ((MoneySubsystem) moneySubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            //TODO: Change exceptions.
            throw new CantStartLayerException();

        }
    }
}
