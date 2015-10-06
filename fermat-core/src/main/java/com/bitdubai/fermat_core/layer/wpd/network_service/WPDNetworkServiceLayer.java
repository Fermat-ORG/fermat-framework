package com.bitdubai.fermat_core.layer.wpd.network_service;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_network_service.NetworkSubsystem;
import com.bitdubai.fermat_core.layer.wpd.network_service.wallet_community.WalletCommunitySubsystem;
import com.bitdubai.fermat_core.layer.wpd.network_service.wallet_resources.WalletResourcesSubsystem;
import com.bitdubai.fermat_core.layer.wpd.network_service.wallet_statistics.WalletStatisticsSubsystem;
import com.bitdubai.fermat_core.layer.wpd.network_service.wallet_store.WalletStoreSubsystem;

/**
 * Created by Nerio on 23/09/15.
 */
public class WPDNetworkServiceLayer implements PlatformLayer {


    private Plugin mWalletCommunity;

    private Plugin mWalletResources;

    private Plugin mWalletStatistics;

    private Plugin mWalletStore;

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

    /**
     * Each layer is started and by that time has the chance to initialize its services.
     */
    @Override
    public void start() throws CantStartLayerException {

        /**
         * Let's try to start the Wallet Store subsystem.
         */
        NetworkSubsystem walletCommunitySubsystem = new WalletCommunitySubsystem();

        try {
            walletCommunitySubsystem.start();
            mWalletCommunity = walletCommunitySubsystem.getPlugin();

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
            mWalletResources = walletResourcesSubsystem.getPlugin();

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
            mWalletStatistics = walletStatisticsSubsystem.getPlugin();
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
            mWalletStore = walletStoreSubsystem.getPlugin();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());
            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }
    }
}
