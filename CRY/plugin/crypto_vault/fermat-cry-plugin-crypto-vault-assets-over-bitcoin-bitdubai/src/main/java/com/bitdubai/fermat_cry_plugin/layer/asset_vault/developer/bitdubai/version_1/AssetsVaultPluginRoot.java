package com.bitdubai.fermat_cry_plugin.layer.asset_vault.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.DealsWithBitcoinCryptoNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantConnectToBitcoinNetwork;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVault;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.assets_vault.interfaces.AssetsVaultManager;

import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.PeerAddress;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Wallet;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.SPVBlockStore;

import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * Created by rodrigo on 8/31/15.
 */
public class AssetsVaultPluginRoot implements AssetsVaultManager, CryptoVault, DealsWithBitcoinCryptoNetwork, Plugin, Service {

    /**
     * AssetsVaultManager variables and implementation
     */
    Wallet vault;


    /**
     * CryptoVault interface variable and implementation
     */
    @Override
    public void setUserPublicKey(String userPublicKey) {

    }

    @Override
    public String getUserPublicKey() {
        return null;
    }

    @Override
    public Object getWallet() {
        return vault;
    }

    /**
     * DealsWithBitcoinCryptoNetwork interface variables and implementation
     */
    BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;
    @Override
    public void setBitcoinCryptoNetworkManager(BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager) {
        this.bitcoinCryptoNetworkManager = bitcoinCryptoNetworkManager;
    }

    /**
     * Plugin interface imeplementation
     * @param pluginId
     */
    UUID pluginId;
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }


    /**
     * Service interface implementation
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("Asset Vault starting....");

        vault = new Wallet(RegTestParams.get());
        bitcoinCryptoNetworkManager.setVault(this);
        try {
            bitcoinCryptoNetworkManager.connectToBitcoinNetwork();
        } catch (CantConnectToBitcoinNetwork cantConnectToBitcoinNetwork) {
            cantConnectToBitcoinNetwork.printStackTrace();
        }

        this.serviceStatus = ServiceStatus.STARTED;
        System.out.println("Asset Vault started.");
    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }
}
