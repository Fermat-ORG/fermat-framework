package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;

import com.bitdubai.fermat_bch_api.layer.crypto_network.manager.BlockchainManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.exceptions.CantCreateBitcoinTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPMessageSubject;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.DAPMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.content_message.AssetMetadataContentMessage;
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendDAPMessageException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartDeliveringException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.TransactionAlreadyDeliveringException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetSwap;
import org.fermat.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.UserRedemptionDigitalAssetTransactionPluginRoot;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.developer.version_1.structure.database.UserRedemptionDao;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 02/11/15.
 */
public class UserRedemptionRedeemer extends AbstractDigitalAssetSwap {

    private ActorAssetUser actorAssetUser;
    private String digitalAssetFileStoragePath;
    private UserRedemptionDao userRedemptionDao;
    private DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault;
    private ActorAssetUserManager actorAssetUserManager;
    UserRedemptionDigitalAssetTransactionPluginRoot userRedemptionDigitalAssetTransactionPluginRoot;

    public UserRedemptionRedeemer(UserRedemptionDigitalAssetTransactionPluginRoot userRedemptionDigitalAssetTransactionPluginRoot,
                                  UUID pluginId,
                                  PluginFileSystem pluginFileSystem,
                                  AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager,
                                  UserRedemptionDao userRedemptionDao,
                                  DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault,
                                  BlockchainManager bitcoinNetworkManager,
                                  ActorAssetUserManager actorAssetUserManager,
                                  AssetVaultManager assetVaultManager) throws CantExecuteDatabaseOperationException, CantGetAssetUserActorsException, CantSetObjectException {

        super(pluginId, pluginFileSystem);
        this.userRedemptionDigitalAssetTransactionPluginRoot = userRedemptionDigitalAssetTransactionPluginRoot;
        setAssetVaultManager(assetVaultManager);
        setAssetTransmissionNetworkServiceManager(assetTransmissionNetworkServiceManager);
        setUserRedemptionDao(userRedemptionDao);
        setDigitalAssetUserRedemptionVault(digitalAssetUserRedemptionVault);
        setBitcoinCryptoNetworkManager(bitcoinNetworkManager);
        setActorAssetUserManager(actorAssetUserManager);
    }

    public void setUserRedemptionDao(UserRedemptionDao userRedemptionDao) throws CantSetObjectException {
        this.userRedemptionDao = userRedemptionDao;
    }

    public void setDigitalAssetUserRedemptionVault(DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault) throws CantSetObjectException {
        this.digitalAssetUserRedemptionVault = digitalAssetUserRedemptionVault;
    }

