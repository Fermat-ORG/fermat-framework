package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.exceptions.CantConfirmTransactionException;
import com.bitdubai.fermat_ccp_api.layer.actor.intra_user.interfaces.IntraWalletUserActorManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_world.wallet.exceptions.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantExecuteQueryException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.asset_vault.interfaces.AssetVaultManager;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_user.interfaces.IntraWalletUserIdentityManager;
import com.bitdubai.fermat_ccp_api.layer.transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.all_definition.enums.IssuingStatus;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuerManager;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantDeliverDigitalAssetToAssetWalletException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantExecuteDatabaseOperationException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions.CantIssueDigitalAssetsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.interfaces.AssetIssuingManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.exceptions.CantCheckAssetIssuingProgressException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.database.AssetIssuingTransactionDao;
import com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_issuing.developer.bitdubai.version_1.structure.events.AssetIssuingTransactionMonitorAgent;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.exceptions.CantGetLoggedInDeviceUserException;

import java.util.UUID;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 09/09/15.
 */
public class AssetIssuingTransactionManager implements AssetIssuingManager, DealsWithErrors/*, TransactionProtocolManager*/ {

    CryptoAddressBookManager cryptoAddressBookManager;
    CryptoVaultManager cryptoVaultManager;
    BitcoinWalletManager bitcoinWalletManager;
    ErrorManager errorManager;
    UUID pluginId;
    PluginDatabaseSystem pluginDatabaseSystem;
    DigitalAssetCryptoTransactionFactory digitalAssetCryptoTransactionFactory;
    PluginFileSystem pluginFileSystem;
    AssetVaultManager assetVaultManager;
    OutgoingIntraActorManager outgoingIntraActorManager;
    AssetIssuingTransactionDao assetIssuingTransactionDao;
    AssetIssuingTransactionMonitorAgent assetIssuingTransactionMonitorAgent;
    String userPublicKey;
    EventManager eventManager;
    DigitalAssetIssuingVault digitalAssetIssuingVault;
    LogManager logManager;
    BitcoinNetworkManager bitcoinNetworkManager;
    //ActorAssetIssuerManager actorAssetIssuerManager;

    public AssetIssuingTransactionManager(UUID pluginId,
                                          CryptoVaultManager cryptoVaultManager,
                                          BitcoinWalletManager bitcoinWalletManager,
                                          PluginDatabaseSystem pluginDatabaseSystem,
                                          PluginFileSystem pluginFileSystem,
                                          ErrorManager errorManager,
                                          AssetVaultManager assetVaultManager,
                                          CryptoAddressBookManager cryptoAddressBookManager,
                                          OutgoingIntraActorManager outgoingIntraActorManager) throws CantSetObjectException, CantExecuteDatabaseOperationException {

        setCryptoVaultManager(cryptoVaultManager);
        setCryptoWallet(bitcoinWalletManager);
        setErrorManager(errorManager);
        setPluginFileSystem(pluginFileSystem);
        setPluginId(pluginId);
        setPluginDatabaseSystem(pluginDatabaseSystem);
        setAssetVaultManager(assetVaultManager);
        setCryptoAddressBookManager(cryptoAddressBookManager);
        setOutgoingIntraActorManager(outgoingIntraActorManager);
        this.digitalAssetCryptoTransactionFactory=new DigitalAssetCryptoTransactionFactory(this.pluginId,
                this.cryptoVaultManager,
                this.bitcoinWalletManager,
                this.pluginDatabaseSystem,
                this.pluginFileSystem,
                this.assetVaultManager,
                this.cryptoAddressBookManager,
                this.outgoingIntraActorManager);
        this.digitalAssetCryptoTransactionFactory.setErrorManager(errorManager);
    }

    public void setUserPublicKey(String userPublicKey)throws CantSetObjectException{
        if(userPublicKey==null){
            throw new CantSetObjectException("UserPublicKey is null");
        }
        this.userPublicKey=userPublicKey;
    }

    public void setEventManager(EventManager eventManager)throws CantSetObjectException{
        if(eventManager==null){
            throw new CantSetObjectException("EventManager is null");
        }
        this.eventManager=eventManager;
    }

    public void setLogManager(LogManager logManager)throws CantSetObjectException{
        if(logManager==null){
            throw new CantSetObjectException("logManager is null");
        }
        this.logManager=logManager;
    }

