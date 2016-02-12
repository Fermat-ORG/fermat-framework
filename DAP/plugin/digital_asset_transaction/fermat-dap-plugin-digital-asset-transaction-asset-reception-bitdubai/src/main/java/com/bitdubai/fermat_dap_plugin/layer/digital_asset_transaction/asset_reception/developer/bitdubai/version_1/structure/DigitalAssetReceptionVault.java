package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetBalanceType;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/10/15.
 */
public class DigitalAssetReceptionVault extends AbstractDigitalAssetVault {

    ErrorManager errorManager;

    public DigitalAssetReceptionVault(UUID pluginId,
                                      PluginFileSystem pluginFileSystem,
                                      ErrorManager errorManager,
                                      AssetUserWalletManager assetUserWalletManager) throws CantSetObjectException {
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);
        setErrorManager(errorManager);
        setAssetUserWalletManager(assetUserWalletManager);
        LOCAL_STORAGE_PATH = "digital-asset-reception/";
    }

    public void setErrorManager(ErrorManager errorManager) throws CantSetObjectException {
        if (errorManager == null) {
            throw new CantSetObjectException("ErrorManager is null");
        }
        this.errorManager = errorManager;
    }

    @Override
    public void setDigitalAssetMetadataAssetIssuerWalletTransaction(CryptoTransaction genesisTransaction, DigitalAssetMetadata digitalAssetMetadataToDeliver, AssetBalanceType assetBalanceType, TransactionType transactionType, DAPTransactionType dapTransactionType, String externalActorPublicKey) throws CantDeliverDigitalAssetToAssetWalletException {
        try {
            digitalAssetMetadataToDeliver.getDigitalAsset().setGenesisAmount(genesisTransaction.getCryptoAmount());
            BalanceType balanceType = BalanceType.BOOK;
            if (assetBalanceType.getCode().equals(AssetBalanceType.BOOK.getCode())) {
                balanceType = BalanceType.BOOK;
            }
            if (assetBalanceType.getCode().equals(AssetBalanceType.AVAILABLE.getCode())) {
                balanceType = BalanceType.AVAILABLE;
            }
            System.out.println("ASSET Distribution OR RECEPTION - DELIVER TO WALLET TEST - " + balanceType + "\nHash: " + genesisTransaction.getTransactionHash());
            updateWalletBalance(digitalAssetMetadataToDeliver, genesisTransaction, balanceType, transactionType, dapTransactionType, externalActorPublicKey);
        } catch (CantGetTransactionsException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the Asset Transaction");
        } catch (CantLoadWalletException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot load the Asset Wallet");
        } catch (CantRegisterCreditException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot register credit in asset issuer wallet");
        } catch (CantRegisterDebitException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot register debit in asset issuer wallet");
        } catch (CantGetAssetIssuerActorsException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the Actor Asset Issuer");
        } catch (CantAssetUserActorNotFoundException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot find the Actor Asset User");
        } catch (CantGetAssetUserActorsException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception, "Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the Actor Asset User");
        }
    }
}
