package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
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
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionSummary;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.AssetUserWalletPluginRoot;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.database.AssetUserWalletDao;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.database.AssetUserWalletDatabaseFactory;
import com.bitdubai.fermat_dap_plugin.layer.wallet.asset.user.developer.bitdubai.version_1.structure.exceptions.CantInitializeAssetUserWalletException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 05/10/15.
 */
public class AssetUserWalletImpl implements AssetUserWallet {
    private static final String ASSET_USER_WALLET_FILE_NAME = "walletsIds";

    /**
     * AssetIssuerWallet member variables.
     */
    private Database database;
    private List<UUID> createdWallets;

    {
        createdWallets = new ArrayList<>();
    }

    private AssetUserWalletDao assetUserWalletDao;
    private final ErrorManager errorManager;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;
    private final ActorAssetUserManager userManager;
    private final ActorAssetIssuerManager issuerManager;
    private final ActorAssetRedeemPointManager redeemPointManager;

    public AssetUserWalletImpl(ErrorManager errorManager, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, UUID pluginId, ActorAssetUserManager userManager, ActorAssetIssuerManager issuerManager, ActorAssetRedeemPointManager redeemPointManager) {
        this.errorManager = errorManager;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.userManager = userManager;
        this.issuerManager = issuerManager;
        this.redeemPointManager = redeemPointManager;
    }

