package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.exceptions.GetNewCryptoAddressException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.ExtendedPublicKey;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.exceptions.CantInitializeWatchOnlyVaultException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.watch_only_vault.interfaces.WatchOnlyVaultManager;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by rodrigo on 12/30/15.
 */
public class CryptoVaultBitcoinWatchOnlyPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, WatchOnlyVaultManager {

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
        return null;
    }

    /**
     * DatabaseManagerForDevelopers implementation
     * @param developerObjectFactory
     * @param developerDatabase
     * @return
     */
    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return null;
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
        return null;
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

    }

    /**
     * Generates a Crypto Address for the specified Network.
     * @param blockchainNetworkType DEFAULT if null value is passed.
     * @return the newly generated crypto Address.
     */
    @Override
    public CryptoAddress getCryptoAddress(@Nullable BlockchainNetworkType blockchainNetworkType) throws GetNewCryptoAddressException {
        return null;
    }

    /**
     * Gets the serving platform this vault is working on.
     * @return a Fermat platform
     */
    @Override
    public Platforms getPlatform() {
        return Platforms.BLOCKCHAINS;
    }


}
