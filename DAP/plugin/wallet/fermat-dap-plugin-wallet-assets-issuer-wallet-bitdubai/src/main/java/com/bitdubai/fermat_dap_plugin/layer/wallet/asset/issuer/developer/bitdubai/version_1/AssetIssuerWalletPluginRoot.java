package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Genders;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
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
import com.bitdubai.fermat_api.layer.osa_android.location_system.Location;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPConnectionState;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces.AssetDistributionManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantInitializeAssetIssuerWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.AssetIssuerWalletImpl;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.AssetIssuerWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.database.DeveloperDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Franklin on 07/09/15.
 */
public class AssetIssuerWalletPluginRoot extends AbstractPlugin implements
        AssetIssuerWalletManager,
        DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM   , layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER         )
    private ErrorManager errorManager;

    //Commented by Manuel Perez to eliminate cyclic reference exception
    //@NeededPluginReference(platform = Platforms.DIGITAL_ASSET_PLATFORM, layer = Layers.DIGITAL_ASSET_TRANSACTION, plugin = Plugins.ASSET_DISTRIBUTION)
    AssetDistributionManager assetDistributionManager;


    private static final String WALLET_ISSUER_FILE_NAME = "walletsIds";
    private Map<String, UUID> walletIssuer = new HashMap<>();

    public AssetIssuerWalletPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }
    boolean existWallet = false;
    String walletPublicKey = "walletPublicKeyTest";
    AssetIssuerWallet assetIssuerWallet;

    @Override
    public void start() throws CantStartPluginException {
        try{
            loadWalletIssuerMap();

            try {
                if(!existWallet) {
                    createWalletAssetIssuer(walletPublicKey);
                }

                assetIssuerWallet = loadAssetIssuerWallet(walletPublicKey);

            } catch (CantLoadWalletException e) {
                e.printStackTrace();
            }

            testWallet();
            System.out.println("Start Plugin AssetWalletIssuer");
            this.serviceStatus = ServiceStatus.STARTED;
        }catch(CantStartPluginException exception){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, exception);
            throw exception;
        }catch(Exception exception){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);

        }
    }

    private boolean loadWalletIssuerMap() throws CantStartPluginException {
        PluginTextFile walletIssuerFile = getWalletIssuerFile();
        String[] stringWalletIssuer = walletIssuerFile.getContent().split(";", -1);

        for (String stringWalletId : stringWalletIssuer)
            if (!stringWalletId.equals("")) {
                String[] idPair = stringWalletId.split(",", -1);
                walletIssuer.put(idPair[0], UUID.fromString(idPair[1]));
                existWallet = true;
            }
        return existWallet;
    }

    private PluginTextFile getWalletIssuerFile() throws CantStartPluginException {
        try {
            PluginTextFile walletIssuerFile = pluginFileSystem.getTextFile(pluginId, "", WALLET_ISSUER_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            walletIssuerFile.loadFromMedia();
            return walletIssuerFile;
        } catch (FileNotFoundException | CantCreateFileException exception) {
            return createWalletIssuerFile();
        } catch (CantLoadFileException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    private PluginTextFile createWalletIssuerFile() throws CantStartPluginException {
        try {
            PluginTextFile walletIssuerFile = pluginFileSystem.createTextFile(pluginId, "", WALLET_ISSUER_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            walletIssuerFile.persistToMedia();
            return walletIssuerFile;
        } catch (CantCreateFileException | CantPersistFileException exception) {
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, exception, null, null);
        }
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        List<String> databasesNames = new ArrayList<>();
        Collection<UUID> ids = this.walletIssuer.values();
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
            Database database = this.pluginDatabaseSystem.openDatabase(this.pluginId, developerDatabase.getName());
            databaseTableRecords.addAll(DeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, database, developerDatabaseTable));
            database.closeDatabase();
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            /**
             * The database exists but cannot be open. I can not handle this situation.
             */
            FermatException e = new CantDeliverDatabaseException("I can't open database", cantOpenDatabaseException, "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            FermatException e = new CantDeliverDatabaseException("Database does not exists", databaseNotFoundException, "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (Exception exception) {
            FermatException e = new CantDeliverDatabaseException(CantDeliverDatabaseException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "WalletId: " + developerDatabase.getName(), "");
            this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        // If we are here the database could not be opened, so we return an empry list
        return databaseTableRecords;
    }

    @Override
    public AssetIssuerWallet loadAssetIssuerWallet(String walletPublicKey) throws CantLoadWalletException {

        try {
            AssetIssuerWalletImpl assetIssuerWallet = new AssetIssuerWalletImpl(errorManager, pluginDatabaseSystem, pluginFileSystem, pluginId/*, assetDistributionManager*/);

            UUID internalAssetIssuerWalletId = walletIssuer.get(walletPublicKey);
            assetIssuerWallet.initialize(internalAssetIssuerWalletId);
            return assetIssuerWallet;

        }catch (CantInitializeAssetIssuerWalletException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantLoadWalletException("I can't initialize wallet", exception, "", "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantLoadWalletException(CantLoadWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "", "");
        }
    }

    @Override
    public void createWalletAssetIssuer(String walletPublicKey) throws CantCreateWalletException {
        try {
            AssetIssuerWalletImpl assetIssuerWallet = new AssetIssuerWalletImpl(errorManager, pluginDatabaseSystem, pluginFileSystem, pluginId/*, assetDistributionManager*/);

            UUID internalAssetIssuerWalletId = assetIssuerWallet.create(walletPublicKey);

            walletIssuer.put(walletPublicKey, internalAssetIssuerWalletId);
        }catch (CantCreateWalletException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantCreateWalletException("Wallet Creation Failed", exception, "walletId: " + walletPublicKey, "");
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantCreateWalletException("Wallet Creation Failed", FermatException.wrapException(exception), "walletId: " + walletPublicKey, "");
        }
    }

    private void testWallet(){
        DigitalAsset digitalAsset = new DigitalAsset();
        digitalAsset.setPublicKey("assetPublicKeyNew1");
        digitalAsset.setName("McDonald Coupon");
        digitalAsset.setDescription("2x1 La Patria te Da Mas");
        CryptoAddress cryptoFromAddress = new CryptoAddress("cryptoAddresFrom", CryptoCurrency.BITCOIN);
        CryptoAddress cryptoToAddress = new CryptoAddress("cryptoAddresFrom", CryptoCurrency.BITCOIN);

        AssetIssuerWalletTransactionRecordWrapper assetIssuerWalletTransactionRecordWrapper = new AssetIssuerWalletTransactionRecordWrapper(
                digitalAsset,
                "",
                "McDonald Coupon Coupon",
                "2x1  La Patria te Da Mas",
                cryptoFromAddress,
                cryptoToAddress,
                "actorFromPublicKey",
                "actorToPublicKey",
                Actors.DAP_ASSET_USER,
                Actors.DAP_ASSET_USER,
                10000,
                0,
                "memo",
                "digitalAssetMetadaHash",
                UUID.randomUUID().toString()
                );

        DigitalAsset digitalAsset1 = new DigitalAsset();
        digitalAsset1.setPublicKey("assetPublicKeyNew2");
        digitalAsset1.setName("Asset for Distribution");
        digitalAsset1.setDescription("2x1 La Patria te Da Mas");
        CryptoAddress cryptoFromAddress1 = new CryptoAddress("cryptoAddresFrom", CryptoCurrency.BITCOIN);
        CryptoAddress cryptoToAddress1 = new CryptoAddress("cryptoAddresFrom", CryptoCurrency.BITCOIN);

        AssetIssuerWalletTransactionRecordWrapper assetIssuerWalletTransactionRecordWrapper3 = new AssetIssuerWalletTransactionRecordWrapper(
                digitalAsset1,
                "",
                "Asset for Distribution",
                "2x1  La Patria te Da Mas",
                cryptoFromAddress1,
                cryptoToAddress1,
                "actorFromPublicKey",
                "actorToPublicKey",
                Actors.DAP_ASSET_USER,
                Actors.DAP_ASSET_USER,
                20000,
                0,
                "memo",
                "digitalAssetMetadaHash",
                UUID.randomUUID().toString()
        );
        try {
            assetIssuerWallet.getBookBalance(BalanceType.AVAILABLE).credit(assetIssuerWalletTransactionRecordWrapper, BalanceType.AVAILABLE);
            assetIssuerWallet.getBookBalance(BalanceType.AVAILABLE).credit(assetIssuerWalletTransactionRecordWrapper3, BalanceType.AVAILABLE);

            AssetIssuerWalletTransactionRecordWrapper assetIssuerWalletTransactionRecordWrapper1 = new AssetIssuerWalletTransactionRecordWrapper(
                    digitalAsset,
                    "",
                    "KFC Coupon",
                    "2x1",
                    cryptoFromAddress,
                    cryptoToAddress,
                    "actorFromPublicKey",
                    "actorToPublicKey",
                    Actors.DAP_ASSET_USER,
                    Actors.DAP_ASSET_USER,
                    10000,
                    0,
                    "memo",
                    "digitalAssetMetadaHash",
                    UUID.randomUUID().toString()
            );
            AssetIssuerWalletTransactionRecordWrapper assetIssuerWalletTransactionRecordWrapper2 = new AssetIssuerWalletTransactionRecordWrapper(
                    digitalAsset,
                    "",
                    "KFC Coupon",
                    "2x1",
                    cryptoFromAddress,
                    cryptoToAddress,
                    "actorFromPublicKey",
                    "actorToPublicKey",
                    Actors.DAP_ASSET_USER,
                    Actors.DAP_ASSET_USER,
                    10000,
                    0,
                    "memo",
                    "digitalAssetMetadaHash",
                    UUID.randomUUID().toString()
            );
            assetIssuerWallet.getBookBalance(BalanceType.AVAILABLE).credit(assetIssuerWalletTransactionRecordWrapper1, BalanceType.AVAILABLE);
            assetIssuerWallet.getBookBalance(BalanceType.AVAILABLE).debit (assetIssuerWalletTransactionRecordWrapper2, BalanceType.AVAILABLE);
        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            e.printStackTrace();
        }
        try {
//            List<AssetIssuerWalletList> assetIssuerWalletLists = assetIssuerWallet.getBookBalance(BalanceType.AVAILABLE).getAssetIssuerWalletBalancesAvailable();
//            //List<AssetIssuerWalletList> assetIssuerWalletLists = assetIssuerWallet.getBookBalance(BalanceType.BOOK).getAssetIssuerWalletBalancesAvailable();
//            System.out.println("--------LISTADO DE ASSET BALANCE---------------------------------------------");
//
//            for (AssetIssuerWalletList assetIssuerWalletList : assetIssuerWalletLists){
//                System.out.println("-------------------------------------------------------------------------");
//                System.out.println("Asset PublicKey        : "   + assetIssuerWalletList.getAssetPublicKey());
//                System.out.println("Asset Name             : "   + assetIssuerWalletList.getName());
//                System.out.println("Asset Description      : "   + assetIssuerWalletList.getDescription());
//                System.out.println("Asset Balance Available: "   + assetIssuerWalletList.getAvailableBalance());
//                System.out.println("Asset Balance Book     : "   + assetIssuerWalletList.getBookBalance());
//                System.out.println("-------------------------------------------------------------------------");
//            }
//
//            List<AssetIssuerWalletTransaction> assetIssuerWalletTransactions = assetIssuerWallet.getTransactionsAll(BalanceType.AVAILABLE, TransactionType.CREDIT, "assetPublicKeyNew2");
//            //List<AssetIssuerWalletTransaction> assetIssuerWalletTransactions = assetIssuerWallet.getTransactions(BalanceType.AVAILABLE, TransactionType.DEBIT, 1, 1000, "assetPublicKey");
//            System.out.println("--------LISTADO DE TRANSACTIONS CREDITOS-------------------------------------");
//            for (AssetIssuerWalletTransaction assetIssuerWalletTransaction : assetIssuerWalletTransactions){
//                System.out.println("-------------------------------------------------------------------------");
//                System.out.println("Asset PublicKey        : "   + assetIssuerWalletTransaction.getAssetPublicKey());
//                System.out.println("Address From           : "   + assetIssuerWalletTransaction.getAddressFrom());
//                System.out.println("Address To             : "   + assetIssuerWalletTransaction.getAddressTo());
//                System.out.println("Amount                 : "   + assetIssuerWalletTransaction.getAmount());
//                System.out.println("Transaction Type       : "   + assetIssuerWalletTransaction.getTransactionType().getCode());
//                System.out.println("TransactionId          : "   + assetIssuerWalletTransaction.getTransactionId());
//                System.out.println("-------------------------------------------------------------------------");
//            }
//            List<AssetIssuerWalletTransaction> assetIssuerWalletTransactionsD = assetIssuerWallet.getTransactionsAll(BalanceType.AVAILABLE, TransactionType.DEBIT, "assetPublicKey");
//            System.out.println("--------LISTADO DE TRANSACTIONS DEBITOS-------------------------------------");
//            for (AssetIssuerWalletTransaction assetIssuerWalletTransaction : assetIssuerWalletTransactionsD){
//                System.out.println("-------------------------------------------------------------------------");
//                System.out.println("Asset PublicKey        : "   + assetIssuerWalletTransaction.getAssetPublicKey());
//                System.out.println("Address From           : "   + assetIssuerWalletTransaction.getAddressFrom());
//                System.out.println("Address To             : "   + assetIssuerWalletTransaction.getAddressTo());
//                System.out.println("Amount                 : "   + assetIssuerWalletTransaction.getAmount());
//                System.out.println("Transaction Type       : "   + assetIssuerWalletTransaction.getTransactionType().getCode());
//                System.out.println("TransactionId          : "   + assetIssuerWalletTransaction.getTransactionId());
//                System.out.println("-------------------------------------------------------------------------");
//            }
            ActorAssetUser actorAssetUser = new ActorAssetUser() {

                @Override
                public String getPublicLinkedIdentity() {
                    return "publicLinkedKeyActor";
                }

                @Override
                public String getPublicKey() {
                    return "publicKeyActor";
                }

                @Override
                public String getName() {
                    return "mock Actor";
                }

                @Override
                public long getRegistrationDate() {
                    return 0;
                }

                @Override
                public long getLastConnectionDate() {
                    return 0;
                }

                @Override
                public byte[] getProfileImage() {
                    return new byte[0];
                }

                @Override
                public DAPConnectionState getDapConnectionState() {
                    return DAPConnectionState.CONNECTED_ONLINE;
                }

                @Override
                public Location getLocation() {
                    return null;
                }

                @Override
                public Double getLocationLatitude() {
                    return null;
                }

                @Override
                public Double getLocationLongitude() {
                    return null;
                }

//                @Override
//                public Location getLocation() {
//                    return null;
//                }

                @Override
                public Genders getGenders() {
                    return null;
                }

                @Override
                public String getAge() {
                    return null;
                }

                @Override
                public CryptoAddress getCryptoAddress() {
                    return null;
                }
            };
            //assetIssuerWallet.distributionAssets("assetPublicKeyNew2", "walletPublicKeyTest", actorAssetUser);

        } catch (Exception e){
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            e.printStackTrace();
        }
    }
}
