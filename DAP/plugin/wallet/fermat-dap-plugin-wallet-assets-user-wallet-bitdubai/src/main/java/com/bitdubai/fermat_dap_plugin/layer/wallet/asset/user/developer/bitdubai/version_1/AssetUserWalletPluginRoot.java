package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
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
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionRecord;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.AssetUserWalletImpl;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.database.AssetUserWalletDao;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.database.DeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.exceptions.CantDeliveryDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.exceptions.CantInitializeAssetUserWalletException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Franklin on 07/09/15.
 */
public class AssetUserWalletPluginRoot extends AbstractPlugin implements
        AssetUserWalletManager,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    public AssetUserWalletPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private static final String WALLET_USER_FILE_NAME = "walletsIds";
    private Map<String, UUID> walletUser = new HashMap<>();

    @Override
    public void start() throws CantStartPluginException {
        try{
            loadWalletIssuerMap();
            createAssetUserWallet("walletPublicKeyTest");
            loadAssetUserWallet("walletPublicKeyTest");
            System.out.println("Star Plugin AssetWalletUser");
            this.serviceStatus = ServiceStatus.STARTED;

            //TODO testing code
            testInsert();

        }catch(CantStartPluginException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);

        }
    }

    private void testInsert() throws Exception {
        DigitalAsset digitalAsset = new DigitalAsset();
        digitalAsset.setPublicKey("digitalAssetPublicKey");
        digitalAsset.setName("name");
        digitalAsset.setDescription("description");

        CryptoAddress cryptoAddress = new CryptoAddress();
        cryptoAddress.setAddress("address");

        CryptoTransaction cryptoTransaction = new CryptoTransaction();
        cryptoTransaction.setAddressFrom(cryptoAddress);
        cryptoTransaction.setAddressTo(cryptoAddress);
        cryptoTransaction.setCryptoAmount(1);
        cryptoTransaction.setTransactionHash("transactionHash");

        AssetUserWalletTransactionRecord record = new AssetUserWalletTransactionRecordWrapper(
                new DigitalAssetMetadata(digitalAsset), cryptoTransaction, "", ""
        );

        UUID internalAssetIssuerWalletId = walletUser.get("walletPublicKeyTest");
        Database database = pluginDatabaseSystem.openDatabase(pluginId, internalAssetIssuerWalletId.toString());
        AssetUserWalletDao assetUserWalletDao = new AssetUserWalletDao(database);
        assetUserWalletDao.setPluginFileSystem(pluginFileSystem);
        assetUserWalletDao.setPlugin(pluginId);
        assetUserWalletDao.addCredit(record, BalanceType.AVAILABLE);
    }

    @Override
    public AssetUserWallet loadAssetUserWallet(String walletPublicKey) throws CantLoadWalletException {
        try {
            AssetUserWalletImpl assetUserWallet = new AssetUserWalletImpl(errorManager, pluginDatabaseSystem, pluginFileSystem, pluginId);

            UUID internalAssetIssuerWalletId = walletUser.get(walletPublicKey);
            assetUserWallet.initialize(internalAssetIssuerWalletId);
            return assetUserWallet;

        }catch (CantInitializeAssetUserWalletException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantLoadWalletException("I can't initialize wallet", exception, "", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantLoadWalletException(CantLoadWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "", "");
        }
    }

    @Override
    public void createAssetUserWallet(String walletPublicKey) throws CantCreateWalletException {
        try {
            AssetUserWalletImpl assetUserWallet = new AssetUserWalletImpl(errorManager, pluginDatabaseSystem, pluginFileSystem, pluginId);

            UUID internalAssetIssuerWalletId = assetUserWallet.create(walletPublicKey);

            walletUser.put(walletPublicKey, internalAssetIssuerWalletId);
        }catch (CantCreateWalletException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantCreateWalletException("Wallet Creation Failed", exception, "walletId: " + walletPublicKey, "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantCreateWalletException("Wallet Creation Failed", FermatException.wrapException(exception), "walletId: " + walletPublicKey, "");
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        List<String> databasesNames = new ArrayList<>();
        Collection<UUID> ids = this.walletUser.values();
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
        UUID walletId = null;
        try {
            loadWalletIssuerMap();
            walletId = walletUser.get("walletPublicKeyTest");
            Database database = this.pluginDatabaseSystem.openDatabase(this.pluginId, walletId.toString());
            databaseTableRecords.addAll(DeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable));
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliveryDatabaseException("I can't open database", cantOpenDatabaseException, "WalletId: " + walletId.toString(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliveryDatabaseException("Database does not exists", databaseNotFoundException, "WalletId: " + walletId.toString(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliveryDatabaseException(CantDeliveryDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "WalletId: " + walletId.toString(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return databaseTableRecords;
    }

    private void loadWalletIssuerMap() throws CantStartPluginException {
        PluginTextFile walletIssuerFile = getWalletUserFile();
        String[] stringWalletIssuer = walletIssuerFile.getContent().split(";", -1);

        for (String stringWalletId : stringWalletIssuer)
            if (!stringWalletId.equals("")) {
                String[] idPair = stringWalletId.split(",", -1);
                walletUser.put(idPair[0], UUID.fromString(idPair[1]));
            }
    }

    private PluginTextFile getWalletUserFile() throws CantStartPluginException {
        try {
            PluginTextFile walletUserFile = pluginFileSystem.getTextFile(pluginId, "", WALLET_USER_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            walletUserFile.loadFromMedia();
            return walletUserFile;
        } catch (FileNotFoundException | CantCreateFileException exception) {
            return createWalletUserFile();
        } catch (CantLoadFileException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    private PluginTextFile createWalletUserFile() throws CantStartPluginException {
        try {
            PluginTextFile walletIssuerFile = pluginFileSystem.createTextFile(pluginId, "", WALLET_USER_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            walletIssuerFile.persistToMedia();
            return walletIssuerFile;
        } catch (CantCreateFileException | CantPersistFileException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, null, null);
        }
    }
}
