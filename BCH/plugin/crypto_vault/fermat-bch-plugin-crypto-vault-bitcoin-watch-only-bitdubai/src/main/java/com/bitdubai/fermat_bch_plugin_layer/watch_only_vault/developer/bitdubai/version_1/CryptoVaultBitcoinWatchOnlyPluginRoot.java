package com.bitdubai.fermat_bch_plugin_layer.watch_only_vault.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.interfaces.PlatformCryptoVault;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.exceptions.CantInitializeWatchOnlyVaultException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.interfaces.WatchOnlyVaultManager;
import com.bitdubai.fermat_bch_plugin_layer.watch_only_vault.developer.bitdubai.version_1.database.BitcoinWatchOnlyCryptoVaultDeveloperDatabaseFactory;
import com.bitdubai.fermat_bch_plugin_layer.watch_only_vault.developer.bitdubai.version_1.structure.BitcoinWatchOnlyVaultManager;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Transaction;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 12/30/15.
 */
@PluginInfo(difficulty = PluginInfo.Dificulty.HIGH, maintainerMail = "acosta_rodrigo@hotmail.com", createdBy = "acostarodrigo", layer = Layers.CRYPTO_VAULT, platform = Platforms.BLOCKCHAINS, plugin = Plugins.BITCOIN_WATCH_ONLY_VAULT)
public class CryptoVaultBitcoinWatchOnlyPluginRoot
        extends AbstractPlugin
        implements
        DatabaseManagerForDevelopers,
        PlatformCryptoVault,
        WatchOnlyVaultManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS         , layer = Layers.CRYPTO_NETWORK  , plugin = Plugins.BITCOIN_NETWORK       )
    private BlockchainManager<ECKey, Transaction> bitcoinNetworkManager;

    /**
     * CryptoVaultBitcoinWatchOnlyPluginRoot class variables
     */
    BitcoinWatchOnlyVaultManager bitcoinWatchOnlyVaultManager;

    /**
     * Constructor
     */
    public CryptoVaultBitcoinWatchOnlyPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /**
     * DatabaseManagerForDevelopers implementation
     * @param developerObjectFactory
     * @return
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        BitcoinWatchOnlyCryptoVaultDeveloperDatabaseFactory developerDatabaseFactory = new BitcoinWatchOnlyCryptoVaultDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return developerDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    /**
     * DatabaseManagerForDevelopers implementation
     * @param developerObjectFactory
     * @param developerDatabase
     * @return
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        BitcoinWatchOnlyCryptoVaultDeveloperDatabaseFactory developerDatabaseFactory = new BitcoinWatchOnlyCryptoVaultDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return developerDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    /**
     * DatabaseManagerForDevelopers implementation
     * @param developerObjectFactory
     * @param developerDatabase
     * @param developerDatabaseTable
     * @return
     */
    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        BitcoinWatchOnlyCryptoVaultDeveloperDatabaseFactory developerDatabaseFactory = new BitcoinWatchOnlyCryptoVaultDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return developerDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }

    /**
     * Will initialize the Vault by deriving the keys, starting the agents and start monitoring the network
     * @param extendedPublicKey
     * @throws CantInitializeWatchOnlyVaultException
     */
    @Override
    public void initialize(ExtendedPublicKey extendedPublicKey) throws CantInitializeWatchOnlyVaultException {
        if (extendedPublicKey == null)
            throw new CantInitializeWatchOnlyVaultException(CantInitializeWatchOnlyVaultException.DEFAULT_MESSAGE, null, "Extended public Key received is null. Can't go on.", null);

        bitcoinWatchOnlyVaultManager.initialize(extendedPublicKey);
    }

    /**
     * Generates a Crypto Address for the specified Network.
     * @param blockchainNetworkType DEFAULT if null value is passed.
     * @return the newly generated crypto Address.
     */
    @Override
    public CryptoAddress getCryptoAddress(@Nullable BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException {
        return bitcoinWatchOnlyVaultManager.getCryptoAddress(blockchainNetworkType);
    }

    /**
     * Gets the serving platform this vault is working on.
     * @return a Fermat platform
     */
    @Override
    public Platforms getPlatform() {
        return Platforms.BLOCKCHAINS;
    }

    @Override
    public void start() throws CantStartPluginException {
        bitcoinWatchOnlyVaultManager = new BitcoinWatchOnlyVaultManager(this.errorManager, this.pluginDatabaseSystem, this.pluginFileSystem, this.bitcoinNetworkManager, this.pluginId);
        super.start();
    }

    /**
     * test method to generate new address
     */
    private void testGetNewAddress(){
        try {
            Thread.sleep(10000);
            System.out.println("CryptoAddress generated by Watch Only vault : " + this.getCryptoAddress(BlockchainNetworkType.getDefaultBlockchainNetworkType()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (GetNewCryptoAddressException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void stop() {
        super.stop();
    }
}
