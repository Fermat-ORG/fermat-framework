package com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.developer.DatabaseManagerForDevelopers;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.core.PluginInfo;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorNetworkServiceDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.actor_network_service.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorNetworkServiceManager;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.network_services.base.AbstractNetworkServiceBase;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.client.CommunicationsClientConnection;

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
@PluginInfo(createdBy = "lnacosta", maintainerMail = "laion.cj91@gmail.com", platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.ACTOR_NETWORK_SERVICE, plugin = Plugins.CRYPTO_CUSTOMER)
public class CryptoCustomerActorNetworkServicePluginRoot extends AbstractNetworkServiceBase implements DatabaseManagerForDevelopers {

    /**
     * Constructor of the Network Service.
     */
    public CryptoCustomerActorNetworkServicePluginRoot() {

        super(
                new PluginVersionReference(new Version()),
                EventSource.ACTOR_NETWORK_SERVICE_CRYPTO_CUSTOMER,
                PlatformComponentType.NETWORK_SERVICE,
                NetworkServiceType.CRYPTO_CUSTOMER,
                "Crypto Customer Actor Network Service",
                null
        );
    }

    /**
     * Service Interface implementation
     */
    @Override
    public void onStart() throws CantStartPluginException {

        final CommunicationsClientConnection communicationsClientConnection = wsCommunicationsCloudClientManager.getCommunicationsCloudClientConnection(getNetworkServiceProfile().getNetworkServiceType());

        fermatManager = new CryptoCustomerActorNetworkServiceManager(
                communicationsClientConnection         ,
                errorManager                           ,
                getPluginVersionReference()
        );

    }

    private CryptoCustomerActorNetworkServiceManager fermatManager;

    @Override
    public FermatManager getManager() {
        return fermatManager;
    }

    @Override
    protected void onNetworkServiceRegistered() {

        fermatManager.setPlatformComponentProfile(this.getNetworkServiceProfile());

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
