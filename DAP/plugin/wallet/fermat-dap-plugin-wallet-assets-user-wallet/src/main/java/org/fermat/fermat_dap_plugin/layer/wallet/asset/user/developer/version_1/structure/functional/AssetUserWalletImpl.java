package org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
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

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.exceptions.CantInitializeAssetUserWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletBalance;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletTransactionSummary;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantExecuteLockOperationException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.AssetUserWalletPluginRoot;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.database.AssetUserWalletDao;
import org.fermat.fermat_dap_plugin.layer.wallet.asset.user.developer.version_1.structure.database.AssetUserWalletDatabaseFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 05/10/15.
 */
public class AssetUserWalletImpl implements AssetUserWallet, Serializable {
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
    AssetUserWalletPluginRoot assetUserWalletPluginRoot;
    private final PluginDatabaseSystem pluginDatabaseSystem;
    private final PluginFileSystem pluginFileSystem;
    private final UUID pluginId;
    private final ActorAssetUserManager userManager;
    private final ActorAssetIssuerManager issuerManager;
    private final ActorAssetRedeemPointManager redeemPointManager;
    private Broadcaster broadcaster;

    public AssetUserWalletImpl(AssetUserWalletPluginRoot assetUserWalletPluginRoot,
                               PluginDatabaseSystem pluginDatabaseSystem,
                               PluginFileSystem pluginFileSystem,
                               UUID pluginId,
                               ActorAssetUserManager userManager,
                               ActorAssetIssuerManager issuerManager,
                               ActorAssetRedeemPointManager redeemPointManager,
                               Broadcaster broadcaster) {

        this.assetUserWalletPluginRoot = assetUserWalletPluginRoot;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.userManager = userManager;
        this.issuerManager = issuerManager;
        this.redeemPointManager = redeemPointManager;
        this.broadcaster = broadcaster;
    }

