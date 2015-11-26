package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAssetMetadata;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.AssetBalanceType;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.exceptions.CantGetAssetIssuerActorsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.AssetIssuerWalletTransactionRecordWrapper;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantCreateDigitalAssetFileException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantGetDigitalAssetFromLocalStorageException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.interfaces.AbstractDigitalAssetVault;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.exceptions.CantRegisterCreditException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWallet;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantGetTransactionsException;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.exceptions.CantLoadWalletException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantPersistsGenesisTransactionException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 01/10/15.
 * This class must be started with the AssetIssuing Plugin
 */
public class DigitalAssetIssuingVault extends AbstractDigitalAssetVault {

    ErrorManager errorManager;
    //For testing I'm gonna use this type of privacy, change to PRIVATE in production release
    //private final FilePrivacy FILE_PRIVACY=FilePrivacy.PUBLIC;

    public DigitalAssetIssuingVault(UUID pluginId, PluginFileSystem pluginFileSystem, ErrorManager errorManager) throws CantSetObjectException {
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);
        setErrorManager(errorManager);
    }

    public void setErrorManager(ErrorManager errorManager) throws CantSetObjectException{
        if(errorManager==null){
            throw new CantSetObjectException("ErrorManager is null");
        }
        this.errorManager=errorManager;
    }

    //TODO: method that update genesisTransaction
    /**
     * This method checks if the OP_return from Crypto Transaction is equals to DigitalAssetMetadata hash.
     */
    private boolean isDigitalAssetMetadataHashValid(DigitalAssetMetadata digitalAssetMetadata, CryptoTransaction genesisTransaction){
        String digitalAssetMetadataHash=digitalAssetMetadata.getDigitalAssetHash();
        String cryptoTransactionOP_return=genesisTransaction.getOp_Return();
        return digitalAssetMetadataHash.equals(cryptoTransactionOP_return);
    }

    public void deliverDigitalAssetMetadataToAssetWallet(CryptoTransaction genesisTransaction, String internalId, AssetBalanceType assetBalanceType)throws CantDeliverDigitalAssetToAssetWalletException{
        try{
            DigitalAssetMetadata digitalAssetMetadataToDeliver=getDigitalAssetMetadataFromLocalStorage(internalId);
            /**
             * Added by Rodrigo. This might not be the right place to do this.
             */
            digitalAssetMetadataToDeliver.setGenesisBlock(genesisTransaction.getBlockHash());
            if(!isDigitalAssetMetadataHashValid(digitalAssetMetadataToDeliver,genesisTransaction)){
                throw new CantDeliverDigitalAssetToAssetWalletException("The Digital Asset Metadata Hash is not valid:\n" +
                        "Hash: "+digitalAssetMetadataToDeliver.getDigitalAssetHash()+"\n"+
                        "OP_return: "+genesisTransaction.getOp_Return());
            }
            BalanceType balanceType;
            switch (assetBalanceType.getCode()){
                case "BOOK":
                    balanceType=BalanceType.BOOK;
                    break;
                case "AVAI":
                    balanceType=BalanceType.AVAILABLE;
                    break;
                default:
                    throw new CantDeliverDigitalAssetToAssetWalletException("Incorrect AssetBalanceType");
            }
            System.out.println("ASSET ISSUING - DELIVER TO WALLET TEST - "+balanceType+"\nHash: "+genesisTransaction.getTransactionHash());
            deliverDigitalAssetMetadata(digitalAssetMetadataToDeliver, genesisTransaction, balanceType);
        } catch (CantGetDigitalAssetFromLocalStorageException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception,"Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the DigitalAssetMetadata from storage");
        } catch (CantGetTransactionsException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception,"Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the Asset Transaction");
        } catch (CantLoadWalletException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception,"Delivering DigitalAssetMetadata to Asset Wallet", "Cannot load the Asset Wallet");
        } catch (CantRegisterCreditException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception,"Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the Asset Transaction");
        } catch (CantGetAssetIssuerActorsException exception) {
            throw new CantDeliverDigitalAssetToAssetWalletException(exception,"Delivering DigitalAssetMetadata to Asset Wallet", "Cannot get the Actor Asset Issuer");
        }
    }

    private void deliverDigitalAssetMetadata(DigitalAssetMetadata digitalAssetMetadata, CryptoTransaction genesisTransaction, BalanceType balanceType) throws CantLoadWalletException, CantGetTransactionsException, CantRegisterCreditException, CantGetAssetIssuerActorsException {
        System.out.println("ASSET ISSUING Before delivering - Wallet public key is:"+this.walletPublicKey);
        AssetIssuerWallet assetIssuerWallet=this.assetIssuerWalletManager.loadAssetIssuerWallet(this.walletPublicKey);
        AssetIssuerWalletBalance assetIssuerWalletBalance= assetIssuerWallet.getBookBalance(balanceType);
        String actorToPublicKey=this.actorAssetIssuerManager.getActorAssetIssuer().getActorPublicKey();
        System.out.println("ASSET ISSUING Actor Issuer public key:"+actorToPublicKey);
        System.out.println("ASSET ISSUING Transaction to deliver: "+genesisTransaction.getTransactionHash());
        AssetIssuerWalletTransactionRecordWrapper assetIssuerWalletTransactionRecordWrapper=new AssetIssuerWalletTransactionRecordWrapper(
                digitalAssetMetadata,
                genesisTransaction,
                "ActorFromPublicKey",
                actorToPublicKey
        );
        System.out.println("ASSET ISSUING AssetIssuerWalletTransactionRecordWrapper: "+ assetIssuerWalletTransactionRecordWrapper.getDescription());
        System.out.println("ASSET ISSUING Balance Type: "+ balanceType);
        assetIssuerWalletBalance.credit(assetIssuerWalletTransactionRecordWrapper, balanceType);
    }

    public void setGenesisTransaction(String internalId, String genesisTransaction) throws CantPersistsGenesisTransactionException {
        try{
            DigitalAssetMetadata digitalAssetMetadata=getDigitalAssetMetadataFromLocalStorage(internalId);
            digitalAssetMetadata.setGenesisTransaction(genesisTransaction);
            persistDigitalAssetMetadataInLocalStorage(digitalAssetMetadata, internalId);
        } catch (CantGetDigitalAssetFromLocalStorageException exception) {
            throw new CantPersistsGenesisTransactionException(exception, "Set genesis transaction in Digital Asset Metadata","Cannot get the Digital Asset metadata from local storage");
        } catch (CantCreateDigitalAssetFileException exception) {
            throw new CantPersistsGenesisTransactionException(exception, "Set genesis transaction in Digital Asset Metadata","Cannot persists the Digital Asset metadata in local storage");
        }
    }

}
