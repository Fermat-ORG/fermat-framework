package com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedPluginExceptionSeverity;
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
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseNegotiationDao;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.database.CustomerBrokerPurchaseNegotiationDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.exceptions.CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation.customer_broker_purchase.developer.bitdubai.version_1.structure.CustomerBrokerPurchaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorge on 12-10-2015.
 * Update by Angel on 30/11/2015
 */
@PluginInfo(createdBy = "vlzangel", maintainerMail = "vlzangel91@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.NEGOTIATION_PURCHASE)
public class CustomerBrokerPurchaseNegotiationPluginRoot extends AbstractPlugin implements DatabaseManagerForDevelopers {

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    private CustomerBrokerPurchaseNegotiationDao customerBrokerPurchaseNegotiationDao;

    /*
       Builder
    */

    public CustomerBrokerPurchaseNegotiationPluginRoot() {
        super(new PluginVersionReference(new Version()));
    }

    /*
        Plugin Interface implementation.
    */

    @Override
    public void start() throws CantStartPluginException {
        this.serviceStatus = ServiceStatus.STARTED;
        try {
            this.customerBrokerPurchaseNegotiationDao = new CustomerBrokerPurchaseNegotiationDao(pluginDatabaseSystem, pluginId);
            this.customerBrokerPurchaseNegotiationDao.initializeDatabase();
        } catch (CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException();
        }
    }

    @Override
    public FermatManager getManager() {
        return new CustomerBrokerPurchaseManager(
                this.customerBrokerPurchaseNegotiationDao,
                this
        );
    }

    /*
        DatabaseManagerForDevelopers Interface implementation.
    */

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        CustomerBrokerPurchaseNegotiationDeveloperDatabaseFactory dbFactory = new CustomerBrokerPurchaseNegotiationDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        CustomerBrokerPurchaseNegotiationDeveloperDatabaseFactory dbFactory = new CustomerBrokerPurchaseNegotiationDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
        return dbFactory.getDatabaseTableList(developerObjectFactory);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        try {
            CustomerBrokerPurchaseNegotiationDeveloperDatabaseFactory dbFactory = new CustomerBrokerPurchaseNegotiationDeveloperDatabaseFactory(this.pluginDatabaseSystem, this.pluginId);
            dbFactory.initializeDatabase();
            return dbFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
        } catch (CantInitializeCustomerBrokerPurchaseNegotiationDatabaseException e) {
            reportError(UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, e);
        }
        return new ArrayList<>();
    }


}
