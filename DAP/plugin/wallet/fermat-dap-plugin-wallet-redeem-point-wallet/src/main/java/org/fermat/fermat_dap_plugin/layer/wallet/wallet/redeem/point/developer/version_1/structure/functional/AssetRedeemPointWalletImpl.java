package org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
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

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPointManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantGetRedeemPointStatisticsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantInitializeRedeemPointWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions.CantSaveRedeemPointStatisticException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWallet;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletBalance;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransaction;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletTransactionSummary;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.RedeemPointStatistic;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantCreateWalletException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantFindTransactionException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetActorTransactionSummaryException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantStoreMemoException;
import org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.AssetRedeemPointWalletPluginRoot;
import org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.database.AssetRedeemPointWalletDao;
import org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.database.AssetRedeemPointWalletDatabaseFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 15/10/15.
 */
public class AssetRedeemPointWalletImpl implements AssetRedeemPointWallet {
    public static final String PATH_DIRECTORY = "assetredeem-point/assets";
    private static final String ASSET_REDEEM_POINT_WALLET_FILE_NAME = "walletsIds";


    /**
     * AssetIssuerWallet member variables.
     */
    private Database database;


    private List<UUID> createdWallets;

    {
        createdWallets = new ArrayList<>();
    }

    private org.fermat.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.version_1.structure.database.AssetRedeemPointWalletDao assetRedeemPointWalletDao;

    AssetRedeemPointWalletPluginRoot assetRedeemPointWalletPluginRoot;

    private final PluginDatabaseSystem pluginDatabaseSystem;

    private final PluginFileSystem pluginFileSystem;

    private final UUID pluginId;

    private final ActorAssetUserManager actorAssetUserManager;

    private final ActorAssetIssuerManager issuerManager;

    private final ActorAssetRedeemPointManager redeemPointManager;

    private Broadcaster broadcaster;

    public AssetRedeemPointWalletImpl(AssetRedeemPointWalletPluginRoot assetRedeemPointWalletPluginRoot,
                                      PluginDatabaseSystem pluginDatabaseSystem,
                                      PluginFileSystem pluginFileSystem,
                                      UUID pluginId,
                                      ActorAssetUserManager actorAssetUserManager,
                                      ActorAssetIssuerManager issuerManager,
                                      ActorAssetRedeemPointManager redeemPointManager,
                                      Broadcaster broadcaster) {

        this.assetRedeemPointWalletPluginRoot = assetRedeemPointWalletPluginRoot;
        this.pluginDatabaseSystem = pluginDatabaseSystem;
        this.pluginFileSystem = pluginFileSystem;
        this.pluginId = pluginId;
        this.actorAssetUserManager = actorAssetUserManager;
        this.issuerManager = issuerManager;
        this.redeemPointManager = redeemPointManager;
        this.broadcaster = broadcaster;
    }

