package com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.identity.ActorIdentity;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.exceptions.CantCreateCryptoCustomerActorException;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces.CryptoCustomerActor;
import com.bitdubai.fermat_cbp_api.layer.cbp_actor.crypto_customer.interfaces.CryptoCustomerActorManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.database.CryptoCustomerActorDatabaseDao;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.exceptions.CantInitializeCryptoCustomerActorDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.actor.crypto_customer.developer.bitdubai.version_1.structure.CryptoCustomerActorImpl;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;

/**
 * TODO THIS PLUG-IN DOES........
 *
 * Created by jorge on 30-10-2015.
 * Updated by lnacosta (laion.cj91@gmail.com) on 18/11/2015.
 */
public class CryptoCustomerActorPluginRoot extends AbstractPlugin implements CryptoCustomerActorManager {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.ERROR_MANAGER)
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.SYSTEM, addon = Addons.PLUGIN_DATABASE_SYSTEM)
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM, layer = Layers.NEGOTIATION, plugin = Plugins.CRYPTO_BROKER_PURCHASE)
    private CustomerBrokerPurchaseNegotiationManager purchaseNegotiationManager;


    private CryptoCustomerActorDatabaseDao databaseDao;

    public CryptoCustomerActorPluginRoot(){
        super(new PluginVersionReference(new Version()));
    }

    @Override
    public void start() throws CantStartPluginException {

        try {

            databaseDao = new CryptoCustomerActorDatabaseDao(pluginDatabaseSystem, pluginId);
            databaseDao.initialize();

            this.serviceStatus = ServiceStatus.STARTED;

        } catch (final CantInitializeCryptoCustomerActorDatabaseException e) {

            errorManager.reportUnexpectedPluginException(this.getPluginVersionReference(), UnexpectedPluginExceptionSeverity.DISABLES_THIS_PLUGIN, e);
            throw new CantStartPluginException(e, "pluginDatabaseSystem=" + pluginDatabaseSystem + "pluginId=" + pluginId, "Cannot initialize Crypto Customer Actor Database.");
        }
    }

    @Override
    public CryptoCustomerActor createNewCryptoCustomerActor(ActorIdentity identity) throws CantCreateCryptoCustomerActorException {

        // TODO PLEASE CHECK THE OTHER ACTORS, THINK THIS IS WRONG. LET'S THINK TOGETHER.
        // TODO MAKE USE OF THE ERROR MANAGER.
        return new CryptoCustomerActorImpl(identity, purchaseNegotiationManager, databaseDao);
    }

    @Override
    public CryptoCustomerActor getCryptoCustomer(ActorIdentity identity) {

        // TODO PLEASE CHECK THE OTHER ACTORS, THINK THIS IS WRONG. LET'S THINK TOGETHER.
        // TODO MAKE USE OF THE ERROR MANAGER.
        return new CryptoCustomerActorImpl(identity, purchaseNegotiationManager, databaseDao);
    }
}
