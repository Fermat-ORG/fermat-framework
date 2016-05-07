package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantLoadTableToMemoryException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantUpdateRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
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
import org.fermat.fermat_dap_api.layer.all_definition.network_service_message.exceptions.CantSendMessageException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUserManager;
import org.fermat.fermat_dap_api.layer.dap_network_services.asset_transmission.exceptions.CantSendDigitalAssetMetadataException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_transfer.exceptions.CantTransferDigitalAssetsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantStartDeliveringException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.RecordsNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.TransactionAlreadyDeliveringException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetSwap;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantCheckTransferProgressException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.structure.database.AssetTransferDAO;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Víctor Mars (marsvicam@gmail.com) on 08/01/16.
 */
public class DigitalAssetTransferer extends AbstractDigitalAssetSwap {

    final String LOCAL_STORAGE_PATH = "digital-asset-transfer/";
    ActorAssetUserManager actorAssetUserManager;
    AssetVaultManager assetVaultManager;
    ErrorManager errorManager;
    String digitalAssetFileStoragePath;
    //String digitalAssetMetadataFileStoragePath;
    AssetTransferDAO assetDistributionDao;
    DigitalAssetTransferVault digitalAssetTransferVault;

    public DigitalAssetTransferer(AssetVaultManager assetVaultManager, ErrorManager errorManager, UUID pluginId, PluginFileSystem pluginFileSystem, BitcoinNetworkManager bitcoinNetworkManager) {
        super(pluginId, pluginFileSystem);
        this.setBitcoinCryptoNetworkManager(bitcoinNetworkManager);
        this.errorManager = errorManager;
        this.assetVaultManager = assetVaultManager;
    }

    public void setAssetTransferDao(AssetTransferDAO assetTransferDAO) throws CantSetObjectException {
        if (assetTransferDAO == null) {
            throw new CantSetObjectException("assetDistributionDao is null");
        }
        this.assetDistributionDao = assetTransferDAO;
    }

    public void setDigitalAssetTransferVault(DigitalAssetTransferVault digitalAssetTransferVault) throws CantSetObjectException {
        if (digitalAssetTransferVault == null) {
            throw new CantSetObjectException("digitalAssetDistributionVault is null");
        }
        this.digitalAssetTransferVault = digitalAssetTransferVault;
    }

    public void setWalletPublicKey(String walletPublicKey) throws CantSetObjectException {
        this.digitalAssetTransferVault.setWalletPublicKey(walletPublicKey);
    }

    public void setActorAssetUserManager(ActorAssetUserManager actorAssetUserManager) throws org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantGetActorAssetIssuerException {
        this.actorAssetUserManager = actorAssetUserManager;
    }

