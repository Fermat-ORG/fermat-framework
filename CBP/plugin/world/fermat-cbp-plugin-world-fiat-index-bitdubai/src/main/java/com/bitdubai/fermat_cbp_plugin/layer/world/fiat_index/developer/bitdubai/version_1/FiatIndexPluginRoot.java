package com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.FermatException;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
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

import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_cbp_api.layer.world.exceptions.CantCreateFiatIndexException;
import com.bitdubai.fermat_cbp_api.layer.world.exceptions.CantGetFiatIndexException;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndex;
import com.bitdubai.fermat_cbp_api.layer.world.interfaces.FiatIndexManager;
import com.bitdubai.fermat_api.layer.world.exceptions.CantGetIndexException;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.database.FiatIndexWorldDao;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.database.FiatIndexWorldDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.enums.FiatIndexProviders;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.exceptions.CantInitializeFiatIndexWorldDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.version_1.interfaces.IndexProvider;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.common.exceptions.CantDeliverDatabaseException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 11/2/2015.
 */


public class FiatIndexPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers, FiatIndexManager {


    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_FILE_SYSTEM)
    private PluginFileSystem pluginFileSystem;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;

    FiatIndexWorldDao dao;

    /*
     * PluginRoot Constructor
     */
    public FiatIndexPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }


    /*
     *  TESTING STUFFS
     */

    public void testGetCurrentIndex(){
        System.out.println("FIATINDEX - testGetCurrentIndex CALLED");

        FiatIndex index = null;
        try{
            index = getCurrentIndex(FiatCurrency.CANADIAN_DOLLAR);
        } catch (CantGetIndexException e){
            System.out.println("FIATINDEX - testGetCurrentIndex DAO EXCEPTION");
        }
        System.out.println("");
        System.out.println("");
        System.out.println("FIATINDEX - PROVIDER DESC: " + index.getProviderDescription());
        System.out.println("FIATINDEX - CURRENCY: " + index.getCurrency().getCode());
        System.out.println("FIATINDEX - REFERENCE CURRENCY: " + index.getReferenceCurrency().getCode());
        System.out.println("FIATINDEX - TIMESTAMP: " + index.getTimestamp());
        System.out.println("FIATINDEX - PURCHASE: " + index.getPurchasePrice());
        System.out.println("FIATINDEX - SALE: " + index.getSalePrice());

    }






    /*
     * Service interface implementation
     */
    @Override
    public void start() throws CantStartPluginException {
        System.out.println("FIATINDEX - PluginRoot START");

        try {
            dao = new FiatIndexWorldDao(pluginDatabaseSystem, pluginId, errorManager);
            dao.initialize();
        } catch (Exception e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(CantStartPluginException.DEFAULT_MESSAGE, FermatException.wrapException(e), null, null);
        }
        serviceStatus = ServiceStatus.STARTED;

        // testGetCurrentIndex();
    }


    @Override
    public FiatCurrency getReferenceCurrency() {
        return FiatCurrency.US_DOLLAR;
    }

    @Override
    public Collection<FiatCurrency> getSupportedCurrencies() {

        return new HashSet<>(Arrays.asList(FiatCurrency.values()));
    }

    @Override
    public FiatIndex getCurrentIndex(FiatCurrency currency) throws CantGetIndexException {
        String currencyCode = currency.getCode();
        IndexProvider ip = FiatIndexProviders.valueOf(currencyCode).getProviderInstance();
        FiatIndex fiatIndex = ip.getCurrentIndex(currency);

       try {
            dao.saveFiatIndex(fiatIndex);

        } catch(CantCreateFiatIndexException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }

        return fiatIndex;
    }

    @Override
    public Collection<FiatIndex> getIndexListFromDate(FiatCurrency currency, long timestamp) throws CantGetIndexException {
        //Todo: implement this in dao
        return null;
    }

    @Override
    public Collection<FiatIndex> getQueriedIndexHistory(FiatCurrency currency) {
        Collection<FiatIndex> fiatIndexList = null;

        try {
            fiatIndexList =  dao.getQueriedIndexHistory(currency);
        } catch(CantGetFiatIndexException e) {
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
        }
        return fiatIndexList;
    }




    /*
     * DatabaseManagerForDevelopers interface implementation
     */
    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        FiatIndexWorldDeveloperDatabaseFactory factory = new FiatIndexWorldDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        FiatIndexWorldDeveloperDatabaseFactory factory = new FiatIndexWorldDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        return factory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        FiatIndexWorldDeveloperDatabaseFactory factory = new FiatIndexWorldDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId);
        List<DeveloperDatabaseTableRecord> tableRecordList = null;
        try {
            factory.initializeDatabase();
            tableRecordList = factory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch(CantInitializeFiatIndexWorldDatabaseException cantInitializeException) {
            FermatException e = new CantDeliverDatabaseException("Database cannot be initialized", cantInitializeException, "FiatIndexPluginRoot", "");
            errorManager.reportUnexpectedPluginException(Plugins.BITDUBAI_CSH_MONEY_TRANSACTION_HOLD, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN,e);
        }
        return tableRecordList;
    }
}