    public void setBitcoinNetworkManager(BitcoinNetworkManager bitcoinNetworkManager) throws CantSetObjectException{
        if(bitcoinNetworkManager ==null){
            throw new CantSetObjectException("bitcoinNetworkManager is null");
        }
        this.bitcoinNetworkManager = bitcoinNetworkManager;
    }

    public void setActorAssetIssuerManager(ActorAssetIssuerManager actorAssetIssuerManager) throws CantSetObjectException{
        if(actorAssetIssuerManager==null){
            throw new CantSetObjectException("actorAssetIssuerManager is null");
        }
        this.digitalAssetCryptoTransactionFactory.setActorAssetIssuerManager(actorAssetIssuerManager);
    }

    public void setIntraWalletUserActorManager(IntraWalletUserActorManager intraWalletUserActorManager) throws CantSetObjectException{
        if(intraWalletUserActorManager==null){
            throw new CantSetObjectException("intraWalletUserActorManager is null");
        }
        this.digitalAssetCryptoTransactionFactory.setIntraWalletUserActorManager(intraWalletUserActorManager);
    }

    public void setIntraWalletUserIdentityManager(IntraWalletUserIdentityManager intraWalletUserIdentityManager) throws CantSetObjectException{
        if(intraWalletUserIdentityManager==null){
            throw new CantSetObjectException("intraWalletUserIdentityManager is null");
        }
        this.digitalAssetCryptoTransactionFactory.setIntraWalletUserIdentityManager(intraWalletUserIdentityManager);
    }

    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the asset issuing plugin
     * @throws CantGetLoggedInDeviceUserException
     * @throws CantSetObjectException
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantGetLoggedInDeviceUserException, CantSetObjectException, CantStartAgentException {
        if(this.assetIssuingTransactionMonitorAgent==null){
            //String userPublicKey = this.deviceUserManager.getLoggedInDeviceUser().getPublicKey();
            this.assetIssuingTransactionMonitorAgent=new AssetIssuingTransactionMonitorAgent(this.eventManager,
                    this.pluginDatabaseSystem,
                    this.errorManager,
                    this.pluginId,
                    this.userPublicKey,
                    this.assetVaultManager,
                    this.outgoingIntraActorManager);
            this.assetIssuingTransactionMonitorAgent.setDigitalAssetIssuingVault(digitalAssetIssuingVault);
            this.assetIssuingTransactionMonitorAgent.setLogManager(this.logManager);
            this.assetIssuingTransactionMonitorAgent.setBitcoinNetworkManager(bitcoinNetworkManager);
            this.assetIssuingTransactionMonitorAgent.start();
        }else{
            this.assetIssuingTransactionMonitorAgent.start();
        }
    }

    @Override
    public void issueAssets(DigitalAsset digitalAssetToIssue, int assetsAmount, String walletPublicKey, BlockchainNetworkType blockchainNetworkType) throws CantIssueDigitalAssetsException {
        try {
            //startMonitorAgent();
            //For testing and for now the walletPublicKey is hardcoded
            //walletPublicKey="walletPublicKeyTest";
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

    public void setCryptoWallet(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager=bitcoinWalletManager;
    }

    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager=errorManager;
    }

    public void setDigitalAssetMetadataVault(DigitalAssetIssuingVault digitalAssetIssuingVault) throws CantSetObjectException {
        this.digitalAssetIssuingVault=digitalAssetIssuingVault;
        this.digitalAssetCryptoTransactionFactory.setDigitalAssetIssuingVault(digitalAssetIssuingVault);
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

    @Override
    public IssuingStatus getIssuingStatus(String assetPublicKey) throws CantExecuteDatabaseOperationException {
        try {
            String issuingStatusCode=this.assetIssuingTransactionDao.getIssuingStatusByPublicKey(assetPublicKey);
            return IssuingStatus.getByCode(issuingStatusCode);
        } catch (CantCheckAssetIssuingProgressException exception) {
            throw new CantExecuteDatabaseOperationException(exception,"Getting the Issuing status","Cannot check the Asset Issuing progress");
        } catch (InvalidParameterException exception) {
            throw new CantExecuteDatabaseOperationException(exception,"Getting the Issuing status","Cannot invalid parameter in IssuingStatus enum");
        }

    }
}
