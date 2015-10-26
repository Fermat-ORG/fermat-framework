package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_distribution.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetBalanceType;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.AssetIssuerWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterDebitException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/10/15.
 */
public class DigitalAssetDistributionVault extends AbstractDigitalAssetVault {

    ErrorManager errorManager;
    //private final String LOCAL_STORAGE_PATH="digital-asset-transmission/";
    //private final String digitalAssetFileName="digital-asset.xml";
    //private final String digitalAssetMetadataFileName="digital-asset-metadata.xml";
    //String digitalAssetFileStoragePath;

    public DigitalAssetDistributionVault(UUID pluginId, PluginFileSystem pluginFileSystem, ErrorManager errorManager) throws CantSetObjectException {
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);
        setErrorManager(errorManager);
        LOCAL_STORAGE_PATH="digital-asset-transmission/";
    }

    public void setErrorManager(ErrorManager errorManager) throws CantSetObjectException{
        if(errorManager==null){
            throw new CantSetObjectException("ErrorManager is null");
        }
        this.errorManager=errorManager;
    }

    public void setDigitalAssetMetadataAssetIssuerWalletTransaction(CryptoTransaction genesisTransaction, String internalId, AssetBalanceType assetBalanceType, TransactionType transactionType)throws CantDeliverDigitalAssetToAssetWalletException {
        try{
            DigitalAssetMetadata digitalAssetMetadataToDeliver=getDigitalAssetMetadataFromLocalStorage(internalId);
            BalanceType balanceType=BalanceType.BOOK;
            if(assetBalanceType.getCode().equals(AssetBalanceType.BOOK)){
                balanceType=BalanceType.BOOK;
            }
            if(assetBalanceType.getCode().equals(AssetBalanceType.AVAILABLE)){
                balanceType=BalanceType.AVAILABLE;
            }
            System.out.println("ASSET Distribution - DELIVER TO WALLET TEST - "+balanceType+"\nHash: "+genesisTransaction.getTransactionHash());
            deliverDigitalAssetMetadata(digitalAssetMetadataToDeliver, genesisTransaction, balanceType, transactionType);
        } catch (CantGetDigitalAssetFromLocalStorageException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception,"Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the DigitalAssetMetadata from storage");
        } catch (CantGetTransactionsException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception,"Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the Asset Transaction");
        } catch (CantLoadWalletException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception,"Delivering DigitalAssetMetadata to Asset Wallet", "Cannot load the Asset Wallet");
        } catch (CantRegisterCreditException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception,"Delivering DigitalAssetMetadata to Asset Wallet", "Cannot register credit in asset issuer wallet");
        } catch (CantRegisterDebitException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception,"Delivering DigitalAssetMetadata to Asset Wallet", "Cannot register debit in asset issuer wallet");
        }
    }

    private void deliverDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata, CryptoTransaction genesisTransaction, BalanceType balanceType, TransactionType transactionType) throws CantLoadWalletException, CantGetTransactionsException, CantRegisterCreditException, CantRegisterDebitException {
        AssetIssuerWallet assetIssuerWallet=this.assetIssuerWalletManager.loadAssetIssuerWallet(this.walletPublicKey);
        AssetIssuerWalletBalance assetIssuerWalletBalance= assetIssuerWallet.getBookBalance(balanceType);
        System.out.println("ASSET Distribution Transaction to deliver: "+genesisTransaction.getTransactionHash());
        AssetIssuerWalletTransactionRecordWrapper assetIssuerWalletTransactionRecordWrapper=new AssetIssuerWalletTransactionRecordWrapper(
                digitalAssetMetadata,
                genesisTransaction,
                "testActorFromPublicKey",
                "testActorToPublicKey"
        );
        System.out.println("ASSET Distribution AssetIssuerWalletTransactionRecordWrapper: "+ assetIssuerWalletTransactionRecordWrapper.getDescription());
        System.out.println("ASSET Distribution Balance Type: " + balanceType);
        System.out.println("ASSET Distribution Transaction Type: " + transactionType);
        if(transactionType.getCode().equals(TransactionType.CREDIT.getCode())){
            assetIssuerWalletBalance.credit(assetIssuerWalletTransactionRecordWrapper, balanceType);
        }
        if(transactionType.getCode().equals(TransactionType.DEBIT.getCode())){
            assetIssuerWalletBalance.debit(assetIssuerWalletTransactionRecordWrapper, balanceType);
        }

    }

    public void setDigitalAssetMetadataAssetIssuerWalletDebit(DigitalAssetMetadata digitalAssetMetadata, CryptoTransaction genesisTransaction, BalanceType balanceType) throws CantLoadWalletException, CantGetTransactionsException, CantRegisterDebitException {
        AssetIssuerWallet assetIssuerWallet=this.assetIssuerWalletManager.loadAssetIssuerWallet(this.walletPublicKey);
        AssetIssuerWalletBalance assetIssuerWalletBalance= assetIssuerWallet.getBookBalance(balanceType);
        AssetIssuerWalletTransactionRecordWrapper assetIssuerWalletTransactionRecordWrapper=new AssetIssuerWalletTransactionRecordWrapper(
                digitalAssetMetadata,
                genesisTransaction,
                "testActorFromPublicKey",
                "testActorToPublicKey"
        );
        System.out.println("ASSET DISTRIBUTION AssetIssuerWalletTransactionRecordWrapper:"+assetIssuerWalletTransactionRecordWrapper.getDescription());
        System.out.println("ASSET DISTRIBUTION Balance Type:"+balanceType);
        //I'm gonna mock a credit in Asset issuer wallet for testing, TODO: delete this lines in advanced testing
        try {
            assetIssuerWalletBalance.credit(assetIssuerWalletTransactionRecordWrapper, BalanceType.BOOK);
            assetIssuerWalletBalance.credit(assetIssuerWalletTransactionRecordWrapper, BalanceType.AVAILABLE);
        } catch (CantRegisterCreditException e) {
            e.printStackTrace();
        }
        //End mock
        assetIssuerWalletBalance.debit(assetIssuerWalletTransactionRecordWrapper, balanceType);
    }

}
