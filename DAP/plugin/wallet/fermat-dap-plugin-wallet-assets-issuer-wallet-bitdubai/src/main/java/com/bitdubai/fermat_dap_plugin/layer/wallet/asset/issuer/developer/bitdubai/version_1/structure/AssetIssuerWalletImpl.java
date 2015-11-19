package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
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
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.exceptions.CantDistributeDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_distribution.interfaces.AssetDistributionManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantInitializeAssetIssuerWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletTransactionSummary;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.database.AssetIssuerWalletDao;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure.database.AssetIssuerWalletDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by franklin on 27/09/15.
 */
public class AssetIssuerWalletImpl implements AssetIssuerWallet {
    public static final String PATH_DIRECTORY = "assetissuer/assets";
    private static final String ASSET_ISSUER_WALLET_FILE_NAME = "walletsIds";

    /**
     * AssetIssuerWallet member variables.
     */
    private Database database;

    private Map<String, UUID> walletAssetIssuer = new HashMap<>();

    private AssetIssuerWalletDao assetIssuerWalletDao;
    private ErrorManager errorManager;

    private PluginDatabaseSystem pluginDatabaseSystem;

    private PluginFileSystem pluginFileSystem;

    private UUID pluginId;

    //private AssetDistributionManager assetDistributionManager;

    public AssetIssuerWalletImpl(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId/*, AssetDistributionManager assetDistributionManager*/) {
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        //this.assetDistributionManager = assetDistributionManager;
    }

