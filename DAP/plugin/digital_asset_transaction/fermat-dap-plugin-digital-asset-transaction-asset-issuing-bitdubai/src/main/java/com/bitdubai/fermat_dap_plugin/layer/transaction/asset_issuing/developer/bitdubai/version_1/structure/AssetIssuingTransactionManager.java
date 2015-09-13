package com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.ProtocolStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Transaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.TransactionProtocolManager;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransaction;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantDeliverPendingTransactionsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCreateDigitalAssetTransactionException;
import com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/09/15.
 */
public class AssetIssuingTransactionManager implements AssetIssuingManager, DealsWithErrors, TransactionProtocolManager {

    CryptoVaultManager cryptoVaultManager;
    CryptoWallet cryptoWallet;
    //DigitalAsset digitalAsset;
    ErrorManager errorManager;
    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;
    DigitalAssetCryptoTransactionFactory digitalAssetCryptoTransactionFactory;
    PluginFileSystem pluginFileSystem;

    public AssetIssuingTransactionManager(UUID pluginId, CryptoVaultManager cryptoVaultManager, CryptoWallet cryptoWallet, PluginDatabaseSystem pluginDatabaseSystem, PluginFileSystem pluginFileSystem, ErrorManager errorManager/*, CryptoAddressBookManager cryptoAddressBookManager*/) throws CantSetObjectException, CantExecuteDatabaseOperationException {

        setCryptoVaultManager(cryptoVaultManager);
        setCryptoWallet(cryptoWallet);
        setErrorManager(errorManager);
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);
        setPluginDatabaseSystem(pluginDatabaseSystem);
        this.digitalAssetCryptoTransactionFactory=new DigitalAssetCryptoTransactionFactory(this.pluginId,
                this.cryptoVaultManager,
                this.cryptoWallet,
                this.pluginDatabaseSystem,
                this.pluginFileSystem);
        this.digitalAssetCryptoTransactionFactory.setErrorManager(errorManager);
    }

    @Override
    public void issueAsset(DigitalAsset digitalAssetToIssue) throws CantIssueDigitalAssetException {
        try {
            this.digitalAssetCryptoTransactionFactory.createDigitalAssetCryptoTransaction(digitalAssetToIssue);
        } catch (CantCreateDigitalAssetTransactionException exception) {
            throw new CantIssueDigitalAssetException(exception, "Creating a Digital Asset Transaction", "Check the cause");
        } catch(Exception exception){
            throw new CantIssueDigitalAssetException(FermatException.wrapException(exception), "Issuing a Digital Asset Transaction", "Unexpected Exception");
        }
    }

    @Override
    public void setCryptoWallet(CryptoWallet cryptoWallet) {
        this.cryptoWallet=cryptoWallet;
    }

    @Override
    public TransactionProtocolManager<CryptoTransaction> getTransactionManager() {
        return null;
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager=errorManager;
    }

    public void setPluginId(UUID pluginId) throws CantSetObjectException{
        if(pluginId==null){
            throw new CantSetObjectException("PluginId is null");
        }
        this.pluginId=pluginId;
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
            throw new CantSetObjectException("CryptoVaultManager is null");
        }
        this.cryptoVaultManager=cryptoVaultManager;
    }

    @Override
    public void confirmReception(UUID transactionID) throws CantConfirmTransactionException {
        try {
            AssetIssuingTransactionDao assetIssuingTransactionDao=new AssetIssuingTransactionDao(this.pluginDatabaseSystem,this.pluginId);
            assetIssuingTransactionDao.updateTransactionProtocolStatus(transactionID, ProtocolStatus.RECEPTION_NOTIFIED);
        } catch (CantExecuteDatabaseOperationException exception) {
            throw new CantConfirmTransactionException(CantExecuteQueryException.DEFAULT_MESSAGE, exception, "Confirming Reception", "Cannot update the plugin database");
        } catch (CantExecuteQueryException exception) {
            throw new CantConfirmTransactionException(CantExecuteQueryException.DEFAULT_MESSAGE, exception, "Confirming Reception", "Cannot execute query");
        } catch (UnexpectedResultReturnedFromDatabaseException exception) {
            throw new CantConfirmTransactionException(UnexpectedResultReturnedFromDatabaseException.DEFAULT_MESSAGE, exception, "Confirming Reception", "The database returns more than one valid result");
        } catch(Exception exception){
            throw new CantConfirmTransactionException(CantConfirmTransactionException.DEFAULT_MESSAGE, FermatException.wrapException(exception), "Confirming Reception", "Unexpected Exception");
        }

    }

    @Override
    public List<Transaction> getPendingTransactions(Specialist specialist) throws CantDeliverPendingTransactionsException {
        return null;
    }
}
