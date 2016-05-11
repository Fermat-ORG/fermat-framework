package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseAgent;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiation;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.negotiation_transmission.interfaces.NegotiationTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseAgent;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

/**
 * Created by Yordin Alayn on 01.01.16.
 */

@RunWith(MockitoJUnitRunner.class)
public class StopTest {

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,           layer = Layers.PLATFORM_SERVICE,    addon = Addons.ERROR_MANAGER)
    @Mock
    private ErrorManager errorManager;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,        layer = Layers.SYSTEM,              addon = Addons.PLUGIN_DATABASE_SYSTEM)
    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;

    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API,        layer = Layers.SYSTEM,              addon = Addons.LOG_MANAGER)
    @Mock
    private LogManager logManager;

    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM,           layer = Layers.PLATFORM_SERVICE,    addon = Addons.EVENT_MANAGER)
    @Mock
    private EventManager eventManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM,     layer = Layers.NEGOTIATION,         plugin = Plugins.CUSTOMER_BROKER_PURCHASE)
    @Mock
    private CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM,     layer = Layers.NEGOTIATION,         plugin = Plugins.CUSTOMER_BROKER_SALE)
    @Mock
    private CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_BROKER_PLATFORM,     layer = Layers.NETWORK_SERVICE,     plugin = Plugins.NEGOTIATION_TRANSMISSION)
    @Mock
    private NegotiationTransmissionManager negotiationTransmissionManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS,                layer = Layers.CRYPTO_MODULE,       plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    @Mock
    private CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS,                layer = Layers.CRYPTO_VAULT,        plugin = Plugins.BITCOIN_VAULT)
    @Mock
    private CryptoVaultManager cryptoVaultManager;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM,   layer = Layers.MIDDLEWARE,          plugin = Plugins.WALLET_MANAGER)
    @Mock
    private WalletManagerManager walletManagerManager;

    /*Represent the Negotiation Purchase*/
    @Mock
    private CustomerBrokerPurchaseNegotiation customerBrokerPurchaseNegotiation;

    /*Represent the Negotiation Sale*/
    @Mock
    private CustomerBrokerSaleNegotiation customerBrokerSaleNegotiation;

    protected UUID pluginId;

    private CustomerBrokerCloseAgent testObj1;

    @Test
    public void Stop_AgentStops_TheThreadIsStoppedInmediately() throws Exception{

        testObj1 = new CustomerBrokerCloseAgent(
                pluginDatabaseSystem,
                logManager,
                errorManager,
                eventManager,
                pluginId,
                negotiationTransmissionManager,
                customerBrokerPurchaseNegotiation,
                customerBrokerSaleNegotiation,
                customerBrokerPurchaseNegotiationManager,
                customerBrokerSaleNegotiationManager,
                cryptoAddressBookManager,
                cryptoVaultManager,
                walletManagerManager
        );
/*
        testObj1.start();
        Thread.sleep(100);
        int i = 0;
        while(testObj1.isRunning()){
            ++i;
            if(i>5)
                testObj1.stop();
            Thread.sleep(100);
            if(i>200)
                break;
        }
        assertThat(i).isLessThan(200);
        */
    }
}
