package com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_manager.CantLoadWalletsException;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.exceptions.CantInitializeBitcoinWalletBasicException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletWallet;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.developerUtils.DeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWallet;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by loui on 30/04/15.
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 18/09/15.
 */
public class BitcoinWalletBasicWalletPluginRoot extends AbstractPlugin implements
        BitcoinWalletManager,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_BROADCASTER_SYSTEM)
    private Broadcaster broadcaster;


    private static final String WALLET_IDS_FILE_NAME = "walletsIds";
    private Map<String, UUID> walletIds = new HashMap<>();

    public BitcoinWalletBasicWalletPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /*
     * DatabaseManagerForDevelopers methods implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        List<String> databasesNames = new ArrayList<>();
        Collection<UUID> ids = this.walletIds.values();
        for (UUID id : ids)
            databasesNames.add(id.toString());
        DeveloperDatabaseFactory dbFactory = new DeveloperDatabaseFactory(this.pluginId.toString(), databasesNames);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return DeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        List<DeveloperDatabaseTableRecord> databaseTableRecords = new ArrayList<>();
        try {
            List<String> databasesNames = new ArrayList<>();
            Collection<UUID> ids = this.walletIds.values();
            for (UUID id : ids)
            {
                Database database = this.pluginDatabaseSystem.openDatabase(this.pluginId, id.toString());
                databaseTableRecords.addAll(DeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable));
                database.closeDatabase();
            }


        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("I can't open database", cantOpenDatabaseException, "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException(CantDeliverDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return databaseTableRecords;
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() throws CantStartPluginException {
        try {
            loadWalletIdsMap();
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantStartPluginException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public BitcoinWalletWallet loadWallet(String walletId) throws CantLoadWalletsException {
        try {
            BitcoinWalletBasicWallet bitcoinWallet = new BitcoinWalletBasicWallet(errorManager, pluginDatabaseSystem, pluginFileSystem, pluginId,this.broadcaster);

            UUID internalWalletId = walletIds.get(walletId);
            bitcoinWallet.initialize(internalWalletId);

            return bitcoinWallet;
        } catch (CantInitializeBitcoinWalletBasicException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantLoadWalletsException("I can't initialize wallet", exception, "", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantLoadWalletsException(CantLoadWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "", "");
        }
    }

    @Override
    public void createWallet(String walletId) throws CantCreateWalletException {
        try {
            BitcoinWalletBasicWallet bitcoinWallet = new BitcoinWalletBasicWallet(errorManager, pluginDatabaseSystem, pluginFileSystem, pluginId,this.broadcaster);

            UUID internalWalletId = bitcoinWallet.create(walletId);

            walletIds.put(walletId, internalWalletId);

            loadWalletIdsMap();


        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_BITCOIN_WALLET_BASIC_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantCreateWalletException("Wallet Creation Failed", FermatException.wrapException(exception), "walletId: " + walletId, "");
        }
    }




    private void loadWalletIdsMap() throws CantStartPluginException {
        PluginTextFile walletIdsFile = getWalletIdsFile();
        String[] stringWalletIds = walletIdsFile.getContent().split(";");

        for (String stringWalletId : stringWalletIds) {
            if (!stringWalletId.equals("")) {
                String[] idPair = stringWalletId.split(",");
                walletIds.put(idPair[0], UUID.fromString(idPair[1]));
            }
        }
    }

    private PluginTextFile getWalletIdsFile() throws CantStartPluginException {
        try {
            PluginTextFile walletIdsFile = pluginFileSystem.getTextFile(pluginId, DeviceDirectory.LOCAL_WALLETS.getName(), WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            walletIdsFile.loadFromMedia();
            return walletIdsFile;
        } catch (FileNotFoundException | CantCreateFileException exception) {
            try {
                PluginTextFile walletIdsFile = pluginFileSystem.createTextFile(pluginId, DeviceDirectory.LOCAL_WALLETS.getName(), WALLET_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                walletIdsFile.persistToMedia();
                return walletIdsFile;
            } catch (CantCreateFileException | CantPersistFileException e) {
                throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, e, null, null);
            }
        } catch (CantLoadFileException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

}

