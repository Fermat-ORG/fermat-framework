package org.fermat.fermat_dap_api.layer.dap_transaction.common.util;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.ObjectNotSetException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions.CantGetCryptoTransactionException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;

import org.fermat.fermat_dap_api.layer.all_definition.contracts.ContractProperty;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContract;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetContractPropertiesConstants;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.State;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

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
            return !Validate.isValidString(digitalAssetHash);
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
        CryptoTransaction cryptoTransaction = getCryptoTransactionFromCryptoNetwork(bitcoinNetworkManager, digitalAssetMetadata);
        String hashFromCryptoTransaction = cryptoTransaction.getOp_Return();
        return digitalAssetMetadataHash.equals(hashFromCryptoTransaction);
    }

    private static CryptoTransaction getCryptoTransactionFromCryptoNetwork(BitcoinNetworkManager bitcoinNetworkManager, DigitalAssetMetadata digitalAssetMetadata) throws DAPException, CantGetCryptoTransactionException {
        return bitcoinNetworkManager.getGenesisCryptoTransaction(digitalAssetMetadata.getNetworkType(), digitalAssetMetadata.getTransactionChain());
    }

    public static CryptoTransaction getCryptoTransactionFromCryptoNetworkByCryptoStatus(BitcoinNetworkManager bitcoinNetworkManager, DigitalAssetMetadata digitalAssetMetadata, CryptoStatus cryptoStatus) throws CantGetCryptoTransactionException {
        List<CryptoTransaction> transactionListFromCryptoNetwork = bitcoinNetworkManager.getCryptoTransactions(digitalAssetMetadata.getLastTransactionHash());
        return matchStatus(transactionListFromCryptoNetwork, cryptoStatus);
    }

    public static CryptoTransaction getCryptoTransactionFromCryptoNetworkByCryptoStatus(BitcoinNetworkManager bitcoinNetworkManager, String genesisTransaction, CryptoStatus cryptoStatus) throws CantGetCryptoTransactionException {
        return matchStatus(bitcoinNetworkManager.getCryptoTransactions(genesisTransaction), cryptoStatus);
    }

    private static CryptoTransaction matchStatus(List<CryptoTransaction> allTransactions, CryptoStatus cryptoStatus) {
        if (allTransactions == null || allTransactions.isEmpty()) {
            System.out.println("ASSET TRANSACTION transaction List From Crypto Network is null or empty");
            return null;
        }
        System.out.println("ASSET TRANSACTION Now, I'm looking for this crypto status " + cryptoStatus);
        for (CryptoTransaction cryptoTransaction : allTransactions) {
            System.out.println("ASSET TRANSACTION CryptoStatus from Crypto Network:" + cryptoTransaction.getCryptoStatus());
            if (cryptoTransaction.getCryptoStatus() == cryptoStatus) {
                return cryptoTransaction;
            }
        }
        System.out.println("ASSET TRANSACTION COULDN'T FIND THE CRYPTO TRANSACTION.");
        return null;
    }


    public static boolean isAvailableBalanceInAssetVault(AssetVaultManager assetVaultManager, long genesisAmount, String genesisTransaction) {
        long availableBalanceForTransaction = assetVaultManager.getAvailableBalanceForTransaction(genesisTransaction);
        return availableBalanceForTransaction < genesisAmount;
    }


    public static CryptoTransaction foundCryptoTransaction(BitcoinNetworkManager bitcoinNetworkManager, DigitalAssetMetadata digitalAssetMetadata, CryptoTransactionType cryptoTransactionType, CryptoAddress addressTo) throws CantGetCryptoTransactionException {
        CryptoTransaction cryptoTransaction = bitcoinNetworkManager.getCryptoTransaction(digitalAssetMetadata.getLastTransactionHash(), cryptoTransactionType, addressTo);
        if (cryptoTransaction == null) {
            throw new CantGetCryptoTransactionException(CantGetCryptoTransactionException.DEFAULT_MESSAGE, null, "Getting the genesis transaction from Crypto Network", "The crypto transaction received is null");
        }
        return cryptoTransaction;
    }

    public static boolean isValidContract(DigitalAssetContract digitalAssetContract) {
        //For now, we going to check, only, the expiration date
        ContractProperty contractProperty = digitalAssetContract.getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE);
        return isValidExpirationDate(contractProperty);
    }

    public static boolean isValidExpirationDate(ContractProperty contractProperty) {
        Timestamp expirationDate = (Timestamp) contractProperty.getValue();
        return (expirationDate == null || new Timestamp(System.currentTimeMillis()).before(expirationDate));
    }
}