    public void setWalletPublicKey(String walletPublicKey) throws CantSetObjectException {
        this.digitalAssetUserRedemptionVault.setWalletPublicKey(walletPublicKey);
    }

    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) throws CantGetAssetUserActorsException {
        this.actorAssetUserManager = actorAssetUserManager;
    }

    /**
     * This method check if the DigitalAssetMetadata remains with not modifications
     */
    public void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws CantRedeemDigitalAssetException {
        try {
            String genesisTransactionFromDigitalAssetMetadata = digitalAssetMetadata.getGenesisTransaction();
            this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH, genesisTransactionFromDigitalAssetMetadata);
            String digitalAssetMetadataHash = digitalAssetMetadata.getDigitalAssetHash();
            List<CryptoTransaction> cryptoTransactionList = bitcoinNetworkManager.getCryptoTransactions(genesisTransactionFromDigitalAssetMetadata);
            if (cryptoTransactionList == null || cryptoTransactionList.isEmpty()) {
                throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, null, "Getting the genesis transaction from Crypto Network", "The crypto transaction received is null");
            }
            //This won't work until I can get the CryptoTransaction from AssetVault
            String op_ReturnFromAssetVault = cryptoTransaction.getOp_Return();
            if (!digitalAssetMetadataHash.equals(op_ReturnFromAssetVault)) {
                throw new CantRedeemDigitalAssetException("Cannot redeem Digital Asset because the " +
                        "Hash was modified:\n" +
                        "Op_return:" + op_ReturnFromAssetVault + "\n" +
                        "digitalAssetMetadata:" + digitalAssetMetadata);
            }
            this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.HASH_CHECKED, genesisTransactionFromDigitalAssetMetadata);
        } catch (CantGetCryptoTransactionException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e,
                    "Delivering the Digital Asset \n" + digitalAssetMetadata,
                    "Cannot get the genesis transaction from Asset vault");
        } catch (CantExecuteQueryException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e,
                    "Delivering the Digital Asset \n" + digitalAssetMetadata,
                    "Cannot execute a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e,
                    "Delivering the Digital Asset \n" + digitalAssetMetadata,
                    "Unexpected result in database");
        }
    }

    /**
     * This method will deliver the DigitalAssetMetadata to ActorAssetUser
     */
    public void deliverDigitalAssetToRemoteDevice(Map<DigitalAssetMetadata, ActorAssetRedeemPoint> toRedeem, String walletPublicKey) throws CantRedeemDigitalAssetException {
        String context = "toRedeem: " + toRedeem + " - Wallet: " + walletPublicKey;
        try {
            for (Map.Entry<DigitalAssetMetadata, ActorAssetRedeemPoint> redeem : toRedeem.entrySet()) {
                DigitalAssetMetadata digitalAssetMetadata = redeem.getKey();
                ActorAssetRedeemPoint actorAssetRedeemPoint = redeem.getValue();
                if (userRedemptionDao.isDeliveringGenesisTransaction(digitalAssetMetadata.getGenesisTransaction())) {
                    throw new TransactionAlreadyDeliveringException(null, context, "This genesis transaction is already being delivered, chill.");
                }
                if (userRedemptionDao.isFirstTransaction(digitalAssetMetadata.getGenesisTransaction())) {
                    System.out.println("ASSET USER REDEMPTION begins for " + actorAssetRedeemPoint.getActorPublicKey());
                    persistDigitalAsset(digitalAssetMetadata, actorAssetRedeemPoint);
                } else {
                    System.out.println("ASSET REDEMPTION IS NOT FIRST TRANSACTION");
                    userRedemptionDao.updateActorAssetRedeemPoint(actorAssetRedeemPoint, digitalAssetMetadata.getGenesisTransaction());
                }
                //First, I going to persist in database the basic information about digitalAssetMetadata
                System.out.println("ASSET USER REDEMPTION begins for persisted");
                //Now, I'll check is Hash wasn't modified
                //checkDigitalAssetMetadata(digitalAssetMetadata);
                DigitalAsset digitalAsset = digitalAssetMetadata.getDigitalAsset();
                System.out.println("ASSET USER REDEMPTION Digital Asset genesis address: " + digitalAsset.getGenesisAddress());
                String genesisTransaction = digitalAssetMetadata.getGenesisTransaction();
                System.out.println("ASSET USER REDEMPTION Genesis transaction:" + genesisTransaction);
                this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_AVAILABLE_BALANCE, genesisTransaction);
                if (!isAvailableBalanceInAssetVault(digitalAsset.getGenesisAmount(), genesisTransaction)) {
                    System.out.println("ASSET USER REDEMPTION The Available balance in asset vault is insufficient - genesisAmount:" + digitalAsset.getGenesisAmount());
                    throw new CantRedeemDigitalAssetException("The Available balance in asset vault is incorrect");
                }
                this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.AVAILABLE_BALANCE_CHECKED, genesisTransaction);
                DigitalAssetContract digitalAssetContract = digitalAsset.getContract();
                System.out.println("ASSET USER REDEMPTION Digital Asset contract: " + digitalAssetContract);
                this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_CONTRACT, genesisTransaction);
                if (!isValidContract(digitalAssetContract)) {
                    System.out.println("ASSET USER REDEMPTION The contract is not valid");
                    throw new CantRedeemDigitalAssetException("The DigitalAsset Contract is not valid, the expiration date has passed");
                }
                this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CONTRACT_CHECKED, genesisTransaction);
                this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH, genesisTransaction);
                if (!isDigitalAssetHashValid(digitalAssetMetadata)) {
                    System.out.println("ASSET USER REDEMPTION The DAM Hash is not valid");
                    throw new CantRedeemDigitalAssetException("The DigitalAsset hash is not valid");
                }
                this.userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.HASH_CHECKED, genesisTransaction);
                System.out.println("ASSET USER REDEMPTION set debit in asset user wallet:" + genesisTransaction);
                actorAssetUser = actorAssetUserManager.getActorAssetUser();
                String newTx = assetVaultManager.createBitcoinTransaction(digitalAssetMetadata.getLastTransactionHash(), actorAssetRedeemPoint.getCryptoAddress(), digitalAssetMetadata.getNetworkType());
                digitalAssetMetadata = digitalAssetUserRedemptionVault.updateMetadataTransactionChain(genesisTransaction, newTx, null, digitalAssetMetadata.getNetworkType());
                cryptoTransaction = foundCryptoTransaction(digitalAssetMetadata);
                digitalAssetUserRedemptionVault.updateWalletBalance(digitalAssetMetadata, this.cryptoTransaction, BalanceType.AVAILABLE, TransactionType.DEBIT, DAPTransactionType.RECEPTION, actorAssetRedeemPoint.getActorPublicKey(), Actors.DAP_ASSET_REDEEM_POINT, WalletUtilities.DEFAULT_MEMO_REDEMPTION);
                System.out.println("ASSET USER REDEMPTION Begins the deliver to an remote actor");
                deliverToRemoteActor(digitalAssetMetadata, actorAssetRedeemPoint, cryptoTransaction.getBlockchainNetworkType());
            }
        } catch (CantPersistDigitalAssetException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e, "Delivering digital assets", "Cannot persist digital asset into database");
        } catch (CantCreateDigitalAssetFileException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e, "Delivering digital assets", "Cannot persist digital asset into local storage");
        } catch (CantGetCryptoTransactionException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e, "Delivering digital assets", "Cannot get the genesisTransaction from Asset Vault");
        } catch (CantExecuteQueryException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e, "Delivering digital assets", "Cannot execute a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e, "Delivering digital assets", "Unexpected result in database");
        } catch (CantSendDigitalAssetMetadataException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e, "Delivering digital assets", "There is an error delivering the digital asset through the network layer");
        } catch (DAPException | CantUpdateRecordException | CantLoadTableToMemoryException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e, "Delivering digital assets", "Generic DAP Exception");
        } catch (CantGetTransactionsException | CantCreateBitcoinTransactionException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e, "Delivering digital assets", "Cannot get the genesis transaction from crypto network");
        } catch (CantLoadWalletException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e, "Delivering digital assets", "Cannot load Asset issuer wallet");
        } catch (CantRegisterDebitException | CantRegisterCreditException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantRedeemDigitalAssetException(e, "Delivering digital assets", "Cannot register a debit in Asset Issuer Wallet");
        }
    }


    private void deliverToRemoteActor(DigitalAssetMetadata digitalAssetMetadata, ActorAssetRedeemPoint actorAssetRedeemPoint, BlockchainNetworkType networkType) throws CantSendDigitalAssetMetadataException {
        String genesisTransaction;
        try {
            System.out.println("ASSET USER REDEMPTION Preparing delivering to remote actor");
            genesisTransaction = digitalAssetMetadata.getGenesisTransaction();
            System.out.println("ASSET USER REDEMPTION Delivering genesis transaction " + genesisTransaction);
            userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.DELIVERING, genesisTransaction);
            System.out.println("ASSET USER REDEMPTION Sender Actor name " + actorAssetRedeemPoint.getName());
            System.out.println("ASSET USER REDEMPTION Before deliver - remote asset user ");
            userRedemptionDao.startDelivering(digitalAssetMetadata.getGenesisTransaction(), digitalAssetMetadata.getDigitalAsset().getPublicKey(), actorAssetRedeemPoint.getActorPublicKey(), networkType);
            assetTransmissionNetworkServiceManager.sendMessage(new DAPMessage(UUID.randomUUID(), new AssetMetadataContentMessage(digitalAssetMetadata), actorAssetUser, actorAssetRedeemPoint, DAPMessageSubject.REDEEM_POINT_REDEMPTION));
        } catch (CantExecuteQueryException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantSendDigitalAssetMetadataException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE, e, "Delivering Digital Asset Metadata to Remote Actor", "There is an error executing a query in database");
        } catch (UnexpectedResultReturnedFromDatabaseException | CantSetObjectException | CantStartDeliveringException | CantSendDAPMessageException e) {
            userRedemptionDigitalAssetTransactionPluginRoot.reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantSendDigitalAssetMetadataException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE, e, "Delivering Digital Asset Metadata to Remote Actor", "The database return an unexpected result");
        }
    }

    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetRedeemPoint actorAssetRedeemPoint) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        System.out.println("ASSET USER REDEMPTION Persist DAM: " + digitalAssetMetadata.getGenesisTransaction());
        setDigitalAssetLocalFilePath(digitalAssetMetadata);
        CryptoAddress cryptoAddress = actorAssetRedeemPoint.getCryptoAddress();
        String actorAddress;
        if (cryptoAddress == null) {
            actorAddress = "UNDEFINED";
            System.out.println("ASSET USER REDEMPTION Harcoding Actor address because is null");
        } else {
            actorAddress = cryptoAddress.getAddress();
        }
        this.userRedemptionDao.persistDigitalAsset(
                digitalAssetMetadata.getGenesisTransaction(),
                this.digitalAssetFileStoragePath,
                digitalAssetMetadata.getDigitalAssetHash(),
                actorAssetRedeemPoint.getActorPublicKey(),
                actorAddress);
        System.out.println("ASSET USER REDEMPTION registered in database");
    }

    public void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException {
        this.digitalAssetUserRedemptionVault.persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata, digitalAssetMetadata.getGenesisTransaction());
    }

    public void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata) {
        String LOCAL_STORAGE_PATH = "user-redemption/";
        this.digitalAssetFileStoragePath = LOCAL_STORAGE_PATH + "/" + digitalAssetMetadata.getGenesisTransaction();
        this.digitalAssetUserRedemptionVault.setDigitalAssetLocalFilePath(this.digitalAssetFileStoragePath);
    }

}