    public void initialize(UUID walletId) throws CantInitializeAssetUserWalletException {
        if (walletId == null)
            throw new CantInitializeAssetUserWalletException("InternalId is null", null, "Parameter walletId is null", "loadWallet didn't find the asociated id");

        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, walletId.toString());
            assetUserWalletDao = new AssetUserWalletDao(database, pluginFileSystem, pluginId, userManager, issuerManager, redeemPointManager);
        } catch (CantOpenDatabaseException cantOpenDatabaseException) {
            throw new CantInitializeAssetUserWalletException("I can't open database", cantOpenDatabaseException, "WalletId: " + walletId.toString(), "");
        } catch (DatabaseNotFoundException databaseNotFoundException) {
            throw new CantInitializeAssetUserWalletException("Database does not exists", databaseNotFoundException, "WalletId: " + walletId.toString(), "");
        } catch (Exception exception) {
            throw new CantInitializeAssetUserWalletException(CantInitializeAssetUserWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    public UUID create(String walletPublicKey, BlockchainNetworkType networkType) throws CantCreateWalletException {
        try {
            // TODO: Until the Wallet MAnager create the wallets, we will use this internal id
            //       We need to change this in the near future
            UUID internalWalletId = WalletUtilities.constructWalletId(walletPublicKey, networkType);
            createWalletDatabase(internalWalletId);
            PluginTextFile walletFile = createAssetUserWalletFile();
            loadAssetUserWalletList(walletFile);
            createdWallets.add(internalWalletId);
            persistAssetUserWallet(walletFile);
            return internalWalletId;
        } catch (CantCreateWalletException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new CantCreateWalletException(CantCreateWalletException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    private void loadAssetUserWalletList(final PluginTextFile walletListFile) throws CantCreateWalletException {
        try {
            walletListFile.loadFromMedia();
            for (String stringWalletId : walletListFile.getContent().split(";")) {
                if (!stringWalletId.isEmpty()) {
                    createdWallets.add(UUID.fromString(stringWalletId));
                }
            }
        } catch (CantLoadFileException exception) {
            throw new CantCreateWalletException("Can't load file content from media", exception, "", "");
        }
    }

    private void persistAssetUserWallet(final PluginTextFile pluginTextFile) throws CantCreateWalletException {
        StringBuilder stringBuilder = new StringBuilder(createdWallets.size() * 72);
        for (UUID walletId : createdWallets) {
            stringBuilder.append(walletId).append(";");
        }
        pluginTextFile.setContent(stringBuilder.toString());
        try {
            pluginTextFile.persistToMedia();
        } catch (CantPersistFileException cantPersistFileException) {
            throw new CantCreateWalletException("Could not persist in file", cantPersistFileException, "stringBuilder: " + stringBuilder.toString(), "");
        }
    }

    private void createWalletDatabase(final UUID internalWalletId) throws CantCreateWalletException {
        try {
            AssetUserWalletDatabaseFactory databaseFactory = new AssetUserWalletDatabaseFactory(pluginDatabaseSystem);
            database = databaseFactory.createDatabase(this.pluginId, internalWalletId);
        } catch (CantCreateDatabaseException cantCreateDatabaseException) {
            throw new CantCreateWalletException("Database could not be created", cantCreateDatabaseException, "internalWalletId: " + internalWalletId.toString(), "");
        }
    }

    private PluginTextFile createAssetUserWalletFile() throws CantCreateWalletException {
        try {
            return pluginFileSystem.getTextFile(pluginId, "", ASSET_USER_WALLET_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException cantCreateFileException) {
            throw new CantCreateWalletException("File could not be created (?)", cantCreateFileException, "File Name: " + ASSET_USER_WALLET_FILE_NAME, "");
        } catch (FileNotFoundException e) {
            throw new CantCreateWalletException("File could not be found", e, "File Name: " + ASSET_USER_WALLET_FILE_NAME, "");
        }
    }

    @Override
    public com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletBalance getBalance() throws CantGetTransactionsException {
        try {
            return new AssetUserWalletBalanceImpl(assetUserWalletDao);
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_DAP_ASSET_USER_WALLET, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<AssetUserWalletTransaction> getAllTransactions(String assetPublicKey) throws CantGetTransactionsException {
        List<AssetUserWalletTransaction> all = assetUserWalletDao.listsTransactionsByAssets(assetPublicKey);
        return all;
    }

    @Override
    public List<AssetUserWalletTransaction> getAllAvailableTransactions(String assetPublicKey) throws CantGetTransactionsException {
        List<AssetUserWalletTransaction> allCreditAvailable = getTransactions(BalanceType.AVAILABLE, TransactionType.CREDIT, assetPublicKey);
        List<AssetUserWalletTransaction> alldebitAvailable = getTransactions(BalanceType.AVAILABLE, TransactionType.DEBIT, assetPublicKey);
        for (AssetUserWalletTransaction transaction : alldebitAvailable) {
            allCreditAvailable.remove(transaction);
        }
        return allCreditAvailable;
    }

    @Override
    public List<AssetUserWalletTransaction> getTransactionsForDisplay(String assetPublicKey) throws CantGetTransactionsException {
        List<AssetUserWalletTransaction> creditAvailable = getTransactions(BalanceType.AVAILABLE, TransactionType.CREDIT, assetPublicKey);
        List<AssetUserWalletTransaction> creditBook = getTransactions(BalanceType.BOOK, TransactionType.CREDIT, assetPublicKey);
        List<AssetUserWalletTransaction> debitAvailable = getTransactions(BalanceType.AVAILABLE, TransactionType.DEBIT, assetPublicKey);
        List<AssetUserWalletTransaction> debitBook = getTransactions(BalanceType.BOOK, TransactionType.DEBIT, assetPublicKey);
        List<AssetUserWalletTransaction> toReturn = new ArrayList<>();
        toReturn.addAll(getTransactionsForDisplay(creditAvailable, creditBook));
        toReturn.addAll(getTransactionsForDisplay(debitBook, debitAvailable));
        Collections.sort(toReturn, new Comparator<AssetUserWalletTransaction>() {
            @Override
            public int compare(AssetUserWalletTransaction o1, AssetUserWalletTransaction o2) {
                return (int) (o2.getTimestamp() - o1.getTimestamp());
            }
        });
        return toReturn;
    }

    private List<AssetUserWalletTransaction> getTransactionsForDisplay(List<AssetUserWalletTransaction> available, List<AssetUserWalletTransaction> book) {
        for (AssetUserWalletTransaction transaction : book) {
            if (!available.contains(transaction)) {
                available.add(transaction);
            }
        }
        return available;
    }

    @Override
    public List<AssetUserWalletTransaction> getTransactions(BalanceType balanceType, TransactionType transactionType, String assetPublicKey) throws CantGetTransactionsException {
        try {
            return assetUserWalletDao.listsTransactionsByAssets(balanceType, transactionType, assetPublicKey);
        } catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<AssetUserWalletTransaction> getTransactions(BalanceType balanceType, TransactionType transactionType, int max, int offset, String assetPublicKey) throws CantGetTransactionsException {
        try {
            return assetUserWalletDao.listsTransactionsByAssets(balanceType, transactionType, max, offset, assetPublicKey);
        } catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<AssetUserWalletTransaction> getTransactionsByActor(String actorPublicKey, BalanceType balanceType, int max, int offset) throws CantGetTransactionsException {
        try {
            return assetUserWalletDao.getTransactionsByActor(actorPublicKey, balanceType, max, offset);
        } catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public List<AssetUserWalletTransaction> gettLastActorTransactionsByTransactionType(BalanceType balanceType, TransactionType transactionType, int max, int offset) throws CantGetTransactionsException {
        try {
            return assetUserWalletDao.getTransactionsByTransactionType(transactionType, max, offset);
        } catch (CantGetTransactionsException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public void lockFunds(DigitalAssetMetadata metadata) {
        //TODO
    }

    @Override
    public void unlockFunds(DigitalAssetMetadata metadata) {
        //TODO
    }

    @Override
    public void setTransactionDescription(UUID transactionID, String description) throws CantFindTransactionException, CantStoreMemoException {
        try {
            assetUserWalletDao.updateMemoField(transactionID, description);
        } catch (CantStoreMemoException exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw exception;
        } catch (Exception exception) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_ASSET_WALLET_ISSUER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, FermatException.wrapException(exception));
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(exception), null, null);
        }
    }

    @Override
    public AssetUserWalletTransactionSummary getActorTransactionSummary(String actorPublicKey, BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        return null;
    }

    @Override
    public DigitalAssetMetadata getDigitalAssetMetadata(String transactionHash) throws CantGetDigitalAssetFromLocalStorageException {
        String context = "Path: " + AssetUserWalletPluginRoot.PATH_DIRECTORY + " - Tx Hash: " + transactionHash;
        try {
            String metadataXML = pluginFileSystem.getTextFile(pluginId, AssetUserWalletPluginRoot.PATH_DIRECTORY, transactionHash, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT).getContent();
            return (DigitalAssetMetadata) XMLParser.parseXML(metadataXML, new DigitalAssetMetadata());
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetDigitalAssetFromLocalStorageException(e, context, "The path could be wrong or there was an error creating the file.");
        }
    }

    @Override
    public DigitalAsset getDigitalAsset(String assetPublicKey) throws CantGetDigitalAssetFromLocalStorageException {
        String context = "Path: " + AssetUserWalletPluginRoot.PATH_DIRECTORY + " - Asset PK: " + assetPublicKey;
        try {
            String assetXML = pluginFileSystem.getTextFile(pluginId, AssetUserWalletPluginRoot.PATH_DIRECTORY, assetPublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT).getContent();
            return (DigitalAsset) XMLParser.parseXML(assetXML, new DigitalAsset());
        } catch (FileNotFoundException | CantCreateFileException e) {
            throw new CantGetDigitalAssetFromLocalStorageException(e, context, "The path could be wrong or there was an error creating the file.");
        }
    }
}
