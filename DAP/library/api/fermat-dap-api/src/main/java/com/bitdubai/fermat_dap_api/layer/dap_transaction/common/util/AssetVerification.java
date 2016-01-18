package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.util;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.State;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 22/10/15.
 */
public final class AssetVerification {

    private AssetVerification() {
        throw new AssertionError("NO INSTANCES!");
    }

    public static boolean isDigitalAssetComplete(DigitalAssetMetadata digitalAssetMetadata) {
        try {
            DigitalAsset digitalAsset = digitalAssetMetadata.getDigitalAsset();
            areObjectsSettled(digitalAsset);
            CryptoAddress genesisAddress = digitalAsset.getGenesisAddress();
            if (Validate.isObjectNull(genesisAddress)) {
                return false;
            }
            String digitalAssetHash = digitalAssetMetadata.getDigitalAssetHash();
            if (Validate.isValidString(digitalAssetHash)) {
                return false;
            }
            return true;
        } catch (ObjectNotSetException e) {
            return false;
        }
    }

    /**
     * This method checks that every object in Digital asset is set.
     *
     * @throws ObjectNotSetException
     */
    public static void areObjectsSettled(DigitalAsset digitalAsset) throws ObjectNotSetException {
        if (digitalAsset.getContract() == null) {
            throw new ObjectNotSetException("Digital Asset Contract is not set");
        }
        if (digitalAsset.getResources() == null) {
            throw new ObjectNotSetException("Digital Asset Resources is not set");
        }
        if (digitalAsset.getDescription() == null) {
            throw new ObjectNotSetException("Digital Asset Description is not set");
        }
        if (digitalAsset.getName() == null) {
            throw new ObjectNotSetException("Digital Asset Name is not set");
        }
        if (digitalAsset.getPublicKey() == null) {
            throw new ObjectNotSetException("Digital Asset PublicKey is not set");
        }
        if (digitalAsset.getState() == null) {
            digitalAsset.setState(State.DRAFT);
        }
        if (digitalAsset.getIdentityAssetIssuer() == null) {
            throw new ObjectNotSetException("Digital Asset Identity is not set");
        }
    }

    public static boolean isDigitalAssetHashValid(BitcoinNetworkManager bitcoinNetworkManager, DigitalAssetMetadata digitalAssetMetadata) throws CantGetCryptoTransactionException, DAPException {
        String digitalAssetMetadataHash = digitalAssetMetadata.getDigitalAssetHash();
        String digitalAssetGenesisTransaction = digitalAssetMetadata.getGenesisTransaction();
        String genesisBlockHash = digitalAssetMetadata.getGenesisBlock();
        CryptoTransaction cryptoTransaction = getCryptoTransactionFromCryptoNetwork(bitcoinNetworkManager, digitalAssetGenesisTransaction, genesisBlockHash);
        String hashFromCryptoTransaction = cryptoTransaction.getOp_Return();
        return digitalAssetMetadataHash.equals(hashFromCryptoTransaction);
    }

    private static CryptoTransaction getCryptoTransactionFromCryptoNetwork(BitcoinNetworkManager bitcoinNetworkManager, String genesisTransaction, String genesisBlock) throws DAPException, CantGetCryptoTransactionException {
        return bitcoinNetworkManager.getCryptoTransactionFromBlockChain(genesisTransaction, genesisBlock);
    }

    public static CryptoTransaction getCryptoTransactionFromCryptoNetworkByCryptoStatus(BitcoinNetworkManager bitcoinNetworkManager, String genesisTransaction, CryptoStatus cryptoStatus) throws CantGetCryptoTransactionException {
        /**
         * I will get the genesis transaction from the CryptoNetwork
         */
        List<CryptoTransaction> transactionListFromCryptoNetwork = bitcoinNetworkManager.getCryptoTransactions(genesisTransaction);
        if (transactionListFromCryptoNetwork.size() == 0) {
            /**
             * If I didn't get it, I will get the child of the genesis Transaction
             */
            transactionListFromCryptoNetwork = bitcoinNetworkManager.getChildCryptoTransaction(genesisTransaction);
        }

        if (transactionListFromCryptoNetwork == null || transactionListFromCryptoNetwork.isEmpty()) {
            System.out.println("ASSET TRANSACTION transaction List From Crypto Network for " + genesisTransaction + " is null or empty");
            return null;
        }
        System.out.println("ASSET TRANSACTION I found " + transactionListFromCryptoNetwork.size() + " in Crypto network from genesis transaction:\n" + genesisTransaction);

        System.out.println("ASSET TRANSACTION Now, I'm looking for this crypto status " + cryptoStatus);
        for (CryptoTransaction cryptoTransaction : transactionListFromCryptoNetwork) {
            System.out.println("ASSET TRANSACTION CryptoStatus from Crypto Network:" + cryptoTransaction.getCryptoStatus());
            if (cryptoTransaction.getCryptoStatus() == cryptoStatus) {
                System.out.println("ASSET TRANSACTION I found it!");
                cryptoTransaction.setTransactionHash(genesisTransaction);
                return cryptoTransaction;
            }
        }
        System.out.println("ASSET TREANSACTION COULDN'T FIND THE CRYPTO TRANSACTION.");
        return null;
    }


    public static boolean isAvailableBalanceInAssetVault(AssetVaultManager assetVaultManager, long genesisAmount, String genesisTransaction) {
        long availableBalanceForTransaction = assetVaultManager.getAvailableBalanceForTransaction(genesisTransaction);
        return availableBalanceForTransaction < genesisAmount;
    }


    public static CryptoTransaction foundCryptoTransaction(BitcoinNetworkManager bitcoinNetworkManager, DigitalAssetMetadata digitalAssetMetadata) throws CantGetCryptoTransactionException {
        CryptoTransaction cryptoTransaction = bitcoinNetworkManager.getCryptoTransactionFromBlockChain(digitalAssetMetadata.getGenesisTransaction(), digitalAssetMetadata.getGenesisBlock());
        if (cryptoTransaction == null) {
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, null, "Getting the genesis transaction from Crypto Network", "The crypto transaction received is null");
        }
        return cryptoTransaction;
    }

    public static boolean isValidContract(DigitalAssetContract digitalAssetContract) {
        //For now, we going to check, only, the expiration date
        ContractProperty contractProperty = digitalAssetContract.getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE);
        Timestamp expirationDate = (Timestamp) contractProperty.getValue();
        return (expirationDate == null || new Timestamp(System.currentTimeMillis()).before(expirationDate));
    }
}
