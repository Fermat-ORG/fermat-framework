package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.DealsWithBitcoinWallet;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.CryptoTransmissionNetworkServiceManager;
import com.bitdubai.fermat_api.layer.dmp_network_service.crypto_transmission.interfaces.DealsWithCryptoTransmissionNetworkService;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.exceptions.CantGetOutgoingIntraUserTransactionManagerException;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.interfaces.OutgoingIntraUserManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.dmp_transaction.outgoing_intrauser.interfaces.IntraUserCryptoTransactionManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DealsWithPluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.DealsWithCryptoVault;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.database.OutgoingIntraUserDao;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.exceptions.CantInitializeOutgoingIntraUserDaoException;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.structure.OutgoingIntraUserTransactionManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.structure.OutgoingIntraUserTransactionProcessorAgent;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_intra_user.developer.bitdubai.version_1.util.OutgoingIntraUserTransactionHandlerFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 20/02/15.
 */
public class OutgoingIntraUserTransactionPluginRoot implements DealsWithBitcoinWallet, DealsWithCryptoTransmissionNetworkService, DealsWithCryptoVault, DealsWithErrors, DealsWithPluginDatabaseSystem, OutgoingIntraUserManager, Plugin, Service{

    /*
     * DealsWithBitcoinWallet Interface member variables.
     */
    private BitcoinWalletManager bitcoinWalletManager;

    /*
     * DealsWithCryptoTransmissionNetworkService Interface member variables.
     */
    private CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager;

    /*
     * DealsWithCryptoVault Interface member variables.
     */
    private CryptoVaultManager cryptoVaultManager;

    /*
     * DealsWithErrors Interface member variables.
     */
    private ErrorManager errorManager;

    /*
     * DealsWithPluginDatabaseSystem Interface member variables.
     */
    private PluginDatabaseSystem pluginDatabaseSystem;

    /*
     * OutgoingIntraUserManager Interface member variables.
     */

    /*
     * Plugin Interface member variables.
     */
    private UUID pluginId;


    /*
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;


    /*
     * OutgoingIntraUserTransaction member variables
     */
    private OutgoingIntraUserDao                       outgoingIntraUserDao;
    private OutgoingIntraUserTransactionProcessorAgent transactionProcessorAgent;
    private OutgoingIntraUserTransactionHandlerFactory transactionHandlerFactory;


    /*
     * DealsWithBitcoinWallet Interface implementation
     */
    @Override
    public void setBitcoinWalletManager(BitcoinWalletManager bitcoinWalletManager) {
        this.bitcoinWalletManager = bitcoinWalletManager;
    }

    /*
     * DealsWithCryptoTransmissionNetworkService Interface implementation
     */
    @Override
    public void setCryptoTransmissionNetworkService(CryptoTransmissionNetworkServiceManager cryptoTransmissionNetworkServiceManager) {
        this.cryptoTransmissionNetworkServiceManager = cryptoTransmissionNetworkServiceManager;
    }

    /*
     * DealsWithCryptoVault Interface implementation
     */
    @Override
    public void setCryptoVaultManager(CryptoVaultManager cryptoVaultManager) {
        this.cryptoVaultManager = cryptoVaultManager;
    }

    /*
     * DealsWithErrors Interface implementation
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    /*
     * DealsWithPluginDatabaseSystem Interface implementation
     */
    @Override
    public void setPluginDatabaseSystem(PluginDatabaseSystem pluginDatabaseSystemManager) {
        this.pluginDatabaseSystem = pluginDatabaseSystemManager;
    }


    /*
     * OutgoingIntraUserManager Interface implementation
     */
    @Override
    public IntraUserCryptoTransactionManager getTransactionManager() throws CantGetOutgoingIntraUserTransactionManagerException {
        return new OutgoingIntraUserTransactionManager(this.pluginId,this.errorManager,this.bitcoinWalletManager,this.pluginDatabaseSystem);
    }

    /**
     * Plugin Interface implementation.
     */
    @Override
    public void setId(UUID pluginId) {
        this.pluginId = pluginId;
    }

    /*
     * Service Interface implementation
     */
    @Override
    public void start() {
        try {
            this.outgoingIntraUserDao = new OutgoingIntraUserDao(this.errorManager, this.pluginDatabaseSystem);
            this.outgoingIntraUserDao.initialize(this.pluginId);
            this.transactionHandlerFactory = new OutgoingIntraUserTransactionHandlerFactory(this.bitcoinWalletManager, this.outgoingIntraUserDao);
            this.transactionProcessorAgent = new OutgoingIntraUserTransactionProcessorAgent(this.errorManager,
                                                                                            this.cryptoVaultManager,
                                                                                            this.bitcoinWalletManager,
                                                                                            this.outgoingIntraUserDao,
                                                                                            this.transactionHandlerFactory,
                                                                                            this.cryptoTransmissionNetworkServiceManager);
            this.transactionProcessorAgent.start();
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantInitializeOutgoingIntraUserDaoException e) {
            reportUnexpectedException(e);
        } catch (Exception e) {
            reportUnexpectedException(FermatException.wrapException(e));
        }
    }

    private void reportUnexpectedException(Exception e) {
        this.errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_OUTGOING_INTRA_USER_TRANSACTION, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
    }

    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.transactionProcessorAgent.stop();

        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

}
