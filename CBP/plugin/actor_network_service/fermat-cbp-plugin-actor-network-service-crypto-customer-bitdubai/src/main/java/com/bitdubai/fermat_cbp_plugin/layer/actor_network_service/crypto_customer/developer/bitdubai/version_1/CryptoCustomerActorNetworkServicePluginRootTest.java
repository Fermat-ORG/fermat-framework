package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorNetworkServiceManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorNetworkServiceManagerTest;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.abstract_classes.AbstractActorNetworkService;

import java.util.List;

/**
 * The class <code>com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.CryptoCustomerActorNetworkServicePluginRoot</code>
 * bla bla bla.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 25/11/2015.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CryptoCustomerActorNetworkServicePluginRootTest extends AbstractActorNetworkService implements DatabaseManagerForDevelopers {

    /**
     * Constructor of the Network Service.
     */
    public CryptoCustomerActorNetworkServicePluginRootTest() {

        super(
                new PluginVersionReference(new Version()),
                EventSource.ACTOR_NETWORK_SERVICE_CRYPTO_CUSTOMER,
                NetworkServiceType.CRYPTO_CUSTOMER
        );
    }

    private CryptoCustomerActorNetworkServiceManagerTest fermatManager;

    @Override
    protected void onActorNetworkServiceStart() {

        fermatManager = new CryptoCustomerActorNetworkServiceManagerTest(
                this,
                errorManager,
                getPluginVersionReference()
        );
    }

    @Override
    public FermatManager getManager() {
        return fermatManager;
    }

    @Override
    public List<DeveloperDatabase> getDatabaseList(DeveloperObjectFactory developerObjectFactory) {
        return new CryptoCustomerActorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseList(developerObjectFactory);

    }

    @Override
    public List<DeveloperDatabaseTable> getDatabaseTableList(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase) {
        return new CryptoCustomerActorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableList(developerObjectFactory, developerDatabase);
    }

    @Override
    public List<DeveloperDatabaseTableRecord> getDatabaseTableContent(DeveloperObjectFactory developerObjectFactory, DeveloperDatabase developerDatabase, DeveloperDatabaseTable developerDatabaseTable) {
        return new CryptoCustomerActorNetworkServiceDeveloperDatabaseFactory(pluginDatabaseSystem, pluginId).getDatabaseTableContent(developerObjectFactory, developerDatabase, developerDatabaseTable);
    }

}