    public void initialize(UUID walletId) throws CantInitializeRedeemPointWalletException {
        if (walletId == null)
            throw new CantInitializeRedeemPointWalletException("InternalId is null", null, "Parameter walletId is null", "loadWallet didn't find the asociated id");

        try {
            database = this.pluginDatabaseSystem.openDatabase(this.pluginId, walletId.toString());
            assetRedeemPointWalletDao = new AssetRedeemPointWalletDao(database, pluginFileSystem, pluginId, actorAssetUserManager, issuerManager, redeemPointManager);
        } catch (CantOpenDatabaseException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeRedeemPointWalletException("I can't open database", e, "WalletId: " + walletId.toString(), "");
        } catch (DatabaseNotFoundException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeRedeemPointWalletException("Database does not exists", e, "WalletId: " + walletId.toString(), "");
        } catch (Exception e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantInitializeRedeemPointWalletException(CantInitializeRedeemPointWalletException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    public UUID create(String walletPublicKey, BlockchainNetworkType networkType) throws CantCreateWalletException {
        try {
            // TODO: Until the Wallet MAnager create the wallets, we will use this internal id
            //       We need to change this in the near future
            UUID internalWalletId = WalletUtilities.constructWalletId(walletPublicKey, networkType);
            createWalletDatabase(internalWalletId);
            PluginTextFile walletFile = createAssetRedeemWalletFile();
            loadAssetRedeemPointWalletList(walletFile);
            createdWallets.add(internalWalletId);
            persistAssetRedeemPointWallet(walletFile);
            return internalWalletId;
        } catch (CantCreateWalletException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException(CantCreateWalletException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    private void persistAssetRedeemPointWallet(final PluginTextFile pluginTextFile) throws CantCreateWalletException {
        StringBuilder stringBuilder = new StringBuilder(createdWallets.size() * 72);
        for (UUID walletId : createdWallets) {
            stringBuilder.append(walletId).append(";");
        }
        pluginTextFile.setContent(stringBuilder.toString());
        try {
            pluginTextFile.persistToMedia();
        } catch (CantPersistFileException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException("Could not persist in file", e, "stringBuilder: " + stringBuilder.toString(), "");
        }
    }

    private PluginTextFile createAssetRedeemWalletFile() throws CantCreateWalletException {
        try {
            return pluginFileSystem.getTextFile(pluginId, "", ASSET_REDEEM_POINT_WALLET_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
        } catch (CantCreateFileException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException("File could not be created (?)", e, "File Name: " + ASSET_REDEEM_POINT_WALLET_FILE_NAME, "");
        } catch (FileNotFoundException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException("File could not be found", e, "File Name: " + ASSET_REDEEM_POINT_WALLET_FILE_NAME, "");
        }
    }

    private void loadAssetRedeemPointWalletList(final PluginTextFile walletListFile) throws CantCreateWalletException {
        try {
            walletListFile.loadFromMedia();
            for (String stringWalletId : walletListFile.getContent().split(";")) {
                if (!stringWalletId.isEmpty()) {
                    createdWallets.add(UUID.fromString(stringWalletId));
                }
            }
        } catch (CantLoadFileException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException("Can't load file content from media", e, "", "");
        }
    }

    private void createWalletDatabase(final UUID internalWalletId) throws CantCreateWalletException {
        try {
            AssetRedeemPointWalletDatabaseFactory databaseFactory = new AssetRedeemPointWalletDatabaseFactory(pluginDatabaseSystem);
            database = databaseFactory.createDatabase(this.pluginId, internalWalletId);
        } catch (CantCreateDatabaseException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantCreateWalletException("Database could not be created", e, "internalWalletId: " + internalWalletId.toString(), "");
        }
    }

    @Override
    public AssetRedeemPointWalletBalance getBalance() throws CantGetTransactionsException {
        try {
            return new AssetRedeemPointWalletBalanceImpl(assetRedeemPointWalletDao, broadcaster);
        } catch (Exception e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public List<AssetRedeemPointWalletTransaction> getTransactions(BalanceType balanceType, TransactionType transactionType, int max, int offset, String assetPublicKey) throws CantGetTransactionsException {
        try {
            return assetRedeemPointWalletDao.listsTransactionsByAssets(balanceType, transactionType, max, offset, assetPublicKey);
        } catch (CantGetTransactionsException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public List<AssetRedeemPointWalletTransaction> getTransactionsForDisplay(String assetPublicKey) throws CantGetTransactionsException {
        List<AssetRedeemPointWalletTransaction> creditAvailable = getTransactions(BalanceType.AVAILABLE, TransactionType.CREDIT, assetPublicKey);
        List<AssetRedeemPointWalletTransaction> creditBook = getTransactions(BalanceType.BOOK, TransactionType.CREDIT, assetPublicKey);
        List<AssetRedeemPointWalletTransaction> debitAvailable = getTransactions(BalanceType.AVAILABLE, TransactionType.DEBIT, assetPublicKey);
        List<AssetRedeemPointWalletTransaction> debitBook = getTransactions(BalanceType.BOOK, TransactionType.DEBIT, assetPublicKey);
        List<AssetRedeemPointWalletTransaction> toReturn = new ArrayList<>();
        toReturn.addAll(getTransactionsForDisplay(creditAvailable, creditBook));
        toReturn.addAll(getTransactionsForDisplay(debitBook, debitAvailable));
        Collections.sort(toReturn, new Comparator<AssetRedeemPointWalletTransaction>() {
            @Override
            public int compare(AssetRedeemPointWalletTransaction o1, AssetRedeemPointWalletTransaction o2) {
                return (int) (o2.getTimestamp() - o1.getTimestamp());
            }
        });
        return toReturn;
    }

    private List<AssetRedeemPointWalletTransaction> getTransactionsForDisplay(List<AssetRedeemPointWalletTransaction> available, List<AssetRedeemPointWalletTransaction> book) {
        for (AssetRedeemPointWalletTransaction transaction : book) {
            if (!available.contains(transaction)) {
                available.add(transaction);
            }
        }
        return available;
    }

    @Override
    public List<AssetRedeemPointWalletTransaction> getTransactions(BalanceType balanceType, TransactionType transactionType, String assetPublicKey) throws CantGetTransactionsException {
        try {
            return assetRedeemPointWalletDao.listsTransactionsByAssets(balanceType, transactionType, assetPublicKey);
        } catch (CantGetTransactionsException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public List<AssetRedeemPointWalletTransaction> getTransactionsByActor(String actorPublicKey, BalanceType balanceType, int max, int offset) throws CantGetTransactionsException {
        try {
            return assetRedeemPointWalletDao.getTransactionsByActor(actorPublicKey, balanceType, max, offset);
        } catch (CantGetTransactionsException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public List<AssetRedeemPointWalletTransaction> gettLastActorTransactionsByTransactionType(BalanceType balanceType, TransactionType transactionType, int max, int offset) throws CantGetTransactionsException {
        try {
            return assetRedeemPointWalletDao.getTransactionsByTransactionType(transactionType, max, offset);
        } catch (CantGetTransactionsException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantGetTransactionsException(CantGetTransactionsException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public void setTransactionDescription(UUID transactionID, String description) throws CantFindTransactionException, CantStoreMemoException {
        try {
            assetRedeemPointWalletDao.updateMemoField(transactionID, description);
        } catch (CantStoreMemoException e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw e;
        } catch (Exception e) {
            assetRedeemPointWalletPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStoreMemoException(CantStoreMemoException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
    }

    @Override
    public AssetRedeemPointWalletTransactionSummary getActorTransactionSummary(String actorPublicKey, BalanceType balanceType) throws CantGetActorTransactionSummaryException {
        return null;
    }


    @Override
    public DigitalAssetMetadata getDigitalAssetMetadata(String genesisTransaction) throws CantGetDigitalAssetFromLocalStorageException {
        return assetRedeemPointWalletDao.getDigitalAssetMetadata(genesisTransaction);
    }

    @Override
    public void newAssetRedeemed(DigitalAssetMetadata digitalAssetMetadata, String userPublicKey) throws CantSaveRedeemPointStatisticException {
        assetRedeemPointWalletDao.newAssetRedeemed(digitalAssetMetadata, userPublicKey);
    }

    @Override
    public List<RedeemPointStatistic> getAllStatistics() throws CantGetRedeemPointStatisticsException, RecordsNotFoundException {
        return assetRedeemPointWalletDao.getAllStatistics();
    }

    @Override
    public List<RedeemPointStatistic> getStatisticsByUser(String userPublicKey) throws CantGetRedeemPointStatisticsException, RecordsNotFoundException {
        return assetRedeemPointWalletDao.getStatisticsForUser(userPublicKey);
    }

    @Override
    public List<RedeemPointStatistic> getStatisticsByAssetPublicKey(String assetPk) throws CantGetRedeemPointStatisticsException, RecordsNotFoundException {
        return assetRedeemPointWalletDao.getStatisticsForAsset(assetPk);
    }

    @Override
    public List<RedeemPointStatistic> getStatisticsByAssetAndUser(String assetPk, String userPk) throws CantGetRedeemPointStatisticsException, RecordsNotFoundException {
        return assetRedeemPointWalletDao.getStatisticForAssetAndUser(userPk, assetPk);
    }
}
