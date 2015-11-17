package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantSendAssetBitcoinsToUserException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.database.AssetsOverBitcoinCryptoVaultDeveloperDatabaseFactory;
import com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.structure.AssetCryptoVaultManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.List;

/**
 * The Class <code>com.bitdubai.fermat_bch_plugin.layer.cryptovault.assetsoverbitcoin.developer.bitdubai.version_1.CryptoVaultAssetsOverBitcoinPluginRoot</code>
 * is the root plugin of the Assets over bitcoin Crypto Vault.
 * <p/>
 *
 * Created by Rodrigo Acosta - (acosta_rodrigo@hotmail.com) on 06/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoVaultAssetsOverBitcoinPluginRoot extends AbstractPlugin implements
        AssetVaultManager,
        DatabaseManagerForDevelopers {

    @NeededAddonReference (platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.USER            , addon  = Addons .DEVICE_USER           )
    private DeviceUserManager deviceUserManager;

    @NeededAddonReference (platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon  = Addons .ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon  = Addons .PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference (platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM          , addon  = Addons .PLUGIN_FILE_SYSTEM    )
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS         , layer = Layers.CRYPTO_NETWORK  , plugin = Plugins.BITCOIN_NETWORK       )
    private BitcoinNetworkManager bitcoinNetworkManager;


    private AssetCryptoVaultManager assetCryptoVaultManager;


    public CryptoVaultAssetsOverBitcoinPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * DatabaseManagerForDevelopers interface implementation
     */

    /**
     * Gets the plugin database List for the Developer sub app
     * @param developerObjectFactory
     * @return
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        AssetsOverBitcoinCryptoVaultDeveloperDatabaseFactory developerDatabaseFactory = new AssetsOverBitcoinCryptoVaultDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return developerDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * Gets the plugin table list for the Developer sub app
     * @param developerObjectFactory
     * @param developerDatabase
     * @return
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        AssetsOverBitcoinCryptoVaultDeveloperDatabaseFactory developerDatabaseFactory = new AssetsOverBitcoinCryptoVaultDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return developerDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * Gets the records from the table for the Developer sub app
     * @param developerObjectFactory
     * @param developerDatabase
     * @param developerDatabaseTable
     * @return
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        AssetsOverBitcoinCryptoVaultDeveloperDatabaseFactory developerDatabaseFactory = new AssetsOverBitcoinCryptoVaultDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return developerDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    @Override
    public void start() throws CantStartPluginException {

        /**
         * The Asset vault works by creating a HD tree of keys.
         * 1) A seed is generated, or loaded by the VaultSeedGenerator class. If the device User logged is new then it will create it
         * or loaded if it exists from before.
         * 2) The AssetCryptoVaultManager will create a Master key (m) with that seed and will create key hierarchies (VaultKeyHierarchy)
         * with the VaultKeyHierarchyGenerator in a separate thread to reduce start time of the platform.
         * 3) for all the accounts that I have configured on this device (Account zero is the vault, but I may have many redeem points) I generate keys
         * for each of them
         * 4) I pass the entire set of keys to the bitcoin network so we start listening the network with those keys.
         * 5) An Agent (VaultKeyhierarchyMaintainer) will monitor the usage of keys to generate new ones when needed.
         */
        try{
            // the DeviceUserLogged
            String deviceUserLoggedPublicKey = deviceUserManager.getLoggedInDeviceUser().getPublicKey();

            assetCryptoVaultManager= new AssetCryptoVaultManager(this.pluginId,
                    pluginFileSystem,
                    pluginDatabaseSystem,
                    deviceUserLoggedPublicKey,
                    bitcoinNetworkManager);
        } catch (Exception e){
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, "couldn't start plugin because seed creation/loading failed. Key hierarchy not created.", "");
        }

        /**
         * Test
         */
        //generateAddress();
        //sendBitcoinsTest();



        /**
         * Nothing left to do.
         */
        this.serviceStatus = ServiceStatus.STARTED;
    }



    /**
     * Test Method to generate an address at startup
     */
    private void generateAddress() {
        try{
            Thread.sleep(5000);
            System.out.println("Asset vault address: " + this.getNewAssetVaultCryptoAddress(BlockchainNetworkType.DEFAULT).getAddress());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (GetNewCryptoAddressException e) {
            e.printStackTrace();
        }
    }

    private void sendBitcoinsTest(){
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                        assetCryptoVaultManager.sendAssetBitcoins("978252efd8f7affdf4ba2d6f064fee04eaf0860678bd4b0f85c98ee8240833b6", new CryptoAddress("mhdFJdh5QmiKnWjD8yoDdH2ZSGVS3aVVKE", CryptoCurrency.BITCOIN), 10000);
                    } catch (CantSendAssetBitcoinsToUserException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public CryptoAddress getNewAssetVaultCryptoAddress(BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException {
        return assetCryptoVaultManager.getNewAssetVaultCryptoAddress(blockchainNetworkType);
    }

    @Override
    public long getAvailableBalanceForTransaction(String genesisTransaction) {
        return assetCryptoVaultManager.getAvailableBalanceForTransaction(genesisTransaction);
    }

    @Override
    public String sendAssetBitcoins(String genesisTransactionId, CryptoAddress addressTo, long amount) throws CantSendAssetBitcoinsToUserException {
        return assetCryptoVaultManager.sendAssetBitcoins(genesisTransactionId, addressTo, amount);
    }
}