    public void initialize(UUID walletId) throws CantInitializeAssetIssuerWalletException {
        if (walletId == null)
            throw new CantInitializeAssetIssuerWalletException("InternalId is null", null, "Parameter walletId is null", "loadWallet didn't find the asociated id");

        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, walletId.toString());
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeAssetIssuerWalletException("I can't open database", cantOpenDatabaseException, "WalletId: " + walletId.toString(), "");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            throw new CantInitializeAssetIssuerWalletException("Database does not exists", databaseNotFoundException, "WalletId: " + walletId.toString(), "");
        } catch (Exception exception) {
            throw new CantInitializeAssetIssuerWalletException(CantInitializeAssetIssuerWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public UUID create(String walletId) throws CantCreateWalletException {
        try {
            // TODO: Until the Wallet MAnager create the wallets, we will use this internal id
            //       We need to change this in the near future
            UUID internalWalletId = UUID.randomUUID();
            createWalletDatabase(internalWalletId);
            PluginTextFile walletAssetIssuerFile = createAssetIssuerWalletFile();
            loadAssetIssuerWalletMap(walletAssetIssuerFile);
            walletAssetIssuer.put(walletId, internalWalletId);
            persistAssetIssuerWallet(walletAssetIssuerFile);
            return internalWalletId;
        } catch (CantCreateWalletException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantCreateWalletException(CantCreateWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }

    }

    private PluginTextFile  createAssetIssuerWalletFile() throws CantCreateWalletException
    {
        try {
            return pluginFileSystem.getTextFile(pluginId, "", ASSET_ISSUER_WALLET_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException cantCreateFileException) {
            throw new CantCreateWalletException("File could not be created (?)", cantCreateFileException, "File Name: " + ASSET_ISSUER_WALLET_FILE_NAME, "");
        } catch (FileNotFoundException e) {
            throw new CantCreateWalletException("File could not be found", e, "File Name: " + ASSET_ISSUER_WALLET_FILE_NAME, "");
        }
    }

    private void loadAssetIssuerWalletMap(final PluginTextFile loadAssetIssuerWalletMap) throws CantCreateWalletException
    {
        try {
            loadAssetIssuerWalletMap.loadFromMedia();
            String[] stringAssetIssuerWallet = loadAssetIssuerWalletMap.getContent().split(";", -1);

            for (String stringWalletId : stringAssetIssuerWallet) {

                if (!stringWalletId.equals("")) {
                    String[] idPair = stringWalletId.split(",", -1);
                    walletAssetIssuer.put(idPair[0], UUID.fromString(idPair[1]));
                }
            }
        } catch (CantLoadFileException exception) {
            throw new CantCreateWalletException("Can't load file content from media", exception, "", "");
        }
    }

    private void createWalletDatabase(final UUID internalWalletId) throws CantCreateWalletException
    {
        try {
            AssetIssuerWalletDatabaseFactory databaseFactory = new AssetIssuerWalletDatabaseFactory();
            databaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);
            database = databaseFactory.createDatabase(this.pluginId, internalWalletId);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            throw new CantCreateWalletException("Database could not be created", cantCreateDatabaseException, "internalWalletId: " + internalWalletId.toString(), "");
        }
    }

    private void persistAssetIssuerWallet(final PluginTextFile pluginTextFile) throws CantCreateWalletException
    {
        StringBuilder stringBuilder = new StringBuilder(walletAssetIssuer.size() * 72);
        Iterator iterator = walletAssetIssuer.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();

            stringBuilder
                    .append(pair.getKey().toString())
                    .append(",")
                    .append(pair.getValue().toString())
                    .append(";");

            iterator.remove();
        }

        pluginTextFile.setContent(stringBuilder.toString());

        try {
            pluginTextFile.persistToMedia();
        } catch (CantPersistFileException cantPersistFileException) {
            throw new CantCreateWalletException("Could not persist in file", cantPersistFileException, "stringBuilder: " + stringBuilder.toString(), "");
        }
    }

    @Override
    public AssetIssuerWalletBalance getBookBalance(BalanceType balanceType) throws CantGetTransactionsException {
        try {
            return new AssetIssuerWallletBalanceImpl(database, pluginId, pluginFileSystem);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<AssetIssuerWalletTransaction> getTransactionsAll(BalanceType balanceType, TransactionType transactionType, String assetPublicKey) throws CantGetTransactionsException {
        try {
            assetIssuerWalletDao = new AssetIssuerWalletDao(database);
            assetIssuerWalletDao.setPluginFileSystem(pluginFileSystem);
            assetIssuerWalletDao.setPlugin(pluginId);
            return assetIssuerWalletDao.listsTransactionsByAssetsAll(balanceType, transactionType, assetPublicKey);
        }catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<AssetIssuerWalletTransaction> getTransactions(BalanceType balanceType, TransactionType transactionType, int max, int offset, String assetPublicKey) throws CantGetTransactionsException {
       try {
           assetIssuerWalletDao = new AssetIssuerWalletDao(database);
           assetIssuerWalletDao.setPluginFileSystem(pluginFileSystem);
           assetIssuerWalletDao.setPlugin(pluginId);
           return assetIssuerWalletDao.listsTransactionsByAssets(balanceType, transactionType, max, offset, assetPublicKey);
       }catch (CantGetTransactionsException exception) {
           errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
           throw exception;
       } catch (Exception exception) {
           errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
           throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
       }
    }

    @Override
    public List<AssetIssuerWalletTransaction> getTransactionsByActor(String actorPublicKey, BalanceType balanceType, int max, int offset) throws CantGetTransactionsException {
        try {
            assetIssuerWalletDao = new AssetIssuerWalletDao(database);
            assetIssuerWalletDao.setPluginFileSystem(pluginFileSystem);
            assetIssuerWalletDao.setPlugin(pluginId);
            return assetIssuerWalletDao.getTransactionsByActor(actorPublicKey, balanceType, max, offset);
        }catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<AssetIssuerWalletTransaction> gettLastActorTransactionsByTransactionType(BalanceType balanceType, TransactionType transactionType, int max, int offset) throws CantGetTransactionsException {
     try{
            assetIssuerWalletDao = new AssetIssuerWalletDao(database);
            assetIssuerWalletDao.setPluginFileSystem(pluginFileSystem);
            assetIssuerWalletDao.setPlugin(pluginId);
            return assetIssuerWalletDao.getTransactionsByTransactionType(transactionType, max, offset);
        }catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void setTransactionDescription(UUID transactionID, String description) throws CantFindTransactionException, CantStoreMemoException {
        try {
            assetIssuerWalletDao = new AssetIssuerWalletDao(database);
            assetIssuerWalletDao.setPluginFileSystem(pluginFileSystem);
            assetIssuerWalletDao.setPlugin(pluginId);
            assetIssuerWalletDao.updateMemoField(transactionID, description);
        }catch (CantStoreMemoException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public AssetIssuerWalletTransactionSummary getActorTransactionSummary(String actorPublicKey, BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        return null;
    }

    @Override
    public List<AssetIssuerWalletTransaction> getTransactionsAssetAll(String assetPublicKey) throws CantGetTransactionsException
    {
        assetIssuerWalletDao = new AssetIssuerWalletDao(database);
        List<AssetIssuerWalletTransaction> assetIssuerWalletTransactions;
        assetIssuerWalletTransactions = assetIssuerWalletDao.distributeAssets(assetPublicKey);
        return assetIssuerWalletTransactions;
    }

    @Override
    public DigitalAssetMetadata getDigitalAssetMetadata(String digitalAssetPublicKey) throws CantGetDigitalAssetFromLocalStorageException {
        DigitalAssetMetadata digitalAssetMetadata = new DigitalAssetMetadata();
        try  {
                DigitalAsset digitalAsset = new  DigitalAsset();
                PluginTextFile pluginTextFile = null;
                pluginTextFile = pluginFileSystem.getTextFile(pluginId, PATH_DIRECTORY, digitalAssetPublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                String digitalAssetData = pluginTextFile.getContent();
                digitalAsset = (DigitalAsset) XMLParser.parseXML(digitalAssetData, digitalAsset);
                digitalAssetMetadata.setDigitalAsset(digitalAsset);
        } catch (FileNotFoundException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantGetDigitalAssetFromLocalStorageException(FermatException.wrapException(e), "File no found", null);
        } catch (CantCreateFileException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantGetDigitalAssetFromLocalStorageException(FermatException.wrapException(e), "Cannot create file", null);
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(e));
            throw new CantGetDigitalAssetFromLocalStorageException(FermatException.wrapException(e), null, null);
        }
        return digitalAssetMetadata;
    }

//    @Override
//    public void distributionAssets(String assetPublicKey, String walletPublicKey, List<ActorAssetUser> actorAssetUsers)  throws CantDistributeDigitalAssetsException, CantGetTransactionsException, CantCreateFileException, FileNotFoundException {
//        try{
//            //TODO: Esta comentado solo para la prueba del Distribution
//            //Buscar el Asset Balance con la data para traerse las propiedades del Digital Asset que me entrego el Issuing en su momento.
////            List<AssetIssuerWalletTransaction> assetIssuerWalletTransactions;
////
////            HashMap<DigitalAssetMetadata, ActorAssetUser> hashMap = new HashMap<>();
////            assetIssuerWalletTransactions = assetIssuerWalletDao.distributeAssets(assetPublicKey);
////            int i = 0;
////            for (AssetIssuerWalletTransaction assetIssuerWalletTransactionList : assetIssuerWalletTransactions){
////                //TODO: Optimizar para que vea el registro de la tabla Balance Wallet
////                DigitalAsset digitalAsset = new  DigitalAsset();
////                PluginTextFile pluginTextFile = pluginFileSystem.getTextFile(pluginId, PATH_DIRECTORY, assetIssuerWalletTransactionList.getAssetPublicKey(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
////                String digitalAssetData = pluginTextFile.getContent();
////                digitalAsset = (DigitalAsset) XMLParser.parseXML(digitalAssetData, digitalAsset);
////                DigitalAssetMetadata digitalAssetMetadata = new DigitalAssetMetadata();
////                digitalAssetMetadata.setDigitalAsset(digitalAsset);
////                digitalAssetMetadata.setGenesisTransaction(assetIssuerWalletTransactionList.getTransactionHash());
////                hashMap.put(digitalAssetMetadata, actorAssetUsers.get(i));
////
////                if (i > actorAssetUsers.size()){
////                    break;
////                }
////
////                i++;
////            }
//            //assetDistributionManager.distributeAssets(hashMap, walletPublicKey);
//            HashMap<DigitalAssetMetadata, ActorAssetUser> hashMap = new HashMap<>();
//            for (ActorAssetUser actorAssetUser : actorAssetUsers){
//                hashMap.put(null, actorAssetUser);
//            }
//            assetDistributionManager.distributeAssets(hashMap, null);
//
//        }//catch(CantDistributeDigitalAssetsException | CantGetTransactionsException | CantCreateFileException | FileNotFoundException  cantDistributeDigitalAssetsException){
//        catch(CantDistributeDigitalAssetsException cantDistributeDigitalAssetsException){
//            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(cantDistributeDigitalAssetsException));
//            throw new CantDistributeDigitalAssetsException(cantDistributeDigitalAssetsException, "Error Distribution Asset", "Method: distributionAssets()");
//        }
//    }

}
