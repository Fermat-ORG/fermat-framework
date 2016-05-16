package org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import org.fermat.fermat_dap_api.layer.all_definition.enums.DAPTransactionType;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import org.fermat.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;

import org.fermat.fermat_dap_plugin.layer.digital_asset_transaction.asset_reception.developer.version_1.AssetReceptionDigitalAssetTransactionPluginRoot;



import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/10/15.
 */
public class DigitalAssetReceptionVault extends AbstractDigitalAssetVault {

    AssetReceptionDigitalAssetTransactionPluginRoot assetReceptionDigitalAssetTransactionPluginRoot;

    public DigitalAssetReceptionVault(UUID pluginId,
                                      PluginFileSystem pluginFileSystem,
                                      AssetReceptionDigitalAssetTransactionPluginRoot assetReceptionDigitalAssetTransactionPluginRoot,
                                      AssetUserWalletManager assetUserWalletManager) throws CantSetObjectException {
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);
        this.assetReceptionDigitalAssetTransactionPluginRoot = assetReceptionDigitalAssetTransactionPluginRoot;
        setAssetUserWalletManager(assetUserWalletManager);
        LOCAL_STORAGE_PATH = "digital-asset-reception/";
    }

    @Override
    public void updateWalletBalance(DigitalAssetMetadata digitalAssetMetadata, CryptoTransaction genesisTransaction, BalanceType balanceType, TransactionType transactionType, DAPTransactionType dapTransactionType, String externalActorPublicKey, Actors externalActorType, String memo) throws CantLoadWalletException, CantGetTransactionsException, CantRegisterCreditException, CantRegisterDebitException, CantGetAssetIssuerActorsException, CantAssetUserActorNotFoundException, CantGetAssetUserActorsException {
        digitalAssetMetadata.getDigitalAsset().setGenesisAmount(genesisTransaction.getCryptoAmount());
        super.updateWalletBalance(digitalAssetMetadata, genesisTransaction, balanceType, transactionType, dapTransactionType, externalActorPublicKey, externalActorType, memo);
    }
}
