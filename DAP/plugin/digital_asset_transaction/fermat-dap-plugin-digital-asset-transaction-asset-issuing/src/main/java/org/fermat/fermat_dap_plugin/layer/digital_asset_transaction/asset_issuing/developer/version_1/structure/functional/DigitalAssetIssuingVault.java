package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.version_1.structure.functional;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;

import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/10/15.
 * This class must be started with the AssetIssuing Plugin
 */
public class DigitalAssetIssuingVault extends AbstractDigitalAssetVault {

    ErrorManager errorManager;

    public DigitalAssetIssuingVault(UUID pluginId,
                                    PluginFileSystem pluginFileSystem,
                                    ErrorManager errorManager,
                                    AssetIssuerWalletManager assetIssuerWalletManager,
                                    ActorAssetIssuerManager actorAssetIssuerManager) throws CantSetObjectException {
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);
        setErrorManager(errorManager);
        setAssetIssuerWalletManager(assetIssuerWalletManager);
        setActorAssetIssuerManager(actorAssetIssuerManager);

    }

    public void setErrorManager(ErrorManager errorManager) throws CantSetObjectException {
        if (errorManager == null) {
            throw new CantSetObjectException("ErrorManager is null");
        }
        this.errorManager = errorManager;
    }

    /**
     * This method checks if the OP_return from Crypto Transaction is equals to DigitalAssetMetadata hash.
     */
    private boolean isDigitalAssetMetadataHashValid(DigitalAssetMetadata digitalAssetMetadata, CryptoTransaction genesisTransaction) {
        String digitalAssetMetadataHash = digitalAssetMetadata.getDigitalAssetHash();
        String cryptoTransactionOP_return = genesisTransaction.getOp_Return();
        return digitalAssetMetadataHash.equals(cryptoTransactionOP_return);
    }

    public void deliverDigitalAssetMetadataToAssetWallet(CryptoTransaction cryptoTransaction, String internalId, BalanceType balanceType) throws CantDeliverDigitalAssetToAssetWalletException {
        try {
            DigitalAssetMetadata digitalAssetMetadataToDeliver = getDigitalAssetMetadataFromLocalStorage(internalId);
            /**
             * Added by Rodrigo. This might not be the right place to do this.
             */
            digitalAssetMetadataToDeliver.addNewTransaction(cryptoTransaction.getTransactionHash(), cryptoTransaction.getBlockHash());

            /**
             * Saving the Digital Asset metadata on disk because we might have a not null genesis block
             */
            this.persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadataToDeliver, internalId);

            if (!isDigitalAssetMetadataHashValid(digitalAssetMetadataToDeliver, cryptoTransaction)) {
                throw new CantDeliverDigitalAssetToAssetWalletException("The Digital Asset Metadata Hash is not valid:\n" +
                        "Hash: " + digitalAssetMetadataToDeliver.getDigitalAssetHash() + "\n" +
                        "OP_return: " + cryptoTransaction.getOp_Return());
            }
            System.out.println("ASSET ISSUING - DELIVER TO WALLET TEST - " + balanceType + "\nHash: " + cryptoTransaction.getTransactionHash());
            updateWalletBalance(digitalAssetMetadataToDeliver, cryptoTransaction, balanceType, TransactionType.CREDIT, DAPTransactionType.DISTRIBUTION, null, null, WalletUtilities.DEFAULT_MEMO_ISSUING);
        } catch (CantGetDigitalAssetFromLocalStorageException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the DigitalAssetMetadata from storage");
        } catch (CantGetTransactionsException | CantRegisterCreditException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the Asset Transaction");
        } catch (CantLoadWalletException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot load the Asset Wallet");
        } catch (CantGetAssetIssuerActorsException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the Actor Asset Issuer");
        } catch (CantCreateDigitalAssetFileException e) {
            throw new CantDeliverDigitalAssetToAssetWalletException(e, "Delivering DigitalAssetMetadata to Asset Wallet", "saving to disk the digital asset metadata");
        } catch (CantAssetUserActorNotFoundException | CantGetAssetUserActorsException | CantRegisterDebitException e) {
            e.printStackTrace();
        }
    }
}
