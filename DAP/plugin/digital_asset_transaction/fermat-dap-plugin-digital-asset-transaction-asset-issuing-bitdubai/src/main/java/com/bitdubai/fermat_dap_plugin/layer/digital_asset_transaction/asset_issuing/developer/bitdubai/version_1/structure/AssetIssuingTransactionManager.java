package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing.intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/09/15.
 */
public class AssetIssuingTransactionManager implements AssetIssuingManager, DealsWithErrors/*, TransactionProtocolManager*/ {

    CryptoAddressBookManager cryptoAddressBookManager;
    CryptoVaultManager cryptoVaultManager;
    CryptoWallet cryptoWallet;
    ErrorManager errorManager;
    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;
    DigitalAssetCryptoTransactionFactory digitalAssetCryptoTransactionFactory;
    PluginFileSystem pluginFileSystem;
    AssetVaultManager assetVaultManager;
    OutgoingIntraActorManager outgoingIntraActorManager;
    AssetIssuingTransactionDao assetIssuingTransactionDao;

    public AssetIssuingTransactionManager(UUID pluginId,
                                          CryptoVaultManager cryptoVaultManager,
                                          CryptoWallet cryptoWallet,
                                          PluginDatabaseSystem pluginDatabaseSystem,
                                          PluginFileSystem pluginFileSystem,
                                          ErrorManager errorManager,
                                          AssetVaultManager assetVaultManager,
                                          CryptoAddressBookManager cryptoAddressBookManager,
                                          OutgoingIntraActorManager outgoingIntraActorManager) throws CantSetObjectException, CantExecuteDatabaseOperationException {

        setCryptoVaultManager(cryptoVaultManager);
        setCryptoWallet(cryptoWallet);
        setErrorManager(errorManager);
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);
        setPluginDatabaseSystem(pluginDatabaseSystem);
        setAssetVaultManager(assetVaultManager);
        setCryptoAddressBookManager(cryptoAddressBookManager);
        //TODO: when the OutgoingIntraUser is working, please, uncomment the following line
        setOutgoingIntraActorManager(outgoingIntraActorManager);
        this.digitalAssetCryptoTransactionFactory=new DigitalAssetCryptoTransactionFactory(this.pluginId,
                this.cryptoVaultManager,
                this.cryptoWallet,
                this.pluginDatabaseSystem,
                this.pluginFileSystem,
                this.assetVaultManager,
                this.cryptoAddressBookManager,
                this.outgoingIntraActorManager);
        this.digitalAssetCryptoTransactionFactory.setErrorManager(errorManager);
    }

    @Override
    public void issueAssets(DigitalAsset digitalAssetToIssue, int assetsAmount, String walletPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException {
        try {
            this.digitalAssetCryptoTransactionFactory.issueDigitalAssets(digitalAssetToIssue, assetsAmount, walletPublicKey, blockchainNetworkType);
        } catch (CantIssueDigitalAssetsException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Creating a Digital Asset Transaction", "Check the cause");
        } catch (CantDeliverDigitalAssetToAssetWalletException exception) {
            throw new CantIssueDigitalAssetsException(exception, "Creating a Digital Asset Transaction", "Cannot deliver the digital asset to the asset wallet");
        } catch(Exception exception){
            throw new CantIssueDigitalAssetsException(FermatException.wrapException(exception), "Issuing the Digital Asset required amount", "Unexpected Exception");
        }
    }

    @Override
    public void issuePendingDigitalAssets(String publicKey) {
        this.digitalAssetCryptoTransactionFactory.issuePendingDigitalAssets(publicKey);
    }

    @Override
    public void setCryptoWallet(CryptoWallet cryptoWallet) {
        this.cryptoWallet=cryptoWallet;
    }

    /*@Override
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager() {
        return this;
    }*/

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager=errorManager;
    }

    public void setDigitalAssetMetadataVault(DigitalAssetMetadataVault digitalAssetMetadataVault) throws CantSetObjectException {
        this.digitalAssetCryptoTransactionFactory.setDigitalAssetMetadataVault(digitalAssetMetadataVault);
    }

    public void setPluginId(UUID pluginId) throws CantSetObjectException{
        if(pluginId==null){
            throw new CantSetObjectException("PluginId is null");
        }
        this.pluginId=pluginId;
    }

    public void setOutgoingIntraActorManager(OutgoingIntraActorManager outgoingIntraActorManager) throws CantSetObjectException{
        if(outgoingIntraActorManager ==null){
            throw new CantSetObjectException("outgoingIntraActorManager is null");
        }
        this.outgoingIntraActorManager = outgoingIntraActorManager;
    }

    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystem)throws CantSetObjectException{
        if(pluginDatabaseSystem==null){
            throw new CantSetObjectException("pluginDatabaseSystem is null");
        }
        this.pluginDatabaseSystem=pluginDatabaseSystem;
    }

    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) throws CantSetObjectException{
        if(pluginFileSystem==null){
            throw new CantSetObjectException("pluginFileSystem is null");
        }
        this.pluginFileSystem=pluginFileSystem;
    }

    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) throws CantSetObjectException{
        if(cryptoVaultManager==null){
            throw new CantSetObjectException("cryptoVaultManager is null");
        }
        this.cryptoVaultManager=cryptoVaultManager;
    }

    public void setAssetVaultManager(AssetVaultManager assetVaultManager)throws CantSetObjectException{
        if(assetVaultManager==null){
            throw new CantSetObjectException("assetVaultManager is null");
        }
        this.assetVaultManager=assetVaultManager;
    }

    public void setCryptoAddressBookManager(CryptoAddressBookManager cryptoAddressBookManager)throws CantSetObjectException{
        if(cryptoAddressBookManager==null){
            throw new CantSetObjectException("CryptoAddressBook is null");
        }
        this.cryptoAddressBookManager=cryptoAddressBookManager;
    }

    public void setAssetIssuingTransactionDao(AssetIssuingTransactionDao assetIssuingTransactionDao) throws CantSetObjectException {
        if(assetIssuingTransactionDao==null){
            throw new CantSetObjectException("assetIssuingTransactionDao is null");
        }
        this.assetIssuingTransactionDao=assetIssuingTransactionDao;
        this.digitalAssetCryptoTransactionFactory.setAssetIssuingTransactionDao(assetIssuingTransactionDao);
    }

    @Override
    public void confirmReception(/*UUID transactionID*/String genesisTransaction) throws CantConfirmTransactionException {
        try {
            this.assetIssuingTransactionDao.confirmReception(genesisTransaction);
        }  catch (CantExecuteQueryException exception) {
            throw new CantConfirmTransactionException(CantExecuteQueryException.DEFAULT_MESSAGE, exception, "Confirming Reception", "Cannot execute query");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantConfirmTransactionException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE, exception, "Confirming Reception", "The database returns more than one valid result");
        } catch(Exception exception){
            throw new CantConfirmTransactionException(CantConfirmTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Confirming Reception", "Unexpected Exception");
        }
    }

    @Override
    public int getNumberOfIssuedAssets(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        try {
            return this.digitalAssetCryptoTransactionFactory.getNumberOfIssuedAssets(assetPublicKey);
        } catch (CantCheckAssetIssuingProgressException exception) {
            throw new CantExecuteDatabaseOperationException(exception, "Getting the number of issued assets","Cannot check the asset issuing progress");
        }
    }

    /**
    @Override
    public List<Transaction> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
return null;

        try{
            List<Transaction> txs = new ArrayList<Transaction>();
            AssetIssuingTransactionDao assetIssuingTransactionDao= new AssetIssuingTransactionDao(this.pluginDatabaseSystem, this.pluginId);

            HashMap<String, String> transactionHeaders = assetIssuingTransactionDao.getPendingTransactionsHeaders();
            for (Map.Entry<String, String> entry : transactionHeaders.entrySet()){
                String txId = entry.getKey();
                String txHash = entry.getValue();
                //TODO: finish this
                String[] addresses = getAddressFromTransaction(txHash);
                CryptoAddress addressFrom = new CryptoAddress(addresses[0], CryptoCurrency.BITCOIN);
                CryptoAddress addressTo = new CryptoAddress(addresses[1], CryptoCurrency.BITCOIN);
                long amount = getAmountFromVault(txHash);



                CryptoStatus cryptoStatus = db.getCryptoStatus(txId);


                CryptoTransaction cryptoTransaction = new CryptoTransaction(txHash, addressFrom, addressTo,CryptoCurrency.BITCOIN, amount, cryptoStatus);

                com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction tx = new com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction(UUID.fromString(txId),cryptoTransaction, Action.APPLY, getTransactionTimestampFromVault(txHash));
                txs.add(tx);

                db.updateTransactionProtocolStatus(UUID.fromString(txId), ProtocolStatus.SENDING_NOTIFIED);
            }
            return txs;
        } catch (Exception e){
            throw new CantDeliverPendingTransactionsException("I couldn't deliver pending transactions",e,null,null);
        }
    }*/
}
