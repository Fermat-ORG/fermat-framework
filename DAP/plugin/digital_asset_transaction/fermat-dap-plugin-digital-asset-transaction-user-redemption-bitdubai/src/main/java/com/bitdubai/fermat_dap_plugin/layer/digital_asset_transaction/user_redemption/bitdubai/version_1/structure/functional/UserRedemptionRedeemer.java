package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DistributionStatus;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;
import com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.interfaces.AssetTransmissionNetworkServiceManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistsTransactionUUIDException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartDeliveringException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.TransactionAlreadyDeliveringException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetSwap;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.user_redemption.exceptions.CantRedeemDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure.database.UserRedemptionDao;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

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

    public UserRedemptionRedeemer(ErrorManager errorManager, UUID pluginId, PluginFileSystem pluginFileSystem,
                                  AssetTransmissionNetworkServiceManager assetTransmissionNetworkServiceManager,
                                  UserRedemptionDao userRedemptionDao,
                                  DigitalAssetUserRedemptionVault digitalAssetUserRedemptionVault,
                                  BitcoinNetworkManager bitcoinNetworkManager,
                                  ActorAssetUserManager actorAssetUserManager,
                                  AssetVaultManager assetVaultManager) throws CantExecuteDatabaseOperationException, CantGetAssetUserActorsException, CantSetObjectException {
        super(pluginId, pluginFileSystem);
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
        } catch (CantGetCryptoTransactionException exception) {
            throw new CantRedeemDigitalAssetException(exception,
                    "Delivering the Digital Asset \n" + digitalAssetMetadata,
                    "Cannot get the genesis transaction from Asset vault");
        } catch (CantExecuteQueryException exception) {
            throw new CantRedeemDigitalAssetException(exception,
                    "Delivering the Digital Asset \n" + digitalAssetMetadata,
                    "Cannot execute a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantRedeemDigitalAssetException(exception,
                    "Delivering the Digital Asset \n" + digitalAssetMetadata,
                    "Unexpected result in database");
        }
    }

    @Override
    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        //To implement
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
                cryptoTransaction = foundCryptoTransaction(digitalAssetMetadata);
                actorAssetUser = actorAssetUserManager.getActorAssetUser();
                digitalAssetUserRedemptionVault.updateWalletBalance(digitalAssetMetadata, this.cryptoTransaction, BalanceType.AVAILABLE, TransactionType.DEBIT, DAPTransactionType.RECEPTION, actorAssetRedeemPoint.getActorPublicKey());
                System.out.println("ASSET USER REDEMPTION Begins the deliver to an remote actor");
                deliverToRemoteActor(digitalAssetMetadata, actorAssetRedeemPoint);
            }
        } catch (CantPersistDigitalAssetException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot persist digital asset into database");
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot persist digital asset into local storage");
        } catch (CantGetCryptoTransactionException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot get the genesisTransaction from Asset Vault");
        } catch (CantExecuteQueryException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot execute a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Unexpected result in database");
        } catch (CantSendDigitalAssetMetadataException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "There is an error delivering the digital asset through the network layer");
        } catch (DAPException | CantUpdateRecordException | CantLoadTableToMemoryException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Generic DAP Exception");
        } catch (CantGetTransactionsException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot get the genesis transaction from crypto network");
        } catch (CantLoadWalletException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot load Asset issuer wallet");
        } catch (CantRegisterDebitException | CantRegisterCreditException exception) {
            throw new CantRedeemDigitalAssetException(exception, "Delivering digital assets", "Cannot register a debit in Asset Issuer Wallet");
        }
    }


    private void deliverToRemoteActor(DigitalAssetMetadata digitalAssetMetadata, ActorAssetRedeemPoint actorAssetRedeemPoint) throws CantSendDigitalAssetMetadataException {
        String genesisTransaction;
        try {
            System.out.println("ASSET USER REDEMPTION Preparing delivering to remote actor");
            genesisTransaction = digitalAssetMetadata.getGenesisTransaction();
            System.out.println("ASSET USER REDEMPTION Delivering genesis transaction " + genesisTransaction);
            userRedemptionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.DELIVERING, genesisTransaction);
            System.out.println("ASSET USER REDEMPTION Sender Actor name " + actorAssetRedeemPoint.getName());
            System.out.println("ASSET USER REDEMPTION Before deliver - remote asset user ");
            userRedemptionDao.startDelivering(digitalAssetMetadata.getGenesisTransaction(), digitalAssetMetadata.getDigitalAsset().getPublicKey(), actorAssetRedeemPoint.getActorPublicKey());
            assetTransmissionNetworkServiceManager.sendDigitalAssetMetadata(actorAssetUser, actorAssetRedeemPoint, digitalAssetMetadata);
        } catch (CantExecuteQueryException exception) {
            throw new CantSendDigitalAssetMetadataException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE, exception, "Delivering Digital Asset Metadata to Remote Actor", "There is an error executing a query in database");
        } catch (UnexpectedResultReturnedFromDatabaseException | CantStartDeliveringException exception) {
            throw new CantSendDigitalAssetMetadataException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE, exception, "Delivering Digital Asset Metadata to Remote Actor", "The database return an unexpected result");
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
        persistInLocalStorage(digitalAssetMetadata);
    }

    @Override
    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, String senderId) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        //To implement
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
