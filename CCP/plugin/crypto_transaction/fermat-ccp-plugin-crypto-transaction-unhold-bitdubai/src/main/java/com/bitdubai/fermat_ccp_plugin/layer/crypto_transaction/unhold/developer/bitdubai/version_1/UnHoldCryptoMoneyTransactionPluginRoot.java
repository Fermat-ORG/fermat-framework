package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.CantStartAgentException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFilter;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_api.all_definition.enums.CryptoTransactionStatus;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.unhold.exceptions.CantCreateUnHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.unhold.exceptions.CantGetUnHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.unhold.interfaces.CryptoUnholdTransaction;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.unhold.interfaces.CryptoUnholdTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.unhold.interfaces.CryptoUnholdTransactionParameters;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.database.UnHoldCryptoMoneyTransactionDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.database.UnHoldCryptoMoneyTransactionDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.database.UnHoldCryptoMoneyTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.exceptions.MissingUnHoldCryptoDataException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.structure.UnHoldCryptoMoneyTransactionManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.structure.events.UnHoldCryptoMoneyTransactionMonitorAgent;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.structure.events.UnHoldCryptoMoneyTransactionMonitorAgent2;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.unhold.developer.bitdubai.version_1.utils.UnHoldCryptoMoneyTransactionImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by franklin on 16/11/15.
 */