    @Override
    public void initialize(UUID walletId) throws CantInitializeAssetUserWalletException {
        if (walletId == null)
            throw new CantInitializeAssetUserWalletException("InternalId is null", null, "Parameter walletId is null", "loadWallet didn't find the asociated id");

        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, walletId.toString());
            assetUserWalletDao = new AssetUserWalletDao(database, pluginFileSystem, pluginId, userManager, issuerManager, redeemPointManager);
        } catch (CantOpenDatabaseException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeAssetUserWalletException("I can't open database", e, "WalletId: " + walletId.toString(), "");
        } catch (DatabaseNotFoundException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeAssetUserWalletException("Database does not exists", e, "WalletId: " + walletId.toString(), "");
        } catch (Exception e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeAssetUserWalletException(CantInitializeAssetUserWalletException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
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
        } catch (CantCreateWalletException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException(CantCreateWalletException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
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
        } catch (CantLoadFileException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException("Can't load file content from media", e, "", "");
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
        } catch (CantPersistFileException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException("Could not persist in file", e, "stringBuilder: " + stringBuilder.toString(), "");
        }
    }

    private void createWalletDatabase(final UUID internalWalletId) throws CantCreateWalletException {
        try {
            AssetUserWalletDatabaseFactory databaseFactory = new AssetUserWalletDatabaseFactory(pluginDatabaseSystem);
            database = databaseFactory.createDatabase(this.pluginId, internalWalletId);
        } catch (CantCreateDatabaseException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException("Database could not be created", e, "internalWalletId: " + internalWalletId.toString(), "");
        }
    }

    private PluginTextFile createAssetUserWalletFile() throws CantCreateWalletException {
        try {
            return pluginFileSystem.getTextFile(pluginId, "", ASSET_USER_WALLET_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException("File could not be created (?)", e, "File Name: " + ASSET_USER_WALLET_FILE_NAME, "");
        } catch (FileNotFoundException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException("File could not be found", e, "File Name: " + ASSET_USER_WALLET_FILE_NAME, "");
        }
    }

    @Override
    public AssetUserWalletBalance getBalance() throws CantGetTransactionsException {
        try {
            return new AssetUserWalletBalanceImpl(assetUserWalletDao, broadcaster);
        } catch (Exception e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public List<AssetUserWalletTransaction> getAllTransactions(CryptoAddress cryptoAddress) throws CantGetTransactionsException {
        List<AssetUserWalletTransaction> toReturn = new ArrayList<>();
        List<AssetUserWalletTransaction> all = assetUserWalletDao.listsTransactionsByAssets(cryptoAddress);
        toReturn.addAll(all);
        return toReturn;
    }

    @Override
    public List<AssetUserWalletTransaction> getAllAvailableTransactions(String assetPublicKey) throws CantGetTransactionsException {
        List<AssetUserWalletTransaction> toReturn = new ArrayList<>();
        List<AssetUserWalletTransaction> allCreditAvailable = assetUserWalletDao.listsTransactionsByAssets(BalanceType.AVAILABLE, TransactionType.CREDIT, assetPublicKey);
        List<AssetUserWalletTransaction> alldebitAvailable = assetUserWalletDao.listsTransactionsByAssets(BalanceType.AVAILABLE, TransactionType.DEBIT, assetPublicKey);
        for (AssetUserWalletTransaction transaction : alldebitAvailable) {
            allCreditAvailable.remove(transaction);
        }
        for (AssetUserWalletTransaction tx : allCreditAvailable) {
            if (!tx.isLocked()) {
                toReturn.add(tx);
            }
        }
        return toReturn;
    }

    @Override
    public List<AssetUserWalletTransaction> getTransactionsForDisplay(CryptoAddress cryptoAddress) throws CantGetTransactionsException {
        List<AssetUserWalletTransaction> creditAvailable = getTransactions(BalanceType.AVAILABLE, TransactionType.CREDIT, cryptoAddress);
        List<AssetUserWalletTransaction> creditBook = getTransactions(BalanceType.BOOK, TransactionType.CREDIT, cryptoAddress);
        List<AssetUserWalletTransaction> debitAvailable = getTransactions(BalanceType.AVAILABLE, TransactionType.DEBIT, cryptoAddress);
        List<AssetUserWalletTransaction> debitBook = getTransactions(BalanceType.BOOK, TransactionType.DEBIT, cryptoAddress);
        List<AssetUserWalletTransaction> toReturn = new ArrayList<>();
        toReturn.addAll(getCreditsForDisplay(creditAvailable, creditBook));
        toReturn.addAll(getDebitsForDisplay(debitAvailable, debitBook));
        Collections.sort(toReturn, new Comparator<AssetUserWalletTransaction>() {
            @Override
            public int compare(AssetUserWalletTransaction o1, AssetUserWalletTransaction o2) {
                return (int) (o2.getTimestamp() - o1.getTimestamp());
            }
        });
        return toReturn;
    }

    private List<AssetUserWalletTransaction> getCreditsForDisplay(List<AssetUserWalletTransaction> available, List<AssetUserWalletTransaction> book) {
        for (AssetUserWalletTransaction transaction : book) {
            if (!available.contains(transaction)) {
                available.add(transaction);
            }
        }
        return available;
    }

    private List<AssetUserWalletTransaction> getDebitsForDisplay(List<AssetUserWalletTransaction> available, List<AssetUserWalletTransaction> book) {
        Collections.reverse(available);
        for (AssetUserWalletTransaction transaction : book) {
            if (available.contains(transaction)) {
                available.remove(transaction); //YES, THIS IS NECESSARY.
            }
            available.add(transaction);
        }
        return available;
    }

    @Override
    public List<AssetUserWalletTransaction> getTransactions(BalanceType balanceType, TransactionType transactionType, CryptoAddress cryptoAddress) throws CantGetTransactionsException {
        try {
            return assetUserWalletDao.listsTransactionsByAssets(balanceType, transactionType, cryptoAddress);
        } catch (CantGetTransactionsException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public List<AssetUserWalletTransaction> getTransactions(BalanceType balanceType, TransactionType transactionType, int max, int offset, String assetPublicKey) throws CantGetTransactionsException {
        try {
            return assetUserWalletDao.listsTransactionsByAssets(balanceType, transactionType, max, offset, assetPublicKey);
        } catch (CantGetTransactionsException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public List<AssetUserWalletTransaction> getTransactionsByActor(String actorPublicKey, BalanceType balanceType, int max, int offset) throws CantGetTransactionsException {
        try {
            return assetUserWalletDao.getTransactionsByActor(actorPublicKey, balanceType, max, offset);
        } catch (CantGetTransactionsException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public List<AssetUserWalletTransaction> gettLastActorTransactionsByTransactionType(BalanceType balanceType, TransactionType transactionType, int max, int offset) throws CantGetTransactionsException {
        try {
            return assetUserWalletDao.getTransactionsByTransactionType(transactionType, max, offset);
        } catch (CantGetTransactionsException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public void lockFunds(DigitalAssetMetadata metadata) throws RecordsNotFoundException, CantExecuteLockOperationException {
        assetUserWalletDao.lockFunds(metadata);
    }

    @Override
    public void unlockFunds(DigitalAssetMetadata metadata) throws RecordsNotFoundException, CantExecuteLockOperationException {
        assetUserWalletDao.unlockFunds(metadata);
    }

    @Override
    public void setTransactionDescription(UUID transactionID, String description) throws CantFindTransactionException, CantStoreMemoException {
        try {
            assetUserWalletDao.updateMemoField(transactionID, description);
        } catch (CantStoreMemoException e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            assetUserWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public AssetUserWalletTransactionSummary getActorTransactionSummary(String actorPublicKey, BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        return null;
    }

    @Override
    public DigitalAssetMetadata getDigitalAssetMetadata(String transactionHash) throws CantGetDigitalAssetFromLocalStorageException {
        return assetUserWalletDao.getDigitalAssetMetadata(transactionHash);
    }

    @Override
    public DigitalAsset getDigitalAsset(String assetPublicKey) throws CantGetDigitalAssetFromLocalStorageException {
        return assetUserWalletDao.getDigitalAsset(assetPublicKey);
    }
}
