package com.bitdubai.fermat_core.layer.dmp_network_service;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkSubsystem;
import com.bitdubai.fermat_core.layer.dmp_network_service.bank_notes.BankNotesSubsystem;
import com.bitdubai.fermat_core.layer.dmp_network_service.money.MoneySubsystem;
import com.bitdubai.fermat_core.layer.dmp_network_service.template.TemplateSubsystem;

/**
 * Created by ciencias on 03.01.15.
 */
public class NetworkServiceLayer implements PlatformLayer {

    private Plugin mBankNotesPlugin;

    private Plugin mMoney;

    private Plugin mUserPlugin;

    private Plugin mTemplate;


    public Plugin getBankNotesPlugin() {
        return mBankNotesPlugin;
    }

    public Plugin getMoney() {
        return mMoney;
    }

    public Plugin getUserPlugin() {
        return mUserPlugin;
    }

    public Plugin getTemplate(){
        return mTemplate;
    }


    @Override
    public void start() throws CantStartLayerException {
        /**
         * Let's try to start the Bank Notes subsystem.
         */

        NetworkSubsystem bankNotesSubsytem = new BankNotesSubsystem();

        try {
            bankNotesSubsytem.start();
            mBankNotesPlugin = (bankNotesSubsytem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }


        NetworkSubsystem moneySubsystem = new MoneySubsystem();

        try {
            moneySubsystem.start();
            mMoney = (moneySubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            throw new CantStartLayerException();
        }

        /**
         * Let's try to start the template subsystem.
         */

        NetworkSubsystem template = new TemplateSubsystem();

        try {

            template.start();
            mTemplate = (template).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }

    }
}