public class UnHoldCryptoMoneyTransactionPluginRoot extends AbstractPlugin  implements
        CryptoUnholdTransactionManager,
        DatabaseManagerForDevelopers {

    public UnHoldCryptoMoneyTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private UnHoldCryptoMoneyTransactionManager unHoldCryptoMoneyTransactionManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.BITCOIN_WALLET)
    CryptoWalletManager cryptoWalletManager;

    /**
     * Represents the plugin processor agent
     */
    UnHoldCryptoMoneyTransactionMonitorAgent2 processorAgent;

    //Agent configuration
    private final long SLEEP_TIME = 5000;
    private final long DELAY_TIME = 500;
    private final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;

    @Override
    public void start() throws CantStartPluginException {
        unHoldCryptoMoneyTransactionManager = new UnHoldCryptoMoneyTransactionManager(pluginDatabaseSystem, pluginId);
        try {
            Database database = pluginDatabaseSystem.openDatabase(pluginId, UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_DATABASE_NAME);

            System.out.println("******* Init UnHold Crypto Money Transaction ******");
            //Buscar la manera de arrancar el agente solo cuando hayan transacciones diferentes a COMPLETED
            startMonitorAgent();

            database.closeDatabase();
        }
        catch (CantOpenDatabaseException | DatabaseNotFoundException | CantStartAgentException e)
        {
            try
            {
                startMonitorAgent();
                UnHoldCryptoMoneyTransactionDatabaseFactory UnHoldCryptoMoneyTransactionDatabaseFactory = new UnHoldCryptoMoneyTransactionDatabaseFactory(this.pluginDatabaseSystem);
                UnHoldCryptoMoneyTransactionDatabaseFactory.createDatabase(this.pluginId, UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_DATABASE_NAME);
            }
            catch(CantCreateDatabaseException cantCreateDatabaseException)
            {
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            }catch (Exception exception) {
                throw new CantStartPluginException("Cannot start UnHoldCryptoMoneyTransactionPluginRoot plugin.", FermatException.wrapException(exception), null, null);
            }
        }
        this.serviceStatus = ServiceStatus.STARTED;
    }

    @Override
    public void stop() {
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        UnHoldCryptoMoneyTransactionDeveloperDatabaseFactory unHoldCryptoMoneyTransactionDeveloperDatabaseFactory = new UnHoldCryptoMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return unHoldCryptoMoneyTransactionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        UnHoldCryptoMoneyTransactionDeveloperDatabaseFactory unHoldCryptoMoneyTransactionDeveloperDatabaseFactory = new UnHoldCryptoMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return unHoldCryptoMoneyTransactionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        UnHoldCryptoMoneyTransactionDeveloperDatabaseFactory unHoldCryptoMoneyTransactionDeveloperDatabaseFactory = new UnHoldCryptoMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            unHoldCryptoMoneyTransactionDeveloperDatabaseFactory.initializeDatabase();
            developerDatabaseTableRecordList = unHoldCryptoMoneyTransactionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Hold Crypto Money Transaction ******");
        }
        return developerDatabaseTableRecordList;
    }


    private UnHoldCryptoMoneyTransactionMonitorAgent unHoldCryptoMoneyTransactionMonitorAgent;
    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the bank money restock plugin
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantStartAgentException {
        /*if(unHoldCryptoMoneyTransactionMonitorAgent == null) {
            unHoldCryptoMoneyTransactionMonitorAgent = new UnHoldCryptoMoneyTransactionMonitorAgent(
                    errorManager,
                    unHoldCryptoMoneyTransactionManager,
                    cryptoWalletManager
            );

            unHoldCryptoMoneyTransactionMonitorAgent.start();
        }else unHoldCryptoMoneyTransactionMonitorAgent.start();*/
        if(processorAgent == null) {
            processorAgent = new UnHoldCryptoMoneyTransactionMonitorAgent2(
                    SLEEP_TIME,
                    TIME_UNIT,
                    DELAY_TIME,
                    this,
                    unHoldCryptoMoneyTransactionManager,
                    cryptoWalletManager
            );

            processorAgent.start();
        }else processorAgent.start();
    }


    @Override
    public CryptoUnholdTransaction createCryptoUnholdTransaction(CryptoUnholdTransactionParameters holdParameters) throws CantCreateUnHoldTransactionException {
        UnHoldCryptoMoneyTransactionImpl unHoldCryptoMoneyTransaction = new UnHoldCryptoMoneyTransactionImpl();
        try {
            unHoldCryptoMoneyTransaction.setTransactionId(holdParameters.getTransactionId());
            unHoldCryptoMoneyTransaction.setPublicKeyWallet(holdParameters.getPublicKeyWallet());
            unHoldCryptoMoneyTransaction.setStatus(CryptoTransactionStatus.ACKNOWLEDGED);
            unHoldCryptoMoneyTransaction.setPublicKeyActor(holdParameters.getPublicKeyActor());
            unHoldCryptoMoneyTransaction.setPublicKeyPlugin(holdParameters.getPublicKeyPlugin());
            //TODO:Cambiar BigDecimal
            unHoldCryptoMoneyTransaction.setAmount(holdParameters.getAmount().floatValue());
            unHoldCryptoMoneyTransaction.setCurrency(holdParameters.getCurrency());
            unHoldCryptoMoneyTransaction.setMemo(holdParameters.getMemo());
            unHoldCryptoMoneyTransaction.setBlockchainNetworkType(holdParameters.getBlockchainNetworkType());
            unHoldCryptoMoneyTransaction.setFee(holdParameters.getFee());
            unHoldCryptoMoneyTransaction.setFeeOrigin(holdParameters.getFeeOrigin());
            unHoldCryptoMoneyTransactionManager.saveUnHoldCryptoMoneyTransactionData(unHoldCryptoMoneyTransaction);
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (MissingUnHoldCryptoDataException e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        return unHoldCryptoMoneyTransaction;
    }

    @Override
    public CryptoTransactionStatus getCryptoUnholdTransactionStatus(final UUID transactionId) throws CantGetUnHoldTransactionException {
        CryptoTransactionStatus cryptoTransactionStatus = null;
        // I define the filter
        DatabaseTableFilter filter = new DatabaseTableFilter() {
            @Override
            public void setColumn(String column) {

            }

            @Override
            public void setType(DatabaseFilterType type) {

            }

            @Override
            public void setValue(String value) {

            }

            @Override
            public String getColumn() {
                return UnHoldCryptoMoneyTransactionDatabaseConstants.UNHOLD_TRANSACTION_ID_COLUMN_NAME;
            }

            @Override
            public String getValue() {
                return transactionId.toString();
            }

            @Override
            public DatabaseFilterType getType() {
                return DatabaseFilterType.EQUAL;
            }
        };


        try {
            cryptoTransactionStatus = unHoldCryptoMoneyTransactionManager.getUnHoldCryptoMoneyTransactionList(filter).get(0).getStatus();
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        return cryptoTransactionStatus;
    }
}
