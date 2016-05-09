package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.ReceptionStatus;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantPersistDigitalAssetException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetSwap;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.exceptions.CantReceiveDigitalAssetException;
import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure.database.AssetReceptionDao;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 10/10/15.
 */
public class DigitalAssetReceptor extends AbstractDigitalAssetSwap {

    ErrorManager errorManager;
    final String LOCAL_STORAGE_PATH = "digital-asset-reception/";
    String digitalAssetFileStoragePath;
    AssetReceptionDao assetReceptionDao;
    BitcoinNetworkManager bitcoinNetworkManager;

    DigitalAssetReceptionVault digitalAssetReceptionVault;

    public DigitalAssetReceptor(ErrorManager errorManager,
                                UUID pluginId,
                                PluginFileSystem pluginFileSystem,
                                BitcoinNetworkManager bitcoinNetworkManager,
                                DigitalAssetReceptionVault digitalAssetReceptionVault,
                                AssetReceptionDao assetReceptionDao) throws CantExecuteDatabaseOperationException {
        super(pluginId, pluginFileSystem);
        this.bitcoinNetworkManager = bitcoinNetworkManager;
        this.setBitcoinCryptoNetworkManager(this.bitcoinNetworkManager);
        this.errorManager = errorManager;
        this.digitalAssetReceptionVault = digitalAssetReceptionVault;
        this.assetReceptionDao = assetReceptionDao;
    }

    public void receiveDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata, String senderId, Actors actorType) throws CantReceiveDigitalAssetException {
        try {
            persistDigitalAsset(digitalAssetMetadata, senderId, actorType);
            verifyAsset(digitalAssetMetadata);
        } catch (CantPersistDigitalAssetException exception) {
            throw new CantReceiveDigitalAssetException(exception, "Receiving Digital Asset Metadata", "Cannot persist Digital Asset Metadata");
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new CantReceiveDigitalAssetException(exception, "Receiving Digital Asset Metadata", "Cannot create Digital Asset Metadata file in local storage");
        }
    }

    public void verifyAsset(DigitalAssetMetadata digitalAssetMetadata) throws CantReceiveDigitalAssetException {
        try {
            DigitalAsset digitalAsset = digitalAssetMetadata.getDigitalAsset();
            String genesisTransaction = digitalAssetMetadata.getGenesisTransaction();
            DigitalAssetContract digitalAssetContract = digitalAsset.getContract();
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.CHECKING_CONTRACT, genesisTransaction);
            if (!isValidContract(digitalAssetContract)) {
                System.out.println("ASSET RECEPTION The contract is not valid");
                this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.REJECTED_BY_CONTRACT, genesisTransaction);
                return;
                //I don't want to throw this exception right now, I need to inform to issuer the asset condition
                //throw new CantReceiveDigitalAssetException("The DigitalAsset Contract is not valid, the expiration date has passed");
            }
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.CONTRACT_CHECKED, genesisTransaction);
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.CHECKING_HASH, genesisTransaction);
            if (!isDigitalAssetHashValid(digitalAssetMetadata)) {
                System.out.println("ASSET RECEPTION The DAM Hash is not valid");
                this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.REJECTED_BY_HASH, genesisTransaction);
                return;
                //throw new CantReceiveDigitalAssetException("The DigitalAsset hash is not valid");
            }
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.HASH_CHECKED, genesisTransaction);
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.ASSET_ACCEPTED, genesisTransaction);
            this.assetReceptionDao.updateDigitalAssetCryptoStatusByGenesisTransaction(genesisTransaction, CryptoStatus.PENDING_SUBMIT);
            persistInLocalStorage(digitalAssetMetadata);
        } catch (Exception e) {
            throw new CantReceiveDigitalAssetException(e, "Receiving Digital Asset Metadata", "Unexpected exception while verifying the asset");
        }
    }

    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, String senderId, Actors actorType) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        setDigitalAssetLocalFilePath(digitalAssetMetadata);
        this.assetReceptionDao.persistDigitalAsset(
                digitalAssetMetadata.getGenesisTransaction(),
                this.digitalAssetFileStoragePath,
                digitalAssetMetadata.getDigitalAssetHash(),
                senderId,
                actorType);
    }

    /**
     * This method check if the DigitalAssetMetadata remains with no modifications
     */
    public void checkDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata) throws CantReceiveDigitalAssetException {
        try {
            String genesisTransactionFromDigitalAssetMetadata = digitalAssetMetadata.getGenesisTransaction();
            this.assetReceptionDao.updateReceptionStatusByGenesisTransaction(ReceptionStatus.CHECKING_HASH, genesisTransactionFromDigitalAssetMetadata);
            String digitalAssetMetadataHash = digitalAssetMetadata.getDigitalAssetHash();
            List<CryptoTransaction> cryptoTransactionList = bitcoinNetworkManager.getCryptoTransactions(digitalAssetMetadata.getGenesisTransaction());
            if (cryptoTransactionList == null || cryptoTransactionList.isEmpty()) {
                throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, null, "Getting the genesis transaction from Crypto Network", "The crypto transaction received is null");
            }
            this.cryptoTransaction = cryptoTransactionList.get(0);
            String op_ReturnFromAssetVault = cryptoTransaction.getOp_Return();
            if (!digitalAssetMetadataHash.equals(op_ReturnFromAssetVault)) {
                throw new CantReceiveDigitalAssetException("Cannot receive Digital Asset because the " +
                        "Hash was modified:\n" +
                        "Op_return:" + op_ReturnFromAssetVault + "\n" +
                        "digitalAssetMetadata:" + digitalAssetMetadata);
            }
        } catch (CantGetCryptoTransactionException exception) {
            throw new CantReceiveDigitalAssetException(exception,
                    "Receiving the Digital Asset \n" + digitalAssetMetadata,
                    "Cannot get the genesis transaction from Asset vault");
        } catch (CantExecuteQueryException exception) {
            throw new CantReceiveDigitalAssetException(exception,
                    "Delivering the Digital Asset \n" + digitalAssetMetadata,
                    "Cannot execute a database operation");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantReceiveDigitalAssetException(exception,
                    "Delivering the Digital Asset \n" + digitalAssetMetadata,
                    "Unexpected result in database");
        }
    }

    public void persistDigitalAsset(DigitalAssetMetadata digitalAssetMetadata, ActorAssetUser actorAssetUser) throws CantPersistDigitalAssetException, CantCreateDigitalAssetFileException {
        setDigitalAssetLocalFilePath(digitalAssetMetadata);
        //this.assetDistributionDao.persistDigitalAsset(digitalAssetMetadata.getGenesisTransaction(), this.digitalAssetFileStoragePath, digitalAssetMetadata.getDigitalAssetHash(), actorAssetUser.getActorPublicKey());
        persistInLocalStorage(digitalAssetMetadata);
    }

    @Override
    public void persistInLocalStorage(DigitalAssetMetadata digitalAssetMetadata) throws CantCreateDigitalAssetFileException {
        this.digitalAssetReceptionVault.persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata, digitalAssetMetadata.getGenesisTransaction());
    }

    @Override
    public void setDigitalAssetLocalFilePath(DigitalAssetMetadata digitalAssetMetadata) {
        this.digitalAssetFileStoragePath = this.LOCAL_STORAGE_PATH + "/" + digitalAssetMetadata.getGenesisTransaction();
        this.digitalAssetReceptionVault.setDigitalAssetLocalFilePath(this.digitalAssetFileStoragePath);
    }

}
