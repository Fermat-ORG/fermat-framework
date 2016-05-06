package com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1;

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
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.exceptions.CantCreateHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.exceptions.CantGetHoldTransactionException;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransaction;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransactionManager;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.hold.interfaces.CryptoHoldTransactionParameters;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.database.HoldCryptoMoneyTransactionDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.database.HoldCryptoMoneyTransactionDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.database.HoldCryptoMoneyTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.exceptions.DatabaseOperationException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.exceptions.MissingHoldCryptoDataException;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.structure.HoldCryptoMoneyTransactionManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.structure.events.HoldCryptoMoneyTransactionMonitorAgent;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.hold.developer.bitdubai.version_1.utils.HoldCryptoMoneyTransactionImpl;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.List;
import java.util.UUID;

/**
 * Created by franklin on 16/11/15.
 */
public class HoldCryptoMoneyTransactionPluginRoot extends AbstractPlugin  implements
        CryptoHoldTransactionManager,
        DatabaseManagerForDevelopers {

    public HoldCryptoMoneyTransactionPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    private HoldCryptoMoneyTransactionManager holdCryptoMoneyTransactionManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM, layer = Layers.BASIC_WALLET, plugin = Plugins.BITCOIN_WALLET)
    BitcoinWalletManager bitcoinWalletManager;


    @Override
    public void start() throws CantStartPluginException {
        holdCryptoMoneyTransactionManager = new HoldCryptoMoneyTransactionManager(pluginDatabaseSystem, pluginId);
        try {
            Database database = pluginDatabaseSystem.openDatabase(pluginId, HoldCryptoMoneyTransactionDatabaseConstants.HOLD_DATABASE_NAME);

            System.out.println("******* Init Hold Crypto Money Transaction ******");
            //Buscar la manera de arrancar el agente solo cuando hayan transacciones diferentes a COMPLETED
            //testHold();
            startMonitorAgent();

            database.closeDatabase();
        }
        catch (CantOpenDatabaseException | DatabaseNotFoundException | CantStartAgentException e)
        {
            try
            {
                startMonitorAgent();
                HoldCryptoMoneyTransactionDatabaseFactory holdCryptoMoneyTransactionDatabaseFactory = new HoldCryptoMoneyTransactionDatabaseFactory(this.pluginDatabaseSystem);
                holdCryptoMoneyTransactionDatabaseFactory.createDatabase(this.pluginId, HoldCryptoMoneyTransactionDatabaseConstants.HOLD_DATABASE_NAME);
            }
            catch(CantCreateDatabaseException cantCreateDatabaseException)
            {
                errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, cantCreateDatabaseException);
                throw new CantStartPluginException();
            }catch (Exception exception) {
                throw new CantStartPluginException("Cannot start HoldCryptoMoneyTransactionPluginRoot plugin.", FermatException.wrapException(exception), null, null);
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
        HoldCryptoMoneyTransactionDeveloperDatabaseFactory holdCryptoMoneyTransactionDeveloperDatabaseFactory = new HoldCryptoMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return holdCryptoMoneyTransactionDeveloperDatabaseFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        HoldCryptoMoneyTransactionDeveloperDatabaseFactory holdCryptoMoneyTransactionDeveloperDatabaseFactory = new HoldCryptoMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return holdCryptoMoneyTransactionDeveloperDatabaseFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        HoldCryptoMoneyTransactionDeveloperDatabaseFactory holdCryptoMoneyTransactionDeveloperDatabaseFactory = new HoldCryptoMoneyTransactionDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> developerDatabaseTableRecordList = null;
        try {
            holdCryptoMoneyTransactionDeveloperDatabaseFactory.initializeDatabase();
            developerDatabaseTableRecordList = holdCryptoMoneyTransactionDeveloperDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (Exception e) {
            System.out.println("******* Error trying to get database table list for plugin Hold Crypto Money Transaction ******");
        }
        return developerDatabaseTableRecordList;
    }


    private HoldCryptoMoneyTransactionMonitorAgent holdCryptoMoneyTransactionMonitorAgent;
    /**
     * This method will start the Monitor Agent that watches the asyncronic process registered in the bank money restock plugin
     * @throws CantStartAgentException
     */
    private void startMonitorAgent() throws CantStartAgentException {
        if(holdCryptoMoneyTransactionMonitorAgent == null) {
            holdCryptoMoneyTransactionMonitorAgent = new HoldCryptoMoneyTransactionMonitorAgent(
                    errorManager,
                    holdCryptoMoneyTransactionManager,
                    bitcoinWalletManager
            );

            holdCryptoMoneyTransactionMonitorAgent.start();
        }else holdCryptoMoneyTransactionMonitorAgent.start();
    }


    @Override
    public CryptoHoldTransaction createCryptoHoldTransaction(CryptoHoldTransactionParameters holdParameters) throws CantCreateHoldTransactionException {
        HoldCryptoMoneyTransactionImpl cryptoMoneyTransaction = new HoldCryptoMoneyTransactionImpl();
        try {
            cryptoMoneyTransaction.setTransactionId(holdParameters.getTransactionId());
            cryptoMoneyTransaction.setPublicKeyWallet(holdParameters.getPublicKeyWallet());
            cryptoMoneyTransaction.setStatus(CryptoTransactionStatus.ACKNOWLEDGED);
            cryptoMoneyTransaction.setPublicKeyActor(holdParameters.getPublicKeyActor());
            cryptoMoneyTransaction.setPublicKeyPlugin(holdParameters.getPublicKeyPlugin());
            //TODO:Colocar BigDecimal
            cryptoMoneyTransaction.setAmount(holdParameters.getAmount().floatValue());
            cryptoMoneyTransaction.setCurrency(holdParameters.getCurrency());
            cryptoMoneyTransaction.setMemo(holdParameters.getMemo());
            cryptoMoneyTransaction.setBlockchainNetworkType(holdParameters.getBlockchainNetworkType());
             holdCryptoMoneyTransactionManager.saveHoldCryptoMoneyTransactionData(cryptoMoneyTransaction);
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (MissingHoldCryptoDataException e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        return cryptoMoneyTransaction;
    }

    @Override
    public CryptoTransactionStatus getCryptoHoldTransactionStatus(final UUID transactionId) throws CantGetHoldTransactionException {
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
                return HoldCryptoMoneyTransactionDatabaseConstants.HOLD_TRANSACTION_ID_COLUMN_NAME;
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
            if(!holdCryptoMoneyTransactionManager.getHoldCryptoMoneyTransactionList(filter).isEmpty()){
                cryptoTransactionStatus = holdCryptoMoneyTransactionManager.getHoldCryptoMoneyTransactionList(filter).get(0).getStatus();
            }
        } catch (DatabaseOperationException e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        } catch (InvalidParameterException e) {
            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }

        return cryptoTransactionStatus;
    }

//    private void testHold(){
//        try {
//            CryptoHoldTransactionParameters cryptoHoldTransactionParameters = new CryptoHoldTransactionParameters() {
//                @Override
//                public UUID getTransactionId() {
//                    return UUID.randomUUID();
//                }
//
//                @Override
//                public void setTransactionId(UUID transactionId) {
//
//                }
//
//                @Override
//                public String getPublicKeyWallet() {
//                    return "walletPublicKey";
//                }
//
//                @Override
//                public void setPublicKeyWallet(String publicKeyWallet) {
//
//                }
//
//                @Override
//                public String getPublicKeyActor() {
//                    return "actorWalletPublicKey";
//                }
//
//                @Override
//                public void setPublicKeyActor(String publicKeyActor) {
//
//                }
//
//                @Override
//                public String getPublicKeyPlugin() {
//                    return pluginId.toString();
//                }
//
//                @Override
//                public void setPublicKeyPlugin(String publicKeyPlugin) {
//
//                }
//
//                @Override
//                public float getAmount() {
//                    return 1500;
//                }
//
//                @Override
//                public void setAmount(float amount) {
//
//                }
//
//                @Override
//                public CryptoCurrency getCurrency() {
//                    return CryptoCurrency.BITCOIN;
//                }
//
//                @Override
//                public void setCurrency(CryptoCurrency currency) {
//
//                }
//
//                @Override
//                public String getMemo() {
//                    return "memo";
//                }
//
//                @Override
//                public void setMemo(String memo) {
//
//                }
//            };
//            createCryptoHoldTransaction(cryptoHoldTransactionParameters);
//        } catch (CantCreateHoldTransactionException e) {
//            e.printStackTrace();
//        }
//    }
}
