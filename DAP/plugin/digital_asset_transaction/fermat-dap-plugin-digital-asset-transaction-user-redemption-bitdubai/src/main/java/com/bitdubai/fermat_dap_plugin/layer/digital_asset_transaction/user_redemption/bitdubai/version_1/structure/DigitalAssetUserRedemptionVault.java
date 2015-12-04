package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.user_redemption.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantAssetUserActorNotFoundException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.exceptions.CantGetAssetUserActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.AssetUserWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_user_wallet.interfaces.AssetUserWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/11/15.
 */
public class DigitalAssetUserRedemptionVault extends AbstractDigitalAssetVault {
    ErrorManager errorManager;

    public DigitalAssetUserRedemptionVault(UUID pluginId, PluginFileSystem pluginFileSystem, ErrorManager errorManager) throws CantSetObjectException {
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);
        setErrorManager(errorManager);
        LOCAL_STORAGE_PATH="digital-asset-user-redemption/";
    }

    public void setErrorManager(ErrorManager errorManager) throws CantSetObjectException{
        if(errorManager==null){
            throw new CantSetObjectException("ErrorManager is null");
        }
        this.errorManager=errorManager;
    }

    public void setDigitalAssetMetadataAssetIssuerWalletDebit(DigitalAssetMetadata digitalAssetMetadata, CryptoTransaction genesisTransaction, BalanceType balanceType) throws CantLoadWalletException, CantGetTransactionsException, CantRegisterDebitException, CantGetAssetUserActorsException, CantAssetUserActorNotFoundException {
        AssetUserWallet assetUserWallet=this.assetUserWalletManager.loadAssetUserWallet(this.walletPublicKey);
        AssetUserWalletBalance assetUserWalletBalance= assetUserWallet.getBookBalance(balanceType);
        ActorAssetUser actorAssetUser=this.actorAssetUserManager.getActorAssetUser();
        String actorFromPublicKey;
        if(actorAssetUser==null){
            System.out.println("USER REDEMPTION Actor user is null");
            actorFromPublicKey="UNDEFINED";
        }else{
            actorFromPublicKey=actorAssetUser.getActorPublicKey();
        }
        System.out.println("USER REDEMPTION Actor user public key:"+actorFromPublicKey);
        AssetUserWalletTransactionRecordWrapper assetUserWalletTransactionRecordWrapper=new AssetUserWalletTransactionRecordWrapper(
                digitalAssetMetadata,
                genesisTransaction,
                actorFromPublicKey,
                "testActorToPublicKey"
        );
        System.out.println("USER REDEMPTION AssetUserWalletTransactionRecordWrapper:" + assetUserWalletTransactionRecordWrapper.getDescription());
        System.out.println("USER REDEMPTION Balance Type:"+balanceType);
        //I'm gonna mock a credit in Asset issuer wallet for testing, TODO: delete this lines in advanced testing
        try {
            assetUserWalletBalance.credit(assetUserWalletTransactionRecordWrapper, BalanceType.BOOK);
            assetUserWalletBalance.credit(assetUserWalletTransactionRecordWrapper, BalanceType.AVAILABLE);
        } catch (CantRegisterCreditException e) {
            e.printStackTrace();
        }
        //End mock
        assetUserWalletBalance.debit(assetUserWalletTransactionRecordWrapper, balanceType);
    }
}
