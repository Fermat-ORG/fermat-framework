package com.bitdubai.fermat_core.layer.ccp_network_service;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.ccp_network_service.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.ccp_network_service.NetworkSubsystem;
import com.bitdubai.fermat_core.layer.ccp_network_service.bank_notes.BankNotesSubsystem;
import com.bitdubai.fermat_core.layer.ccp_network_service.intra_user.IntraUserSubsystem;
import com.bitdubai.fermat_core.layer.ccp_network_service.money.MoneySubsystem;
import com.bitdubai.fermat_core.layer.ccp_network_service.template.TemplateSubsystem;
import com.bitdubai.fermat_core.layer.ccp_network_service.wallet_community.WalletCommunitySubsystem;
import com.bitdubai.fermat_core.layer.ccp_network_service.wallet_resources.WalletResourcesSubsystem;
import com.bitdubai.fermat_core.layer.ccp_network_service.wallet_statistics.WalletStatisticsSubsystem;
import com.bitdubai.fermat_core.layer.ccp_network_service.wallet_store.WalletStoreSubsystem;
import com.bitdubai.fermat_core.layer.ccp_network_service.crypto_transmission.CryptoTransmissionSubsystem;

/**
 * Created by ciencias on 03.01.15.
 */
public class NetworkServiceLayer implements PlatformLayer {

    private Plugin mBankNotesPlugin;

    private Plugin mMoney;

    private Plugin mUserPlugin;

    private Plugin mWalletCommunity;

    private Plugin mWalletResources;

    private Plugin mWalletStatistics;

    private Plugin mWalletStore;

    private Plugin mTemplate;

    private Plugin mCryptoTransmission;

    private Plugin mIntraUser;

    public Plugin getBankNotesPlugin() {
        return mBankNotesPlugin;
    }

    public Plugin getMoney() {
        return mMoney;

    }

    public Plugin getUserPlugin() {
        return mUserPlugin;
    }

    public Plugin getWalletCommunity() {
        return mWalletCommunity;
    }

    public Plugin getWalletResources() {
        return mWalletResources;
    }

    public Plugin getWalletStatistics() {
        return mWalletStatistics;
    }

    public Plugin getWalletStore() {
        return mWalletStore;
    }

    public Plugin getTemplate(){
        return mTemplate;
    }

    public Plugin getCryptoTransmission(){
        return mCryptoTransmission;
    }

    public Plugin getIntraUser(){
        return mIntraUser;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * The most essential service is the UserPlugin. I start it now.
         */

        NetworkSubsystem userSubsystem = new IntraUserSubsystem();

        try {
            userSubsystem.start();
            mUserPlugin = ((IntraUserSubsystem) userSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            //TODO: Change exceptions.
            throw new CantStartLayerException();

        }


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
         * Let's try to start the Wallet Store subsystem.
         */

        NetworkSubsystem walletCommunitySubsystem = new WalletCommunitySubsystem();

        try {
            walletCommunitySubsystem.start();
            mWalletCommunity = (walletCommunitySubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }

        /**
         * Let's try to start the Wallet Resources subsystem.
         */

        NetworkSubsystem walletResourcesSubsystem = new WalletResourcesSubsystem();

        try {
            walletResourcesSubsystem.start();
            mWalletResources = (walletResourcesSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */

            throw new CantStartLayerException();

        }


        /**
         * Let's try to start the Wallet Statistics subsystem.
         */

        NetworkSubsystem walletStatisticsSubsystem = new WalletStatisticsSubsystem();

        try {
            walletStatisticsSubsystem.start();
            mWalletStatistics = (walletStatisticsSubsystem).getPlugin();
        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            throw new CantStartLayerException();
        }

        /**
         * Let's try to start the Wallet Store subsystem.
         */

        NetworkSubsystem walletStoreSubsystem = new WalletStoreSubsystem();

        try {
            walletStoreSubsystem.start();
            mWalletStore = (walletStoreSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
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


        /**
         * Let's try to start the Crypto Transmission subsystem.
         */

        NetworkSubsystem cryptoTransmission = new CryptoTransmissionSubsystem();

        try {

            cryptoTransmission.start();
            mCryptoTransmission = (cryptoTransmission).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }

        /**
         * Let's try to start the Intra User subsystem.
         */

        NetworkSubsystem intraUserSubsystem = new IntraUserSubsystem();

        try {

            intraUserSubsystem.start();
            mIntraUser = (intraUserSubsystem).getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartIntraUserNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }
    }
}
