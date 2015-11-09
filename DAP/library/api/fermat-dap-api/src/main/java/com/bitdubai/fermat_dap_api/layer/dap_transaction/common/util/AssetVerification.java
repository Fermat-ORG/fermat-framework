package com.bitdubai.fermat_dap_api.layer.dap_transaction.common.util;

import com.bitdubai.fermat_api.layer.DAPException;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
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
        CryptoTransaction cryptoTransaction = getCryptoTransactionFromCryptoNetwork(bitcoinNetworkManager, digitalAssetGenesisTransaction);
        String hashFromCryptoTransaction = cryptoTransaction.getOp_Return();
        return digitalAssetMetadataHash.equals(hashFromCryptoTransaction);
    }

    private static CryptoTransaction getCryptoTransactionFromCryptoNetwork(BitcoinNetworkManager bitcoinNetworkManager, String genesisTransaction) throws DAPException, CantGetCryptoTransactionException {
        List<CryptoTransaction> cryptoTransactionList =
                bitcoinNetworkManager.getCryptoTransaction(genesisTransaction);
        for (CryptoTransaction cryptoTransaction : cryptoTransactionList) {
            if (cryptoTransaction.getTransactionHash().equals(genesisTransaction)) {
                return cryptoTransaction;
            }
        }
        throw new DAPException("The genesis transaction doesn't exists in the crypto network");
    }


    public static boolean isAvailableBalanceInAssetVault(AssetVaultManager assetVaultManager, long genesisAmount, String genesisTransaction) {
        long availableBalanceForTransaction = assetVaultManager.getAvailableBalanceForTransaction(genesisTransaction);
        return availableBalanceForTransaction < genesisAmount;
    }

    public static boolean isValidContract(DigitalAssetContract digitalAssetContract) {
        //For now, we going to check, only, the expiration date
        ContractProperty contractProperty = digitalAssetContract.getContractProperty(DigitalAssetContractPropertiesConstants.EXPIRATION_DATE);
        Timestamp expirationDate = (Timestamp) contractProperty.getValue();
        return expirationDate.after(new Timestamp(System.currentTimeMillis()));
    }
}