    /**
     * This method check if the DigitalAssetMetadata remains with not modifications
     */
    public void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException {
        try {
            String genesisTransactionFromDigitalAssetMetadata = digitalAssetMetadata.getGenesisTransaction();
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH, genesisTransactionFromDigitalAssetMetadata);
            String digitalAssetMetadataHash = digitalAssetMetadata.getDigitalAssetHash();
            List<CryptoTransaction> cryptoTransactionList = bitcoinNetworkManager.getCryptoTransactions(genesisTransactionFromDigitalAssetMetadata);
            if (cryptoTransactionList == null || cryptoTransactionList.isEmpty()) {
                throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, null, "Getting the genesis transaction from Crypto Network", "The crypto transaction received is null");
            }
            //This won't work until I can get the CryptoTransaction from AssetVault
            String op_ReturnFromAssetVault = cryptoTransaction.getOp_Return();
            if (!digitalAssetMetadataHash.equals(op_ReturnFromAssetVault)) {
                throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException("Cannot deliver Digital Asset because the " +
                        "Hash was modified:\n" +
                        "Op_return:" + op_ReturnFromAssetVault + "\n" +
                        "digitalAssetMetadata:" + digitalAssetMetadata);
            }
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.HASH_CHECKED, genesisTransactionFromDigitalAssetMetadata);
        } catch (CantGetCryptoTransactionException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception,
                    "Delivering the Digital Asset \n" + digitalAssetMetadata,
                    "Cannot get the genesis transaction from Asset vault");
        } catch (CantExecuteQueryException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception,
                    "Delivering the Digital Asset \n" + digitalAssetMetadata,
                    "Cannot execute a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception,
                    "Delivering the Digital Asset \n" + digitalAssetMetadata,
                    "Unexpected result in database");
        }
    }

    /**
     * This method will deliver the DigitalAssetMetadata to ActorAssetUser
     */
    public void deliverDigitalAssetToRemoteDevice(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException {
        try {
            //First, I going to persist in database the basic information about digitalAssetMetadata
            System.out.println("ASSET TRANSFER begins for persisted");
            DigitalAsset digitalAsset = digitalAssetMetadata.getDigitalAsset();
            System.out.println("ASSET TRANSFER Digital Asset genesis address: " + digitalAsset.getGenesisAddress());
            String genesisTransaction = digitalAssetMetadata.getGenesisTransaction();
            System.out.println("ASSET TRANSFER Genesis transaction:" + genesisTransaction);
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_AVAILABLE_BALANCE, genesisTransaction);
            if (!isAvailableBalanceInAssetVault(digitalAsset.getGenesisAmount(), genesisTransaction)) {
                System.out.println("ASSET TRANSFER The Available balance in asset vault is insufficient - genesisAmount:" + digitalAsset.getGenesisAmount());
                throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException("The Available balance in asset vault is incorrect");
            }
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.AVAILABLE_BALANCE_CHECKED, genesisTransaction);
            DigitalAssetContract digitalAssetContract = digitalAsset.getContract();
            System.out.println("ASSET TRANSFER Digital Asset contract: " + digitalAssetContract);
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_CONTRACT, genesisTransaction);
            if (!isValidContract(digitalAssetContract)) {
                System.out.println("ASSET TRANSFER The contract is not valid");
                throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException("The DigitalAsset Contract is not valid, the expiration date has passed");
            }
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CONTRACT_CHECKED, genesisTransaction);
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.CHECKING_HASH, genesisTransaction);
            if (!isDigitalAssetHashValid(digitalAssetMetadata)) {
                System.out.println("ASSET TRANSFER The DAM Hash is not valid");
                throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException("The DigitalAsset hash is not valid");
            }
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.HASH_CHECKED, genesisTransaction);
            String newTx = assetVaultManager.createBitcoinTransaction(digitalAssetMetadata.getLastTransactionHash(), actorAssetUser.getCryptoAddress(), digitalAssetMetadata.getNetworkType());
            digitalAssetMetadata = digitalAssetTransferVault.updateMetadataTransactionChain(genesisTransaction, newTx, null, digitalAssetMetadata.getNetworkType());
            System.out.println("ASSET TRANSFER set debit in asset issuer wallet:" + genesisTransaction);
            cryptoTransaction = foundCryptoTransaction(digitalAssetMetadata);
            digitalAssetTransferVault.updateWalletBalance(digitalAssetMetadata, cryptoTransaction, BalanceType.AVAILABLE, TransactionType.DEBIT, DAPTransactionType.RECEPTION, actorAssetUser.getActorPublicKey(), Actors.DAP_ASSET_USER, WalletUtilities.DEFAULT_MEMO_DISTRIBUTION);
            System.out.println("ASSET TRANSFER Begins the deliver to an remote actor");
            deliverToRemoteActor(digitalAssetMetadata, actorAssetUser, cryptoTransaction.getBlockchainNetworkType());
        } catch (CantPersistDigitalAssetException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot persist digital asset into database");
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot persist digital asset into local storage");
        } catch (CantGetCryptoTransactionException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot get the genesisTransaction from Asset Vault");
        } catch (CantExecuteQueryException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot execute a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Unexpected result in database");
        } catch (CantSendDigitalAssetMetadataException | CantCreateBitcoinTransactionException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception, "Delivering digital assets", "There is an error delivering the digital asset through the network layer");
        } catch (DAPException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Generic DAP Exception");
        } catch (CantGetTransactionsException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot get the genesis transaction from crypto network");
        } catch (CantLoadWalletException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot load Asset issuer wallet");
        } catch (CantRegisterDebitException | CantRegisterCreditException exception) {
            throw new org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException(exception, "Delivering digital assets", "Cannot register a debit in Asset Issuer Wallet");
        }
    }


    private void deliverToRemoteActor(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser remoteActorAssetUser, BlockchainNetworkType networkType) throws CantSendDigitalAssetMetadataException {
        String genesisTransaction;
        try {
            System.out.println("ASSET TRANSFER Preparing delivering to remote actor");
            genesisTransaction = digitalAssetMetadata.getGenesisTransaction();
            System.out.println("ASSET TRANSFER Delivering genesis transaction " + genesisTransaction);
            this.assetDistributionDao.updateDistributionStatusByGenesisTransaction(DistributionStatus.DELIVERING, genesisTransaction);
            System.out.println("ASSET TRANSFER Sender Actor name: " + actorAssetUserManager.getActorAssetUser().getName());
            System.out.println("ASSET TRANSFER Before deliver - remote asset user ");
            assetDistributionDao.startDelivering(digitalAssetMetadata.getGenesisTransaction(), digitalAssetMetadata.getDigitalAsset().getPublicKey(), remoteActorAssetUser.getActorPublicKey(), networkType);
            assetTransmissionNetworkServiceManager.sendMessage(new DAPMessage(new AssetMetadataContentMessage(digitalAssetMetadata), actorAssetUserManager.getActorAssetUser(), remoteActorAssetUser, DAPMessageSubject.ASSET_RECEPTION));
        } catch (CantExecuteQueryException | CantStartDeliveringException exception) {
            throw new CantSendDigitalAssetMetadataException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE, exception, "Delivering Digital Asset Metadata to Remote Actor", "There is an error executing a query in database");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantSendDigitalAssetMetadataException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE, exception, "Delivering Digital Asset Metadata to Remote Actor", "The database return an unexpected result");
        } catch (CantGetAssetUserActorsException | CantSetObjectException | CantSendMessageException e) {
            e.printStackTrace();
        }
    }

    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        System.out.println("ASSET TRANSFER Persist DAM: " + digitalAssetMetadata.getGenesisTransaction());
        setDigitalAssetLocalFilePath(digitalAssetMetadata);
        CryptoAddress cryptoAddress = actorAssetUser.getCryptoAddress();
        String actorAddress;
        if (cryptoAddress == null) {
            actorAddress = "UNDEFINED";
            System.out.println("ASSET TRANSFER Harcoding Actor address because is null");
        } else {
            actorAddress = cryptoAddress.getAddress();
        }
        this.assetDistributionDao.persistDigitalAsset(
                digitalAssetMetadata.getGenesisTransaction(),
                this.digitalAssetFileStoragePath,
                digitalAssetMetadata.getDigitalAssetHash(),
                actorAssetUser.getActorPublicKey(),
                actorAddress);
        System.out.println("ASSET TRANSFER registered in database");
    }

    public void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException {
        System.out.println("ASSET TRANSFER Internal Id: " + digitalAssetMetadata.getGenesisTransaction());
        this.digitalAssetTransferVault.persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata, digitalAssetMetadata.getLastTransactionHash());
    }

    public void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata) {
        this.digitalAssetFileStoragePath = this.LOCAL_STORAGE_PATH + "/" + digitalAssetMetadata.getDigitalAssetHash();
        this.digitalAssetTransferVault.setDigitalAssetLocalFilePath(this.digitalAssetFileStoragePath);
    }

    public void transferAssets(HashMap<DigitalAssetMetadata, ActorAssetUser> digitalAssetsToDistribute) throws CantTransferDigitalAssetsException {
        String context = "Map: " + digitalAssetsToDistribute;
        try {
            System.out.println("ASSET TRANSFER DistributionMap size:" + digitalAssetsToDistribute.size());
            for (Map.Entry<DigitalAssetMetadata, ActorAssetUser> entry : digitalAssetsToDistribute.entrySet()) {
                DigitalAssetMetadata digitalAssetMetadata = entry.getKey();
                ActorAssetUser actorAssetUser = entry.getValue();
                try {

                    if (assetDistributionDao.isDeliveringGenesisTransaction(digitalAssetMetadata.getGenesisTransaction())) {
                        throw new TransactionAlreadyDeliveringException(null, context, "This genesis transaction is already being delivered, chill.");
                    }
                    if (assetDistributionDao.isFirstTransaction(digitalAssetMetadata.getGenesisTransaction())) {
                        System.out.println("ASSET TRANSFER IS FIRST TRANSACTION");
                        persistDigitalAsset(digitalAssetMetadata, actorAssetUser);
                    } else {
                        System.out.println("ASSET TRANSFER IS NOT FIRST TRANSACTION");
                        assetDistributionDao.updateActorAssetUser(actorAssetUser, digitalAssetMetadata.getGenesisTransaction());
                    }
                } catch (TransactionAlreadyDeliveringException | CantPersistDigitalAssetException | CantCreateDigitalAssetFileException | CantCheckTransferProgressException | RecordsNotFoundException | CantUpdateRecordException | CantLoadTableToMemoryException e) {
                    this.errorManager.reportUnexpectedPluginException(Plugins.ASSET_TRANSFER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
                    throw new CantTransferDigitalAssetsException(e, context, "Something bad happen.");
                }
                //Deliver one DigitalAsset
                System.out.println("ASSET TRANSFER DAM-Hash:" + digitalAssetMetadata.getDigitalAssetHash());
                System.out.println("ASSET TRANSFER ActorAssetUser - PublicKey:" + actorAssetUser.getActorPublicKey());
                System.out.println("ASSET TRANSFER ActorAssetUser - Name:" + actorAssetUser.getName());
                deliverDigitalAssetToRemoteDevice(digitalAssetMetadata, actorAssetUser);
            }
        } catch (org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_transfer.developer.version_1.exceptions.CantDeliverDigitalAssetException exception) {
            this.errorManager.reportUnexpectedPluginException(Plugins.ASSET_TRANSFER, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, exception);
            throw new CantTransferDigitalAssetsException(exception, context, "Something bad happen.");
        }

    }
}
